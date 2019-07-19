package com.laozhang.max_oauth2_client;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 用于存储 oauth2 服务端返回的 auth code
 */
@Data
public class OAuth2Token implements AuthenticationToken {

    private String authCode;
    private String principal;

    public OAuth2Token(String authCode) {
        this.authCode = authCode;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return authCode;
    }
}
