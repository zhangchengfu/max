package com.laozhang.max_springsecurity.filter;

import com.laozhang.max_springsecurity.entity.SysRole;
import com.laozhang.max_springsecurity.entity.SysUser;
import com.laozhang.max_springsecurity.entity.SysUserRole;
import com.laozhang.max_springsecurity.service.SysRoleService;
import com.laozhang.max_springsecurity.service.SysUserRoleService;
import com.laozhang.max_springsecurity.service.SysUserService;
import com.laozhang.max_springsecurity.sms.SmsCodeAuthenticationToken;
import com.laozhang.max_springsecurity.utils.JwtUtils;
import com.laozhang.max_springsecurity.utils.SpringBeanFactoryUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * JWT过滤器
 * 在访问受限URL前，验证JWT token
 * @author jitwxs
 * @since 2018/5/2 20:43
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    private static final PathMatcher pathMatcher = new AntPathMatcher();

    static final String USER_ID_KEY = "USER_ID";

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isProtectedUrl(request)) {
            Authentication authentication = getAuthentication(request);

            // 如果验证失败，设置异常；否则将UsernamePasswordAuthenticationToken注入到框架中
            if (authentication == null) {
                //手动设置异常
                request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new AuthenticationCredentialsNotFoundException("权限认证失败"));
                // 转发到错误Url
                request.getRequestDispatcher("/login/error").forward(request, response);
            } else {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }
        }
    }

    /**
     * 验证token
     * @return 成功返回包含角色的UsernamePasswordAuthenticationToken；失败返回null
     */
    private Authentication getAuthentication(HttpServletRequest request) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String token = request.getHeader("Authorization");

        if (token != null) {
            Map map = JwtUtils.unSign(token);

            Integer userId = (Integer) map.get(USER_ID_KEY);
            if (userId != null) {
                // 将用户id放入request中
                request.setAttribute(USER_ID_KEY, userId);

                // 从数据库中获取用户角色
                SysUserService sysUserService = SpringBeanFactoryUtils.getBean(SysUserService.class);
                SysUser sysUser = sysUserService.selectById(userId);
                SysUserRoleService userRoleService = SpringBeanFactoryUtils.getBean(SysUserRoleService.class);
                SysRoleService roleService = SpringBeanFactoryUtils.getBean(SysRoleService.class);
                List<SysUserRole> userRoles = userRoleService.listByUserId(userId);
                for (SysUserRole userRole : userRoles) {
                    SysRole role = roleService.selectById(userRole.getRoleId());
                    authorities.add(new SimpleGrantedAuthority(role.getName()));
                }

                // 这里直接注入角色，因为JWT已经验证了用户合法性，所以principal和credentials直接为null即可
                return new SmsCodeAuthenticationToken(sysUser.getName(), authorities);
            }
            return null;
        }
        return null;
    }

    //只对/api/*下请求拦截
    private boolean isProtectedUrl(HttpServletRequest request) {
        return pathMatcher.match("/api/**", request.getServletPath());
    }
}