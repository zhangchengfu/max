package com.laozhang.security.gateway.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Token {

    private String access_token;
    private String refresh_token;
    private String token_type;
    private Long expires_in;
    private String scope;

    private LocalDateTime expireTime;

    public Token init() {
        expireTime = LocalDateTime.now().plusSeconds(expires_in - 3);
        return this;
    }

    @JsonIgnore
    public boolean isExpired() {
        return expireTime.isBefore(LocalDateTime.now());
    }
}
