package com.laozhang.max_encrypt.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.net.URLDecoder;
import java.util.*;

public class MyRequestWrapper extends HttpServletRequestWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyRequestWrapper.class);

    private final Map<String, String[]> parameters = new HashMap<String, String[]>();

    private final Map<String, String> dataParms = new HashMap<String, String>();

    private byte[] bytes;

    private boolean firstTime = true;

    private InputStream inputStream;

    private BufferedReader bufferedReader;

    private ObjectMapper objectMapper = new ObjectMapper().configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,
            true);

    private static String encoding = "UTF-8";

    /**
     * 将拦截器重的异常信息存放进request中的key
     */
    public static String FILTERERROR = "filterError";

    public ObjectMapper getObjectMapper() {

        return objectMapper;
    }

    public void setObjectMapper(final ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    public String getEncoding() {

        return encoding;
    }

    public void setEncoding(final String encoding) {

        MyRequestWrapper.encoding = encoding;
    }

    public MyRequestWrapper(final HttpServletRequest request) {
        super(request);
        //final HttpServletRequest req = request;
        try {
            parseParameters();
        } catch (final Exception e) {
            LOGGER.error("URIDecodingFilter-入参格式不正确", e);
            //req.setAttribute(FILTERERROR, "入参格式不正确");
        }
    }

    protected void parseParameters() throws IOException {

        final String method = this.getHttpServletRequest().getMethod();
        final StringBuilder jsonBuilder = new StringBuilder();

        if ("GET".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method)) {
            jsonBuilder.append(this.getQueryString());
        } else {
            String line;
            final BufferedReader reader = getReader2();
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        }
        String jsonStr = jsonBuilder.toString();

        // 如果是xml格式的访问数据
        if (XmlUtil.requestDataIsXml(this.getHttpServletRequest())) {
            jsonStr = XmlUtil.xmlToJsonStr(jsonStr);
        }

        if (jsonStr.length() > 0  && ("GET".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method))) {
            final JsonNode node = objectMapper.readTree(jsonStr);
            final Iterator<String> fieldNames = node.fieldNames();
            for (; fieldNames.hasNext();) {
                final String key = fieldNames.next();
                final String value = node.get(key).toString();
                final String[] strArr = { "" };
                if (value.length() > 2 && value.startsWith("\"")) {

                    strArr[0] = value.substring(1, value.length() - 1);
                    parameters.put(key, strArr);
                } else {
                    strArr[0] = value;
                    parameters.put(key, strArr);
                }
            }
        }
    }

    private HttpServletRequest getHttpServletRequest() {

        return (HttpServletRequest) super.getRequest();
    }

    @Override
    public String getParameter(final String name) {

        return parameters.get(name) == null ? null : parameters.get(name)[0];

    }

    @Override
    public String[] getParameterValues(final String name) {

        final String value = getParameter(name);
        if (value != null) {
            return new String[] { value };
        }
        return null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {

        return parameters;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Enumeration<String> getParameterNames() {

        return new IteratorEnumeration<String>(parameters.keySet());
    }

    @Override
    public String getQueryString() {

        String queryString = super.getQueryString();
        String s = null;
        try {
            if (queryString != null) {

                // 解密
                s = AesEncryptUtil.desEncrypt(queryString).trim();

                final String[] parms = s.split("&");
                Map<String, String> map = new HashMap<>();
                for (final String str : parms) {
                    final String[] coupleStr = str.split("=");
                    dataParms.put(coupleStr[0], coupleStr[1]);
                    String[] pvalue = new String[1];
                    pvalue[0] = coupleStr[1];
                    parameters.put(coupleStr[0], pvalue);
                    map.put(coupleStr[0], coupleStr[1]);
                }
                try {
                    return objectMapper.writeValueAsString(map);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            return s;
        }
        finally {

        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return Objects.isNull(inputStream) ? null : new BufferedReader(new InputStreamReader(inputStream));
    }

    public BufferedReader getReader2() throws IOException {

        if (firstTime) {
            firstTime();
        }
        final InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(bytes), encoding);
        return new BufferedReader(isr);
    }

    private void firstTime() throws IOException {

        firstTime = false;
        final StringBuilder buffer = new StringBuilder();
        //final BufferedReader reader = this.getHttpServletRequest().getReader();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(this.getHttpServletRequest().getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String originStr = buffer.toString();
        originStr = AesEncryptUtil.desEncrypt(originStr).trim();
        this.inputStream = new ByteArrayInputStream(originStr.getBytes(encoding));
        if (!originStr.isEmpty()) {
            final String method = this.getHttpServletRequest().getMethod();
            if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) {
                bytes = originStr.getBytes(encoding);
            } else {
                final String[] parms = originStr.split("&");
                for (final String str : parms) {
                    final String[] coupleStr = str.split("=");
                    dataParms.put(coupleStr[0], coupleStr[1]);
                }
                bytes = URLDecoder.decode(dataParms.get("data"), encoding).getBytes();
            }
        } else {
            bytes = originStr.getBytes(encoding);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return  Objects.isNull(inputStream) ? null : new DefaultServletInputStream(inputStream);
    }

    public class IteratorEnumeration<E> implements Enumeration {

        private final Iterator<E> iterator;

        public IteratorEnumeration(final Set<E> set) {

            this.iterator = set.iterator();
        }

        @Override
        public boolean hasMoreElements() {

            return iterator.hasNext();
        }

        @Override
        public E nextElement() {

            return iterator.next();
        }

    }
}

class DefaultServletInputStream extends ServletInputStream {

    private InputStream sourceStream;
    private boolean finished = false;

    public DefaultServletInputStream(InputStream sourceStream) {
        this.sourceStream = sourceStream;
    }

    @Override
    public boolean isFinished() {
        return this.finished;
    }

    @Override
    public int read() throws IOException {
        int data = this.sourceStream.read();
        if (data == -1) {
            this.finished = true;
        }

        return data;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {

    }

    @Override
    public int available() throws IOException {
        return this.sourceStream.available();
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.sourceStream.close();
    }
}
