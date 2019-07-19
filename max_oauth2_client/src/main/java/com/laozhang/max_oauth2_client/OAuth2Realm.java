package com.laozhang.max_oauth2_client;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

@Data
@Slf4j
public class OAuth2Realm extends AuthorizingRealm {

    private String clientId;
    private String clientSecret;
    private String accessTokenUrl;
    private String userInfoUrl;
    private String redirectUrl;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token; //此Realm 只支持 OAuth2Token 类型
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }

    /**
     * 身份认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        OAuth2Token oAuth2Token = (OAuth2Token) token;
        String code = oAuth2Token.getAuthCode(); //获取 auth code
        log.info("客户端Relam中获取到的auth_code=" + code);
        String username = getUsernameByCode(code); // 获取用户名
        log.info("客户端Relam中获取到的username=" + username);
        oAuth2Token.setPrincipal(username);
        SimpleAuthenticationInfo authenticationInfo =
                new SimpleAuthenticationInfo(username, code, getName());
        return authenticationInfo;
    }

    private String getUsernameByCode(String code) {
        try {
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthClientRequest accessTokenRequest = OAuthClientRequest
                    .tokenLocation(accessTokenUrl)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(clientId).setClientSecret(clientSecret)
                    .setCode(code).setRedirectURI(redirectUrl)
                    .buildQueryMessage();
            //获取 access token
            log.info("clientId=" + clientId + "   clientsecret=" + clientSecret);
            OAuthAccessTokenResponse oAuthResponse =
                    oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
            String accessToken = oAuthResponse.getAccessToken();
            log.info("客户端Realm中获取到accessToken=" + accessToken);
            Long expiresIn = oAuthResponse.getExpiresIn(); // 获取过期时间，此处暂时没用到
            //获取 user info
            OAuthClientRequest userInfoRequest =
                    new OAuthBearerClientRequest(userInfoUrl)
                            .setAccessToken(accessToken).buildQueryMessage();
            OAuthResourceResponse resourceResponse = oAuthClient.resource(
                    userInfoRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            String username = resourceResponse.getBody();
            return username;
        } catch (Exception e) {
            log.info("获取用户名发生异常e=", e);
            throw new OAuth2AuthenticationException(e);
        }
    }
}
