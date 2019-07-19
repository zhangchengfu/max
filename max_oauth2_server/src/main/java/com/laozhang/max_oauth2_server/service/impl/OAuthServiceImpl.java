package com.laozhang.max_oauth2_server.service.impl;

import com.laozhang.max_oauth2_server.service.ClientService;
import com.laozhang.max_oauth2_server.service.OAuthService;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * OAuth相关处理：authCode 添加/移除  accessToken的添加/移除 。。。。。
 */
@Service
public class OAuthServiceImpl implements OAuthService {

    private Cache<String, String> cache;

    @Autowired
    ClientService clientService;

    @Autowired
    public OAuthServiceImpl(EhCacheManager cacheManager) {
        this.cache = cacheManager.getCache("code-cache");
    }

    @Override
    public void addAuthCode(String authCode, String username) {
        cache.put(authCode, username);
    }

    @Override
    public String removeAuthCode(String authCode) {
        return cache.remove(authCode);
    }

    @Override
    public void addAccessToken(String accessToken, String username) {
        cache.put(accessToken, username);
    }

    @Override
    public boolean checkAuthCode(String authCode) {
        return Objects.nonNull(cache.get(authCode));
    }

    @Override
    public boolean checkAccessToken(String accessToken) {
        return Objects.nonNull(cache.get(accessToken));
    }

    @Override
    public String getUsernameByAuthCode(String authCode) {
        return cache.get(authCode);
    }

    @Override
    public String getUsernameByAccessToken(String accessToken) {
        return cache.get(accessToken);
    }

    @Override
    public long getExpireIn() {
        // 此处模拟写成固定值
        return 3600L;
    }

    @Override
    public boolean checkClientId(String clientId) {
        return Objects.nonNull(clientService.findByClientId(clientId));
    }

    @Override
    public boolean checkClientSecret(String clientSecret) {
        return Objects.nonNull(clientService.findByClientSecret(clientSecret));
    }
}
