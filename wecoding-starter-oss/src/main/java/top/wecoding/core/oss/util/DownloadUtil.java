package top.wecoding.core.oss.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.URLUtil;
import lombok.extern.slf4j.Slf4j;
import top.wecoding.core.exception.BizException;
import top.wecoding.core.exception.code.SystemErrorCodeEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 下载文件工具类
 *
 * @author liuyuhui
 * @date 2022/04/05
 * @qq 1515418211
 */
@Slf4j
public class DownloadUtil {

    /**
     * 下载文件到 Response
     *
     * @param fileName  文件名称
     * @param fileBytes 文件字节数组
     * @param response  response
     */
    public static void download(String fileName, byte[] fileBytes, HttpServletResponse response) {
        try {
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLUtil.encode(fileName) + "\"");
            response.addHeader("Content-Length", "" + fileBytes.length);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setContentType("application/octet-stream;charset=UTF-8");
            IoUtil.write(response.getOutputStream(), true, fileBytes);
        } catch (IOException e) {
            log.error(">>> 下载文件异常:", e);
            throw new BizException(SystemErrorCodeEnum.FILE_DOWNLOAD_ERROR);
        }
    }

}
