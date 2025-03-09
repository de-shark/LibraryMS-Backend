package me.deshark.lms.infrastructure.security;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.auth.entity.AuthUser;
import me.deshark.lms.domain.repository.auth.UserRepository;
import me.deshark.lms.domain.service.auth.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

/**
 * @author DE_SHARK
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/books").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/books").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                // 禁用表单登录
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    // 自定义用户详情服务
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            AuthUser authUser = userRepository.findByUsername(username);
            return new org.springframework.security.core.userdetails.User(
                    authUser.getUsername(),
                    authUser.getPasswordHash().encryptedValue(),
                    Collections.singleton(new SimpleGrantedAuthority(authUser.getUserRoleType().name()))
            );
        };
    }
}
