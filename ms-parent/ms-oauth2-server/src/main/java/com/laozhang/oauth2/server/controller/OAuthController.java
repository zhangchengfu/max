package com.laozhang.oauth2.server.controller;

import com.laozhang.commons.model.domain.ResultInfo;
import com.laozhang.commons.utils.ResultInfoUtil;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Oauth2 控制器
 */
@RestController
@RequestMapping("oauth")
public class OAuthController {

    @Resource
    private TokenEndpoint tokenEndpoint;

    @Resource
    private HttpServletRequest request;

    /*@Resource
    private SessionRepository<? extends ExpiringSession> repository;

    @PostMapping(value = "/gettoken", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Object creatToken(
            @RequestParam(name = "appid") String appid,
            @RequestParam(name = "secret") String secret) {

        Integer seconds = 3600;
        ExpiringSession session = repository.createSession();
        session.setMaxInactiveIntervalInSeconds(seconds);
        session.setAttribute("user", "test");
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("accessToken", session.getId());
        jsonObject.append("expiresIn", seconds);
        FindByIndexNameSessionRepository<ExpiringSession> sp = (FindByIndexNameSessionRepository<ExpiringSession>) repository;
        sp.save(session);
        return jsonObject;
    }*/

    @RequestMapping("token")
    //@PostMapping("token")
    public Object postAccessToken(Principal principal, @RequestParam Map<String, String> parameters)
            throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return accessToken;
        //return custom(accessToken);
    }

    /**
     * 自定义 Token 返回对象
     *
     * @param accessToken
     * @return
     */
    private ResultInfo custom(OAuth2AccessToken accessToken) {
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        Map<String, Object> data = new LinkedHashMap(token.getAdditionalInformation());
        data.put("accessToken", token.getValue());
        data.put("expireIn", token.getExpiresIn());
        data.put("scopes", token.getScope());
        if (token.getRefreshToken() != null) {
            data.put("refreshToken", token.getRefreshToken().getValue());
        }
        return ResultInfoUtil.buildSuccess(request.getServletPath(), data);
    }

}