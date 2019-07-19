package com.laozhang.max_oauth2_client;

import org.apache.shiro.authc.AuthenticationException;

public class OAuth2AuthenticationException extends AuthenticationException {

    public OAuth2AuthenticationException(Throwable t) {
        super(t);
    }
}
