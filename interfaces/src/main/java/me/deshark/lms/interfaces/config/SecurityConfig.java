package me.deshark.lms.interfaces.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

/**
 * @author DE_SHARK
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/auth/register", "/api/auth/login", "/api/auth/test").permitAll()
                    .anyRequest().authenticated()
            )
            // 禁用 CSRF 保护（适合 API 服务）
            .csrf(AbstractHttpConfigurer::disable)
            // 禁用 HTTP Basic 认证
            .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public FilterRegistrationBean<Filter> requestLoggingFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new Filter() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
                Instant start = Instant.now();
                HttpServletRequest req = (HttpServletRequest) request;
                HttpServletResponse res = (HttpServletResponse) response;

                // 记录请求信息
                System.out.printf("\n=== 请求进入 ===\n时间: %s\n方法: %s\nURI: %s\n参数: %s\n用户: %s\n",
                        Instant.now(),
                        req.getMethod(),
                        req.getRequestURI(),
                        req.getQueryString(),
                        req.getRemoteUser());

                // 继续处理请求
                chain.doFilter(request, response);

                // 记录响应信息
                long duration = Duration.between(start, Instant.now()).toMillis();
                System.out.printf("\n=== 响应返回 ===\n时间: %s\n状态: %d\n耗时: %dms\n内容类型: %s\n",
                        Instant.now(),
                        res.getStatus(),
                        duration,
                        res.getContentType());
            }
        });
        registrationBean.setOrder(Integer.MIN_VALUE); // 设置最高优先级
        return registrationBean;
    }
}
