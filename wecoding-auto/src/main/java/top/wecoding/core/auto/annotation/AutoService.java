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

package top.wecoding.core.auto.annotation;

import top.wecoding.core.auto.service.AutoServiceProcessor;

import java.lang.annotation.*;

/**
 * An annotation for service providers as described in {@link java.util.ServiceLoader}. The {@link
 * AutoServiceProcessor} generates the configuration files which
 * allows service providers to be loaded with {@link java.util.ServiceLoader#load(Class)}.
 *
 * <p>Service providers assert that they conform to the service provider specification.
 * Specifically, they must:
 *
 * <ul>
 * <li>be a non-inner, non-anonymous, concrete class
 * <li>have a publicly accessible no-arg constructor
 * <li>implement the interface type returned by {@code value()}
 * </ul>
 *
 * @author google
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface AutoService {
    /**
     * Returns the interfaces implemented by this service provider.
     *
     * @return interface array
     */
    Class<?>[] value();
}
