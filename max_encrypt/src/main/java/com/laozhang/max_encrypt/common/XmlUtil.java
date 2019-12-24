package com.laozhang.max_encrypt.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class XmlUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlUtil.class);

    /**
     * 将xml字符串转成json字符串
     *
     * @param xmlStr
     * @return
     */
    public static String xmlToJsonStr(final String xmlStr) {

        try {
            return XmlFormatter.xmlToJsonStr(xmlStr);
        } catch (final Exception e) {
            LOGGER.error("XmlUtil.xmlToJsonStr error: ", e);
        }
        return null;
    }

    /**
     * 将json字符串转成xml字符串
     *
     * @param jsonStr
     * @return
     */
    public static String jsonToXmlStr(final String jsonStr) {

        try {
            return XmlFormatter.jsonToXmlStr(jsonStr);
        } catch (final Exception e) {
            LOGGER.error("XmlUtil.jsonToXmlStr error: ", e);
        }
        return null;
    }

    /**
     * 将对象转成xml字符串
     *
     * @param obj
     * @return
     */
    public static String objToXmlStr(final Object obj) {

        try {
            return XmlFormatter.objToXmlStr(obj);
        } catch (final Exception e) {
            LOGGER.error("XmlUtil.objToXmlStr error: ", e);
        }
        return null;
    }

    /**
     * 返回本次请求格式是否为application/xml
     *
     * @return true 如果是application/xml <br>
     *         false 如果不是
     */
    public static boolean requestDataIsXml() {

        final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        final String contentType = requestAttributes.getRequest().getHeader("Content-Type");
        if (StringUtils.isEmpty(contentType)) {
            return false;
        } else {
            return contentType.toLowerCase().contains("application/xml");
        }

    }

    /**
     * 返回本次请求格式是否为application/xml
     *
     * @return true 如果是application/xml <br>
     *         false 如果不是
     */
    public static boolean requestDataIsXml(final HttpServletRequest request) {

        final String contentType = request.getHeader("Content-Type");

        if (StringUtils.isEmpty(contentType)) {
            return false;
        } else {
            return contentType.toLowerCase().contains("application/xml");
        }

    }

    static class XmlFormatter {

        private static final Logger LOGGER = LoggerFactory.getLogger(XmlFormatter.class);

        /**
         * 将xml字符串转成Json对象
         *
         * @param xmlStr
         * @return
         */
        public static JSONObject xmlToJsonObj(final String xmlStr) {

            try {
                return XML.toJSONObject(xmlStr, true);
            } catch (final Exception e) {
                LOGGER.info("XML.toJSONObject exception:", e);
            }
            return null;

        }

        /**
         * 将xml字符串转成json字符串
         *
         * @param xmlStr
         * @return
         */
        public static String xmlToJsonStr(final String xmlStr) {

            return JSONObject.valueToString(xmlToJsonObj(xmlStr));
        }

        /**
         * 将json字符串转成xml字符串
         *
         * @param jsonStr
         * @return
         */
        public static String jsonToXmlStr(final String jsonStr) {

            try {
                return XML.toString(new JSONObject(jsonStr));
            } catch (final JSONException e) {
                LOGGER.info("jsonToXmlStr exception:", e);
            }
            return null;
        }

        /**
         * 将对象转成xml字符串
         *
         * @param obj
         * @return
         * @throws JSONException
         * @throws JsonProcessingException
         */
        public static String objToXmlStr(final Object obj) throws JSONException, JsonProcessingException {

            return XML.toString(new JSONObject(JsonUtil.toString(obj)));
        }

    }

}
