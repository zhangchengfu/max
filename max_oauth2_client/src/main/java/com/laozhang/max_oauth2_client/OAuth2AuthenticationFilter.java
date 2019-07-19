package com.laozhang.max_oauth2_client;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 该 filter 的作用类似于 FormAuthenticationFilter 用于 oauth2 客户端的身份验证控制
 */
@Data
@Slf4j
public class OAuth2AuthenticationFilter extends AuthenticatingFilter {

    //oauth2 authc code参数名
    private String authcCodeParam;
    //客户端id
    private String clientId;
    //服务器端登录成功/失败后重定向到的客户端地址
    private String redirectUrl;
    //oauth2服务器响应类型
    private String responseType;

    private String failureUrl;


    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String code = httpRequest.getParameter(authcCodeParam);
        log.info("OAuth2AuthenticationFilter中拿到的auth_code = " + code);
        return new OAuth2Token(code);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String error = request.getParameter("error");
        String errorDescription = request.getParameter("error_description");
        if (!StringUtils.isEmpty(error)) {
            log.info("error = " + error + "  errorDesc=" + errorDescription);
            WebUtils.issueRedirect(request, response, failureUrl + "?error=" + error + "&error_description=" + errorDescription);
            return false;
        }
        Subject subject = getSubject(request, response);

        log.info("subject.isAuthenticated() == " + subject.isAuthenticated());
        if (!subject.isAuthenticated()) {
            if (StringUtils.isEmpty(request.getParameter(authcCodeParam))) {
                // 如果没有身份认证，且没有authCode,则重定向到服务端授权
                saveRequestAndRedirectToLogin(request, response);
                return false;
            }
        }
        // 执行父类的登录逻辑
        String successURL = getSuccessUrl();
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        Subject subject = getSubject(request, response);
        log.info("是否验证：subject.isAuthenticated() == " + subject.isAuthenticated());
        log.info("是否记住：subject.isRemembered() == " + subject.isRemembered());
        if (subject.isAuthenticated() || subject.isRemembered()) {
            // 重定向到成功页面
            try {
                log.info("重定向到成功页面success。。。。");
                issueSuccessRedirect(request, response);
            } catch (Exception e1) {
                log.info("重定向到成功页面异常=", e1);
            }
        } else {
            try {
                log.info("重定向到失败页面failure。。。。");
                WebUtils.issueRedirect(request, response, failureUrl);
            } catch (IOException e1) {
                log.info("重定向到失败页面异常=", e1);
            }
        }
        return false;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.issueRedirect(request, response, getSuccessUrl());
        return false; // 表示执行链结束
    }
}
