package top.wecoding.core.log.factory;

import top.wecoding.core.log.annotation.Log;

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
