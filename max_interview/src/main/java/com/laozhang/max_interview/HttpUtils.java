package com.laozhang.max_interview;

import okhttp3.*;

import java.util.Map;
import java.util.Objects;

public class HttpUtils {

    private static OkHttpClient client;

    static {
        if (client == null) {
            client = new OkHttpClient();
        }
    }

    public static String doGet(String url, Map<String, Object> params) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(),String.valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder().url(urlBuilder.build()).get().build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String postForm(String urlParam, Map<String, Object> params, Files... files) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (files != null && files.length > 0) {
            for (Files file : files) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file.getFile());
                builder.addFormDataPart(file.getName(), file.getFile().getName(), fileBody);
            }
        }
        for (String key : params.keySet()) {
            builder.addFormDataPart(key, String.valueOf(params.get(key)));
        }
        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(urlParam)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String doPost(String url, String json) {
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static String postForm(String urlParam, Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (String key : params.keySet()) {
            builder.addFormDataPart(key, String.valueOf(params.get(key)));
        }
        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(urlParam)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
