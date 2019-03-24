package com.laozhang.max_oauth2_client.config;

import com.laozhang.max_oauth2_client.OAuth2AuthenticationFilter;
import com.laozhang.max_oauth2_client.OAuth2Realm;
import lombok.Data;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "oauth2")
public class ShiroClientConfig {

    // 注意: /r/n前不能有空格
    private static final String CRLF = "\r\n";

    private static final String AUTHENTICATION_CACHE = "authenticationCache";
    private String authenticationCache = AUTHENTICATION_CACHE;

    private static final String AUTHORIZATION_CACHE = "authorizationCache";
    private String authorizationCache = AUTHORIZATION_CACHE;

    @Value("oauth2.clientId")
    private String clientId;

    @Value("oauth2.clientSecret")
    private String clientSecret;

    @Value("oauth2.redirectUrl")
    private String redirectUrl;

    @Value("oauth2.userInfoUrl")
    private String userInfoUrl;

    @Value("oauth2.accessTokenUrl")
    private String accessTokenUrl;

    @Value("oauth2.authorizeUrl")
    private String authorizeUrl;

    @Bean(name="oAuth2Realm")
    public OAuth2Realm getUserAuthcRealm() {
        OAuth2Realm oAuth2Realm = new OAuth2Realm();
        oAuth2Realm.setCachingEnabled(true);
        oAuth2Realm.setAuthenticationCachingEnabled(true);
        oAuth2Realm.setAuthenticationCacheName(authenticationCache);
        oAuth2Realm.setAuthorizationCachingEnabled(true);
        oAuth2Realm.setAuthorizationCacheName(authorizationCache);
        oAuth2Realm.setClientId(clientId);
        oAuth2Realm.setClientSecret(clientSecret);
        oAuth2Realm.setAccessTokenUrl(accessTokenUrl);
        oAuth2Realm.setUserInfoUrl(userInfoUrl);
        oAuth2Realm.setRedirectUrl(redirectUrl);
        return oAuth2Realm;
    }

    public Map<String, Filter> createFilterChainMap() {
        Map<String, Filter> filters = new LinkedHashMap<>();
        OAuth2AuthenticationFilter oAuth2AuthenticationFilter =
                new OAuth2AuthenticationFilter();
        oAuth2AuthenticationFilter.setAuthcCodeParam(OAuth.OAUTH_CODE);
        oAuth2AuthenticationFilter.setResponseType(OAuth.OAUTH_CODE);
        oAuth2AuthenticationFilter.setFailureUrl("/oauth2Failure");
        filters.put("oauth2Authc",oAuth2AuthenticationFilter);
        return filters;
    }

    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean createShiroSecurityFilterFactory() {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(createSecurityManager());
        shiroFilter.setLoginUrl(authorizeUrl+"?client_id="+clientId+"&response_type=" + OAuth.OAUTH_CODE +
                                "&redirect_uri=" + redirectUrl);
        shiroFilter.setSuccessUrl("/oauth2-login");
        shiroFilter.setFilters(createFilterChainMap());
        shiroFilter.setFilterChainDefinitions(loadFilterChainDefinitions());
        return shiroFilter;
    }

    @Bean(name="securityMananger")
    public DefaultWebSecurityManager createSecurityManager() {
        DefaultWebSecurityManager securityMananger = new DefaultWebSecurityManager();
        securityMananger.setRealm(getUserAuthcRealm());
        securityMananger.setCacheManager(createEhcacheManager());
       // securityMananger.setRememberMeManager(createCookieRemmberMananger());
        return securityMananger;
    }
    @Bean(name="cacheManager")
    public EhCacheManager createEhcacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        String path = "classpath:ehcache.xml";
        cacheManager.setCacheManagerConfigFile(path);
        return cacheManager;
    }

    public String loadFilterChainDefinitions() {
        StringBuffer sb = new StringBuffer();
        //	sb.append(getFixedAuthRule());//固定权限，采用读取配置文件
        sb.append("/ = anon").append(CRLF);
        sb.append("/oauth2Failure = anon").append(CRLF);
        sb.append("/oauth2-login = oauth2Authc").append(CRLF);
        sb.append("/logout = logout").append(CRLF);
        sb.append("/** = user").append(CRLF);

        return sb.toString();
    }
}
