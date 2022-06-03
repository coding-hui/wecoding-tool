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
package top.wecoding.core.log.factory;

import top.wecoding.core.log.annotation.Log;
import top.wecoding.core.log.entity.SysOpLog;

/**
 * 日志对象创建工厂
 *
 * @author liuyuhui
 * @date 2022/4/29
 * @qq 1515418211
 */
public class LogFactory {

    /**
     * 创建操作日志
     */
    public static void createSuccessLoginLog(Log log) {

    }

    /**
     * 构建基础操作日志
     */
    public static SysOpLog genBaseSysOpLog(String ip, String location, String browser, String os, String url, String method) {
        SysOpLog sysOpLog = new SysOpLog();
        sysOpLog.setIp(ip);
        sysOpLog.setLocation(location);
        sysOpLog.setUserAgent(browser);
        sysOpLog.setLocation(os);
        sysOpLog.setRequestUri(url);
        sysOpLog.setMethod(method);
        return sysOpLog;
    }

}
