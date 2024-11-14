package com.travelauth.interceptor;

import com.travelauth.jwt.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: dapeng
 * @Date: 2024/11/11/14:59
 * @Description:
 */
@Component
public class BasicAuthInterceptor implements HandlerInterceptor {
    private static final String AUTH_HEADER = "Authorization";
    private static final String BASIC_PREFIX = "Basic ";
    private static final String BASIC_USER = "123456";
    private static final String BASIC_PASSWORD = "123456";
    public static final String BEAR_PREFIX = "Bearer ";
    private static final Set<String> EXCLUDE_PATHS = new HashSet<>();

    static {
        // 添加不需要认证的接口路径
        EXCLUDE_PATHS.add("/public");
        EXCLUDE_PATHS.add("/api/open"); // 例如：开放的 API 接口
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String requestURI = request.getRequestURI();

        // 检查当前请求的 URI 是否在不需要认证的路径列表中
        if (EXCLUDE_PATHS.contains(requestURI)) {
            return true; // 不需要认证，继续处理请求
        }

        String authHeader = request.getHeader(AUTH_HEADER);

        if(authHeader != null && authHeader.startsWith(BASIC_PREFIX)) {
            String basic64Credentials = authHeader.substring(BASIC_PREFIX.length()).trim();
            String credentials = new String(Base64.getDecoder().decode(basic64Credentials));
            // credentials 的格式是 username:password
            final String[] values = credentials.split(":", 2);

            String username = values[0];
            String password = values[1];

            // 在这里验证用户名和密码
            if (isValidUser(username, password)) {
                return true; // 验证通过，继续处理请求
            }
        } else if (authHeader != null && authHeader.startsWith(BEAR_PREFIX)) {
            String username = JwtUtil.getClaim(authHeader, JwtUtil.ACCOUNT);
            if (!username.isEmpty()) {
                return true;
            }
        }
        // 验证失败，返回 401 Unauthorized
        response.setHeader("WWW-Authenticate", "Basic realm=\"Access to the staging site\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Unauthorized access. Invalid credentials.\"}");
        return false;
    }

    private boolean isValidUser(String username, String password) {
        return BASIC_USER.equals(username) && BASIC_PASSWORD.equals(password);
    }
}
