package com.laozhang.max_interview;


import freemarker.cache.ByteArrayTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.*;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;


public class HelpUtil {
    @SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(HelpUtil.class);

    public static String createDoc(Map<String, Object> dataMap, String path) {
        File parentDir = new File("TEMP_FILE_PATH");
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        String resultFile = "TEMP_FILE_PATH" + UUID.randomUUID() + Doc2PdfUtil.DOC_SUFFIX;//输出doc文件路径
        @SuppressWarnings("deprecation")
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        Template t = null;
        try {
            Resource resource = ResourceUtils.getResource(path);
            byte[] bytes = ResourceUtils.getBytes(resource);
            ByteArrayTemplateLoader byteArrayTemplateLoader = new ByteArrayTemplateLoader();
            byteArrayTemplateLoader.putTemplate(resource.getFilename(), bytes);
            configuration.setTemplateLoader(byteArrayTemplateLoader);
            // test.ftl为要装载的模板
            t = configuration.getTemplate(resource.getFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 输出文档路径及名称
        File outFile = new File(resultFile);
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outFile), "utf-8"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            t.process(dataMap, out);
            if (out != null) {
                out.close();
            }
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultFile;
    }



    /**
     * 让value 保留 decimal 位小数
     *
     * @param value
     * @param decimal
     * @return
     */
    public static double cutDecimals(double value, int decimal) {
        BigDecimal b = new BigDecimal(value);
        value = b.setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
        return value;
    }

    // 不足两位的，补齐0
    public static String formatDecimal(Double value) {
        if (value == null) {
            return "";
        } else {
            String valueStr = value.toString();
            int len = valueStr.substring(valueStr.lastIndexOf(".") + 1, valueStr.length()).length();
            if (len == 1) {
                valueStr += "0";
            }
            return valueStr;
        }
    }
}
