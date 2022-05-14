/*
 * Copyright (c) 2019-2029, Dreamlu 卢春梦 (596392912@qq.com & www.dreamlu.net).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.wecoding.core.auto.factories;

import top.wecoding.core.auto.annotation.AutoIgnore;
import top.wecoding.core.auto.annotation.AutoService;
import top.wecoding.core.auto.common.AbstractWeCodingProcessor;
import top.wecoding.core.auto.common.BootAutoType;
import top.wecoding.core.auto.common.MultiSetMap;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * spring boot 自动配置处理器
 *
 * @author L.cm
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("*")
@SupportedOptions("debug")
public class AutoFactoriesProcessor extends AbstractWeCodingProcessor {

    /**
     * 处理的注解 @FeignClient
     */
    private static final String FEIGN_CLIENT_ANNOTATION = "org.springframework.cloud.openfeign.FeignClient";
    /**
     * Feign 自动配置
     */
    private static final String FEIGN_AUTO_CONFIGURE_KEY = "top.wecoding.core.feign.WeCodingFeignAutoConfiguration";
    /**
     * The location to look for factories.
     * <p>Can be present in multiple JAR files.
     */
    private static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
    /**
     * devtools，有 Configuration 注解的 jar 一般需要 devtools 配置文件
     */
    private static final String DEVTOOLS_RESOURCE_LOCATION = "META-INF/spring-devtools.properties";
    /**
     * 数据承载
     */
    private final MultiSetMap<String, String> factories = new MultiSetMap<>();
    /**
     * 元素辅助类
     */
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    protected boolean processImpl(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            generateFactoriesFiles();
        } else {
            processAnnotations(annotations, roundEnv);
        }
        return false;
    }

    private void processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 日志 打印信息 gradle build --debug
        log(annotations.toString());
        Set<? extends Element> elementSet = roundEnv.getRootElements();
        log("All Element set: " + elementSet.toString());

        // 过滤 TypeElement
        Set<TypeElement> typeElementSet = elementSet.stream()
                .filter(this::isClassOrInterface)
                .filter(e -> e instanceof TypeElement)
                .map(e -> (TypeElement) e)
                .collect(Collectors.toSet());
        // 如果为空直接跳出
        if (typeElementSet.isEmpty()) {
            log("Annotations elementSet is isEmpty");
            return;
        }

        for (TypeElement typeElement : typeElementSet) {
            // ignore @AutoIgnore Element
            if (isAnnotation(elementUtils, typeElement, AutoIgnore.class.getName())) {
                log("Found @AutoIgnore annotation，ignore Element: " + typeElement.toString());
            } else if (isAnnotation(elementUtils, typeElement, FEIGN_CLIENT_ANNOTATION)) {
                log("Found @FeignClient Element: " + typeElement.toString());

                ElementKind elementKind = typeElement.getKind();
                // Feign Client 只处理 接口
                if (ElementKind.INTERFACE != elementKind) {
                    fatalError("@FeignClient Element " + typeElement.toString() + " 不是接口。");
                    continue;
                }

                String factoryName = typeElement.getQualifiedName().toString();
                if (factories.containsVal(factoryName)) {
                    continue;
                }

                log("读取到新配置 spring.factories factoryName：" + factoryName);
                factories.put(FEIGN_AUTO_CONFIGURE_KEY, factoryName);
            } else {
                for (BootAutoType autoType : BootAutoType.values()) {
                    String annotation = autoType.getAnnotation();
                    if (isAnnotation(elementUtils, typeElement, annotation)) {
                        log("Found @" + annotation + " Element: " + typeElement.toString());

                        String factoryName = typeElement.getQualifiedName().toString();
                        if (factories.containsVal(factoryName)) {
                            continue;
                        }

                        log("读取到新配置 spring.factories factoryName：" + factoryName);
                        factories.put(autoType.getConfigureKey(), factoryName);
                    }
                }
            }
        }
    }

    private void generateFactoriesFiles() {
        if (factories.isEmpty()) {
            return;
        }
        Filer filer = processingEnv.getFiler();
        try {
            // spring.factories 配置
            MultiSetMap<String, String> allFactories = new MultiSetMap<>();
            // 1. 用户手动配置项目下的 spring.factories 文件
            try {
                FileObject existingFactoriesFile = filer.getResource(StandardLocation.SOURCE_OUTPUT, "", FACTORIES_RESOURCE_LOCATION);
                // 查找是否已经存在 spring.factories
                log("Looking for existing spring.factories file at " + existingFactoriesFile.toUri());
                MultiSetMap<String, String> existingFactories = FactoriesFiles.readFactoriesFile(existingFactoriesFile, elementUtils);
                log("Existing spring.factories entries: " + existingFactories);
                allFactories.putAll(existingFactories);
            } catch (IOException e) {
                log("spring.factories resource file not found.");
            }
            // 2. 增量编译，已经存在的 spring.factories 文件
            try {
                FileObject existingFactoriesFile = filer.getResource(StandardLocation.CLASS_OUTPUT, "", FACTORIES_RESOURCE_LOCATION);
                // 查找是否已经存在 spring.factories
                log("Looking for existing spring.factories file at " + existingFactoriesFile.toUri());
                MultiSetMap<String, String> existingFactories = FactoriesFiles.readFactoriesFile(existingFactoriesFile, elementUtils);
                log("Existing spring.factories entries: " + existingFactories);
                allFactories.putAll(existingFactories);
            } catch (IOException e) {
                log("spring.factories resource file did not already exist.");
            }
            // 3. 处理器扫描出来的新的配置
            allFactories.putAll(factories);
            log("New spring.factories file contents: " + allFactories);
            FileObject factoriesFile = filer.createResource(StandardLocation.CLASS_OUTPUT, "", FACTORIES_RESOURCE_LOCATION);
            try (OutputStream out = factoriesFile.openOutputStream()) {
                FactoriesFiles.writeFactoriesFile(allFactories, out);
            }
            // 4. devtools 配置，因为有 @Configuration 注解的需要 devtools
            String classesPath = factoriesFile.toUri().toString().split("classes")[0];
            Path projectPath = Paths.get(new URI(classesPath)).getParent();
            String projectName = projectPath.getFileName().toString();
            FileObject devToolsFile = filer.createResource(StandardLocation.CLASS_OUTPUT, "", DEVTOOLS_RESOURCE_LOCATION);
            try (OutputStream out = devToolsFile.openOutputStream()) {
                FactoriesFiles.writeDevToolsFile(projectName, out);
            }
        } catch (IOException | URISyntaxException e) {
            fatalError(e);
        }
    }
}
