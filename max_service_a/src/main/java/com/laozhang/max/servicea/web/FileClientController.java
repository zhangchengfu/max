package com.laozhang.max.servicea.web;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("file")
public class FileClientController {
    private static final Logger log = LoggerFactory.getLogger(FileClientController.class);

    /**
     * 附件上传，通过http上传到另一个服务器
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Object upload(@RequestParam("file") MultipartFile file,
                         HttpServletRequest request) throws Exception {
        InputStream is = null;
        FileOutputStream fos = null;
        String url = "http://localhost:8080/file/upload";
        // 创建HttpClients实体类
        CloseableHttpClient httpClient = HttpClients.createDefault();
        int timeOut = 40000;
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeOut).setSocketTimeout(timeOut).build();
        CloseableHttpResponse httpResponse = null;
        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            log.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            log.info("文件的后缀名为：" + suffixName);
            // 获取文件前缀名
            String preName = fileName.substring(0, fileName.lastIndexOf("."));
            log.info("文件的前缀名为：" + preName);

            // http上传文件
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
            multipartEntityBuilder.addBinaryBody("file", file.getInputStream(),
                    ContentType.DEFAULT_BINARY, file.getOriginalFilename());
            HttpEntity httpEntity = multipartEntityBuilder.build();
            httpPost.setEntity(httpEntity);
            log.info("文件开始开始上传");
            httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new Exception();
            }
            HttpEntity responseEntity = httpResponse.getEntity();
            // 解析返回信息
            JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(responseEntity));

            return jsonObject;
        } catch (IllegalStateException e) {
            throw new IllegalStateException();
        } catch (IOException e) {
            throw new IOException();
        } catch (Exception e) {
            throw new Exception();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("关闭httpClient异常：" + e.getMessage(), e);
            }
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 附件下载,通过http从另一个服务器下载附件
     * @param attachmentId 附件id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public Object download(@RequestParam(value = "attachmentId",required = true) Integer attachmentId,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (attachmentId != null) {

            String url = "http://localhost:8080/file/download";
            // 创建HttpClients实体类
            CloseableHttpClient httpClient = HttpClients.createDefault();
            int timeOut = 40000;
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeOut).setSocketTimeout(timeOut).build();
            CloseableHttpResponse httpResponse = null;
            InputStream fis = null;
            BufferedInputStream bis = null;
            try {
                // http请求，下载文件
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("attachmentId",attachmentId.toString()));
                URIBuilder builder = new URIBuilder(url);
                builder.setParameters(pairs);
                HttpGet get = new HttpGet(builder.build());
                get.setConfig(requestConfig);
                httpResponse = httpClient.execute(get);
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new Exception();
                }
                fis = httpResponse.getEntity().getContent();
                String fileName = "**";
                String filename = new String(fileName.getBytes(),"ISO-8859-1");
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + filename);// 设置文件名
                byte[] buffer = new byte[1024];
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                os.flush();
                os.close();
                return null;

            } catch (Exception e) {
                log.error(e.getMessage());
                throw new Exception();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
