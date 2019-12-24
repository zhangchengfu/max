package com.laozhang.max_encrypt.common;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "myFilter")
@Component
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        MyResponseWrapper wrapperResponse = new MyResponseWrapper((HttpServletResponse)response);

        MyRequestWrapper requestWrapper = new MyRequestWrapper((HttpServletRequest)request);
        filterChain.doFilter(requestWrapper, response);

        filterChain.doFilter(request, wrapperResponse);
        byte[] content = wrapperResponse.getContent();
        if (content.length > 0) {
            String str = new String(content, "UTF-8");
            ServletOutputStream out = response.getOutputStream();
            try {
                str = AesEncryptUtil.encrypt(str);
            } catch (Exception e) {

            }
            out.write(str.getBytes());
            out.flush();
            out.close();
        }
    }

    @Override
    public void destroy() {

    }
}
