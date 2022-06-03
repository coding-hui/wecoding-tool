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
package top.wecoding.core.log.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import top.wecoding.core.log.entity.SysOpLog;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
@Getter
public class OperationLogEvent extends ApplicationEvent {

    private final SysOpLog sysOpLog;

    public OperationLogEvent(final SysOpLog source) {
        super(source);
        this.sysOpLog = source;
    }

}
