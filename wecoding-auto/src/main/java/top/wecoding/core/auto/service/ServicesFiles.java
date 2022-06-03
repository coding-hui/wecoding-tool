/*
 * Copyright (c) 2019-2022. WeCoding (wecoding@yeah.net).
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

package top.wecoding.core.auto.service;

import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A helper class for reading and writing Services files.
 *
 * @author L.cm
 */
class ServicesFiles {
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * Reads the set of service classes from a service file.
     *
     * @param fileObject not {@code null}. Closed after use.
     * @return a not {@code null Set} of service class names.
     * @throws IOException
     */
    protected static Set<String> readServiceFile(FileObject fileObject, Elements elementUtils) throws IOException {
        HashSet<String> serviceClasses = new HashSet<>();
        try (
                InputStream input = fileObject.openInputStream();
                InputStreamReader isr = new InputStreamReader(input, UTF_8);
                BufferedReader r = new BufferedReader(isr)
        ) {
            String line;
            while ((line = r.readLine()) != null) {
                // 跳过注释行
                int commentStart = line.indexOf('#');
                if (commentStart >= 0) {
                    continue;
                }
                line = line.trim();
                // 校验是否删除文件
                if (!line.isEmpty() && Objects.nonNull(elementUtils.getTypeElement(line))) {
                    serviceClasses.add(line);
                }
            }
            return serviceClasses;
        }
    }

    /**
     * Writes the set of service class names to a service file.
     *
     * @param output   not {@code null}. Not closed after use.
     * @param services a not {@code null Collection} of service class names.
     * @throws IOException
     */
    protected static void writeServiceFile(Collection<String> services, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, UTF_8));
        for (String service : services) {
            writer.write(service);
            writer.newLine();
        }
        writer.flush();
    }
}
