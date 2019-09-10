package com.laozhang.max_mybatis_mapper.common;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FileUtil {
    /**
     * 封装下载返回
     *
     * @param response
     * @param fileName
     */
    public static final void wrapDownloadResponse(final HttpServletResponse response, final String fileName) {

        FileUtil.wrapDownloadResponse(response, fileName, -1);
    }

    /**
     * 封装下载返回
     *
     * @param response
     * @param fileName
     * @param fileLength
     */
    public static final void wrapDownloadResponse(final HttpServletResponse response, final String fileName,
                                                  final int fileLength) {

        response.setContentType("application/octet-stream;charset=UTF-8");
        if (fileLength > 0) {
            response.setContentLength(fileLength);
        }
        String headerValue = "attachment;";
        headerValue += " filename=\"" + encodeURIComponent(fileName) + "\";";
        headerValue += " filename*=utf-8''" + encodeURIComponent(fileName);
        response.setHeader("Content-Disposition", headerValue);
        response.setCharacterEncoding("UTF-8");
    }

    /**
     * <pre>
     * 符合 RFC 3986 标准的“百分号URL编码”
     * 在这个方法里，空格会被编码成%20，而不是+
     * 和浏览器的encodeURIComponent行为一致
     * </pre>
     *
     * @param value
     * @return
     */
    public static String encodeURIComponent(final String value) {

        try {
            return URLEncoder.encode(value, "UTF-8").replaceAll("\\+", "%20");
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
