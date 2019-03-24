package com.laozhang.max_oauth2_server.controller;


import com.laozhang.max_oauth2_server.constant.Constants;
import com.laozhang.max_oauth2_server.service.OAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 令牌生成
 * */
@RestController
@Slf4j
public class AccessTokenController {

    @Autowired
    private OAuthService oAuthService;

    @RequestMapping("/accessToken")
    public HttpEntity token(HttpServletRequest request) throws OAuthSystemException {
        try{
            // 转为OAuth请求
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
            //检查提交的客户端ID是否正确
            if(!oAuthService.checkClientId(oauthRequest.getClientId())){
                log.error("获取accessToken时，客户端ID错误 client=" + oauthRequest.getClientId());
                OAuthResponse response = OAuthResponse
                        .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION)
                        .buildJSONMessage();
                return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }

            // 检查客户端安全KEY是否正确
            if(!oAuthService.checkClientSecret(oauthRequest.getClientSecret())){
                log.error("ClientSecret不合法 client_secret="+oauthRequest.getClientSecret());
                OAuthResponse response = OAuthResponse
                        .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
                        .setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION)
                        .buildJSONMessage();
                return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }

            String authCode = oauthRequest.getParam(OAuth.OAUTH_CODE);

            /**
             * 此处只校验 AUTHORIZATION_CODE 类型，其他的还有PASSWORD 、REFRESH_TOKEN 和 CLIENT_CREDENTIALS
             * 具体查看 {@link GrantType}
             * */
            if(oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())){
                if(!oAuthService.checkAuthCode(authCode)){
                    OAuthResponse response = OAuthResponse
                            .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                            .setError(OAuthError.TokenResponse.INVALID_GRANT)
                            .setErrorDescription(Constants.INVALID_CODE_DESCRIPTION)
                            .buildJSONMessage();
                    return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
                }
            }

            // 生成Access Token
            OAuthIssuerImpl oAuthIssuer = new OAuthIssuerImpl(new MD5Generator()) ;
            String accessToken = oAuthIssuer.accessToken();
            log.info("服务器生成的accessToken="+accessToken);
            oAuthService.addAccessToken(accessToken,oAuthService.getUsernameByAuthCode(authCode));
            // 移除code，每个code只能使用一次
            String username = oAuthService.removeAuthCode(authCode);
            log.info("服务器移除auth_code="+authCode+" username="+username);

            // 生成OAuth响应
            OAuthResponse response = OAuthASResponse
                    .tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(accessToken)
                    .setExpiresIn(String.valueOf(oAuthService.getExpireIn()))
                    .buildJSONMessage();

            // 根据OAuthResponse 生成ResponseEntity
            return new ResponseEntity(response.getBody(),HttpStatus.valueOf(response.getResponseStatus()));

        }catch (OAuthProblemException e){
            log.error("获取accessToken发生异常e=",e);
            // 构建错误响应
            OAuthResponse response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
                    .buildJSONMessage();
            return new ResponseEntity(response.getBody(),HttpStatus.valueOf(response.getResponseStatus()));
        }
    }
}
