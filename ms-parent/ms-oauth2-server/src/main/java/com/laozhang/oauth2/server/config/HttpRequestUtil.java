package com.laozhang.oauth2.server.config;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class HttpRequestUtil {

    public static HttpServletRequest getRequest() {

        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String getAuthHeader(final HttpServletRequest request) {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(authHeader)) {
            throw new RuntimeException();
        }
        return authHeader;
    }

    public static String[] extractAndDecodeHeader(final String header) throws IOException {

        final byte[] base64Token = header.substring(6).getBytes("UTF-8");
        final byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (final IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        final String token = new String(decoded, "UTF-8");

        final int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
    }
}
