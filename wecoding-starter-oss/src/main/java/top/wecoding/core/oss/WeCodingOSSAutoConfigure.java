/*
 * Copyright (c) 2022. WeCoding (wecoding@yeah.net).
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.wecoding.core.oss;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.wecoding.core.oss.props.FileStorageProperties;
import top.wecoding.core.oss.strategy.FileStorageContext;
import top.wecoding.core.oss.strategy.base.FileOperatorStrategy;
import top.wecoding.core.oss.strategy.local.LocalFileOperatorStrategy;

import java.util.Map;

/**
 * @author liuyuhui
 * @date 2022/04/04
 * @qq 1515418211
 */
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(FileStorageProperties.class)
public class WeCodingOSSAutoConfigure {

    private final FileStorageProperties fileStorageProperties;

    @Bean("LOCAL")
    @ConditionalOnProperty(prefix = FileStorageProperties.PREFIX, name = "default-storage-type", havingValue = "LOCAL")
    public LocalFileOperatorStrategy localFileOperatorStrategy() {
        return new LocalFileOperatorStrategy(fileStorageProperties);
    }

    @Bean
    public FileStorageContext fileStorageContext(Map<String, FileOperatorStrategy> fileOperatorStrategyMap) {
        return new FileStorageContext(fileOperatorStrategyMap, fileStorageProperties);
    }

}
