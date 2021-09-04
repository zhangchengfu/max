package com.laozhang.oauth2.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Resource
    private ClientDetailsService clientDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public MyAuthenticationFilter() {
        /*super(new AntPathRequestMatcher("/login2", "POST"));*/
        RequestMatcher requiresAuthenticationRequestMatcher = new AntPathRequestMatcher("/login", "POST");
        setRequiresAuthenticationRequestMatcher(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }

        String[] tokens = new String[0];
        try {
            tokens = HttpRequestUtil.extractAndDecodeHeader(header);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String clientId = tokens[0];
        String clientSecret = tokens[1];
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
        } else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
        }

        if (request.getContentType() == null || request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper objectMapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            InputStream inputStream = null;
            Map<String, String> authenticationBean = null;

            try {
                inputStream = request.getInputStream();
                authenticationBean = objectMapper.readValue(inputStream, Map.class);
            } catch (final Exception e) {

            }
            String username = authenticationBean.get("username");
            String password = authenticationBean.get("password");
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    username, password);
            Authentication authentication = this.getAuthenticationManager().authenticate(token);
            return authentication;
        } else {
            return super.attemptAuthentication(request, response);
            //return super.getAuthenticationManager().authenticate(null);
        }

    }
}
