/**
 * 
 */
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

/**
 * @author jojo
 *
 */
@Slf4j
@Component
public class AuthorizationFilter extends ZuulFilter {

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
		
		log.info("authorization start");
		
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		
		if(isNeedAuth(request)) {
			
			TokenInfo tokenInfo = (TokenInfo)request.getAttribute("tokenInfo");
			
			if(tokenInfo != null) {
				if (tokenInfo.isActive()) {
					if(!hasPermission(tokenInfo, request)) {
						log.info("audit log update fail 403");
						handleError(403, requestContext);
					}
				} else {
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
					} catch (Exception e) {
						requestContext.setSendZuulResponse(false);
						requestContext.setResponseStatusCode(500);
						requestContext.setResponseBody("{\"message\":\"refresh fail\"}");
						requestContext.getResponse().setContentType("application/json");
					}
				}

				requestContext.addZuulRequestHeader("username", tokenInfo.getUser_name());
			}else {
				if(!StringUtils.startsWith(request.getRequestURI(), "/token")) {
					log.info("audit log update fail 401");
					handleError(401, requestContext);
				}
			}
		}

		return null;
	}
	
	private void handleError(int status, RequestContext requestContext) {
		requestContext.getResponse().setContentType("application/json");
		requestContext.setResponseStatusCode(status);
		requestContext.setResponseBody("{\"message\":\"auth fail\"}");
		requestContext.setSendZuulResponse(false);
	}

	private boolean hasPermission(TokenInfo tokenInfo, HttpServletRequest request) {
		return true; //RandomUtils.nextInt() % 2 == 0;
	}

	private boolean isNeedAuth(HttpServletRequest request) {
		return true;
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
		return 3;
	}

}
