package com.laozhang.max_oauth2_server.service;

public interface OAuthService {

    void addAuthCode(String authCode, String username);// 添加 auth code

    String removeAuthCode(String authCode); // 移除auth code

    void addAccessToken(String accessToken, String username); // 添加 access token

    boolean checkAuthCode(String authCode); // 验证 auth code 是否有效

    boolean checkAccessToken(String accessToken); // 验证 access token 是否有效

    String getUsernameByAuthCode(String authCode);// 根据 auth code 获取用户名

    String getUsernameByAccessToken(String accessToken);// 根据 access token 获取用户名

    long getExpireIn();//auth code / access token 过期时间

    boolean checkClientId(String clientId);// 检查客户端 id 是否存在

    boolean checkClientSecret(String clientSecret);// 检查客户端安全 KEY 是否存在

}
