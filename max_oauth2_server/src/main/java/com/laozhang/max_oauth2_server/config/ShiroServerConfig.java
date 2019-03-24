package com.laozhang.max_oauth2_server.config;
import com.laozhang.max_oauth2_server.credentials.RetryLimitHashedCredentialsMatcher;
import com.laozhang.max_oauth2_server.realm.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroServerConfig {
	
	// 注意/r/n前不能有空格
	private static final String CRLF = "\r\n";

	private static final String MD5 = "md5";
		
	@Bean(name="hashedCredMatcher")
	public HashedCredentialsMatcher createHashCredentialsMatcher() {
		RetryLimitHashedCredentialsMatcher hcm = new RetryLimitHashedCredentialsMatcher();
		hcm.setHashAlgorithmName(MD5);
		hcm.setStoredCredentialsHexEncoded(true);
        hcm.setCacheManager(createEhcacheManager());
		return hcm;
	}

	@Bean(name="userAuthc")
	public UserRealm getUserAuthcRealm() {
		UserRealm uar = new UserRealm();
		uar.setCredentialsMatcher(createHashCredentialsMatcher());
		return uar;
	}
	
	@Bean(name="rememberCookies")
	public SimpleCookie createSimpleCookies() {
		SimpleCookie sc = new SimpleCookie("rememberMe");
		sc.setHttpOnly(true);
		sc.setMaxAge(5);
		return sc;
	}
	
	@Bean(name="cookieRemmberMananger")
	public CookieRememberMeManager createCookieRemmberMananger() {
		CookieRememberMeManager crm = new CookieRememberMeManager();
		crm.setCookie(createSimpleCookies());
		return crm;
	}
	
	/*public MemorySessionDAO createSessionDAO() {
		MemorySessionDAO sessionDao = new MemorySessionDAO();
		return null;
	}*/
	
	@Bean(name="cacheManager")
	public EhCacheManager createEhcacheManager() {
		EhCacheManager cacheManager = new EhCacheManager();
		String path = "classpath:ehcache.xml";
		cacheManager.setCacheManagerConfigFile(path);
		return cacheManager;
	}
	
	@Bean(name="securityMananger")
	public DefaultWebSecurityManager createSecurityManager() {
		DefaultWebSecurityManager securityMananger = new DefaultWebSecurityManager();
		securityMananger.setRealm(getUserAuthcRealm());
		securityMananger.setCacheManager(createEhcacheManager());
		securityMananger.setRememberMeManager(createCookieRemmberMananger());
		return securityMananger;
	}

	/**
	 * 自定义的shiro相关的Filter在这里做统一处理
	 * */
	public Map<String, Filter> createFilterChainMap() {
		Map<String, Filter> filters = new LinkedHashMap<>();
		return filters;
	}
	
	/**
	 * 读取ini文获得固定权限
	 * */
	public String loadFilterChainDefinitions() {
		StringBuffer sb = new StringBuffer();
		//	sb.append(getFixedAuthRule());//固定权限，采用读取配置文件
		sb.append("/ = anon").append(CRLF);
        sb.append("/login = authc").append(CRLF);
        sb.append("/logout = logout").append(CRLF);
        sb.append("/authorize=anon").append(CRLF);
        sb.append("/accessToken=anon").append(CRLF);
        sb.append("/userInfo=anon").append(CRLF);
        sb.append("/** = user").append(CRLF);

		return sb.toString();
	}
	

	
	 @Bean(name="shiroFilter")
	 public ShiroFilterFactoryBean createShiroSecurityFilterFactory() {
		 ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		 shiroFilter.setSecurityManager(createSecurityManager());
		 shiroFilter.setLoginUrl("/login");
		 //shiroFilter.setFilters(createFilterChainMap());
         //注意过滤器配置顺序 不能颠倒
  /*     filterChainDefinitionMap.put("/static/**", "anon");
         filterChainDefinitionMap.put("/templates/**", "anon");
         filterChainDefinitionMap.put("/g/unLogin", "anon");
         filterChainDefinitionMap.put("/g/login", "loginFtr");
         filterChainDefinitionMap.put("/g/logout", "authc");
         filterChainDefinitionMap.put("/g/unauthorized", "anon");
         filterChainDefinitionMap.put("/**", "permsFilter");*/
         shiroFilter.setFilterChainDefinitions(loadFilterChainDefinitions());
		 return shiroFilter;
	 }
	
}
