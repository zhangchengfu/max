package com.laozhang.security.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class OAuthFilter extends ZuulFilter {
	
	private RestTemplate restTemplate = new RestTemplate();

	/* (non-Javadoc)
	 * @see com.netflix.zuul.IZuulFilter#shouldFilter()
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.netflix.zuul.IZuulFilter#run()
	 */
	@Override
	public Object run() throws ZuulException {
		
		log.info("oauth start");
		
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		
		if(StringUtils.startsWith(request.getRequestURI(), "/token")) {
			return null;
		}
		
		String authHeader = request.getHeader("Authorization");
		
		if(StringUtils.isBlank(authHeader)) {
			return null;
		}
		
		if(!StringUtils.startsWithIgnoreCase(authHeader, "bearer ")) {
			return null;
		}
		
		try {
			
			TokenInfo info = getTokenInfo(authHeader);
			request.setAttribute("tokenInfo", info);
			
		} catch (Exception e) {
			log.error("get token info fail", e);
			// 刷新token
			String oauthServiceUrl = "http://localhost:9090/oauth/token";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setBasicAuth("orderApp", "123456");

			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("grant_type", "refresh_token");
			String refresh_token = request.getHeader("refresh_token");
			params.add("refresh_token", refresh_token);

			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

			try {
				ResponseEntity<Token> newToken = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, Token.class);
				requestContext.addZuulRequestHeader("Authorization", "bearer " + newToken.getBody().getAccess_token());
				requestContext.getResponse().setHeader("access_token", newToken.getBody().getAccess_token());
				TokenInfo info = getTokenInfo("bearer " + newToken.getBody().getAccess_token());
				request.setAttribute("tokenInfo", info);
			} catch (Exception e1) {
				requestContext.setSendZuulResponse(false);
				requestContext.setResponseStatusCode(500);
				requestContext.setResponseBody("{\"message\":\"refresh fail\"}");
				requestContext.getResponse().setContentType("application/json");
			}
			return null;
		}
		
		return null;
	}

	private TokenInfo getTokenInfo(String authHeader) {
		
		String token = StringUtils.substringAfter(authHeader, "bearer ");
		String oauthServiceUrl = "http://localhost:9090/oauth/check_token";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBasicAuth("orderApp", "123456");
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("token", token);
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
		
		ResponseEntity<TokenInfo> response = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, TokenInfo.class);
		
		log.info("token info :" + response.getBody().toString());
		
		return response.getBody();
	}

	/* (non-Javadoc)
	 * @see com.netflix.zuul.ZuulFilter#filterType()
	 */
	@Override
	public String filterType() {
		return "pre";
	}

	/* (non-Javadoc)
	 * @see com.netflix.zuul.ZuulFilter#filterOrder()
	 */
	@Override
	public int filterOrder() {
		return 1;
	}

}
