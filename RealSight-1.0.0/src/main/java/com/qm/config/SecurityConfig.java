package com.qm.config;

import com.qm.filter.JwtAuthenticationTokenFilter;
import com.qm.handler.AnonymousAuthenticationHandler;
import com.qm.handler.LoginFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @program: springsecurity-hello
 * @description:
 * @author: ZhangQingMin
 * @create: 2025-05-06 23:59
 **/
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // 开启用注解标识资源需要什么权限访问
public class SecurityConfig {

    // 注入自定义过滤器，在security的config里面配置顺序，放在最前面
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    // 已认证（登录）用户，访问资源权限不足处理器
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AnonymousAuthenticationHandler anonymousAuthenticationHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    /**
     * 非对称加密，存放密码，把这个bean注入到Spring容器里管理
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 使用 BCrypt 密码编码器
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()); // 关闭csrf远程攻击保护

        // 用户表单登录是的异常处理器
        http.formLogin(new Customizer<FormLoginConfigurer<HttpSecurity>>() {
            @Override
            public void customize(FormLoginConfigurer<HttpSecurity> httpSecurityFormLoginConfigurer) {
                httpSecurityFormLoginConfigurer.failureHandler(loginFailureHandler);
            }
        });

        // STATELESS(无状态）：表示应用程序是无状态的，不会创建会话
        http.sessionManagement(configurer ->
                configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.requestMatchers("/user/login").permitAll()
                        .anyRequest().authenticated());
        // 自定义Jwt校验过滤器放在最前面
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);


        http.exceptionHandling(configurer -> configurer
                // 配置自定义的用户权限不足提示的异常处理器
                .accessDeniedHandler(accessDeniedHandler)
                // 配置登录时一些异常的处理
                .authenticationEntryPoint(anonymousAuthenticationHandler));

        return http.build();
    }
}
