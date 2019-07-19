package com.laozhang.max_oauth2_server.controller;

import com.laozhang.max_oauth2_server.constant.Constants;
import com.laozhang.max_oauth2_server.entity.User;
import com.laozhang.max_oauth2_server.service.ClientService;
import com.laozhang.max_oauth2_server.service.OAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 授权处理类
 */
@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private ClientService clientService;

    @RequestMapping("/authorize")
    public Object authroize(Model model, HttpServletRequest request) throws OAuthSystemException, URISyntaxException {

        try {
            // 构建OAuth 授权请求
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);

            // 检查传入的客户端ID是否正确
            if (!oAuthService.checkClientId(oauthRequest.getClientId())) {
                log.error("校验客户端ID失败，ClientID=" + oauthRequest.getClientId());
                OAuthResponse response = OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION)
                        .buildBodyMessage();
                return new ResponseEntity<>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }

            Subject subject = SecurityUtils.getSubject();

            // 如果用户没有登录，跳转登录页面
            if (!subject.isAuthenticated()) {
                if (!login(subject, request)) { // 登录失败
                    model.addAttribute("client", clientService.findByClientId(oauthRequest.getClientId()));
                    //return "oauth2login";
                    return "login";
                }
            }

            String username = ((User) subject.getPrincipal()).getUsername();
            // 生成授权码
            String authorizationCode = null;
            // resopnseType 目前仅支持CODE， 另外还有TOKEN
            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);

            if (responseType.equals(ResponseType.CODE.toString())) {
                OAuthIssuerImpl oAuthIssuer = new OAuthIssuerImpl(new MD5Generator());
                authorizationCode = oAuthIssuer.authorizationCode();
                log.info("服务端生成的授权码=" + authorizationCode);
                oAuthService.addAuthCode(authorizationCode, username);
            }

            // 构建OAuth响应
            OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                    OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);

            // 设置授权码
            builder.setCode(authorizationCode);
            // 得到 到客户端请求地址中的redirect_uri重定向地址
            String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);

            // 构建响应
            OAuthResponse response = builder.location(redirectURI).buildQueryMessage();

            //根据OAuthResponse返回ResponseEntity响应
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity<>(headers, HttpStatus.valueOf(response.getResponseStatus()));
        } catch (OAuthProblemException e) {
            // 处理出错
            String redirectUri = e.getRedirectUri();
            if (OAuthUtils.isEmpty(redirectUri)) {
                // 告诉客户端没有传入回调地址
                return new ResponseEntity<>("OAuth callback url needs to be provider by client!!", HttpStatus.NOT_FOUND);
            }
            // 返回消息错误（如?error=）
            OAuthResponse response =
                    OAuthResponse.errorResponse(HttpServletResponse.SC_FOUND)
                            .error(e).location(redirectUri).buildQueryMessage();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity<>(headers, HttpStatus.valueOf(response.getResponseStatus()));
        }

    }

    private boolean login(Subject subject, HttpServletRequest request) {
        if ("get".equalsIgnoreCase(request.getMethod())) {
            return false;
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return false;
        }

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            token.setRememberMe(true);
            subject.login(token);
            return true;
        } catch (Exception e) {
            log.error("登录异常e={}", e);
            request.setAttribute("error", "登录失败:" + e.getClass().getName());
            return false;
        }
    }
}
