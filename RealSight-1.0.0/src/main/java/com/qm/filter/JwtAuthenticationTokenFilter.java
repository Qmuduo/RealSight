package com.qm.filter;

import com.alibaba.fastjson2.JSON;
import com.qm.entity.vo.LoginUser;
import com.qm.exception.CustomerAuthenticationException;
import com.qm.handler.LoginFailureHandler;
import com.qm.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @program: springsecurity-hello
 * @description: 前置过滤器，看看请求有没有携带jwt
 * @author: ZhangQingMin
 * @create: 2025-05-07 13:18
 **/
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 改造前
     */
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String requestURI = request.getRequestURI();// uri就是url的一部分，url:localhost:8080/hello uri:/hello
//        // 1.放行登录接口
//        if (requestURI.equals("/user/login")) { // 放行登录接口
//            filterChain.doFilter(request, response);
//            return;
//        }
//        // 2.判断令牌是不是空的
//        String token = request.getHeader("Authorization");
//        System.out.println("JwtAuthenticationTokenFilter调试jwt：" + token);
//        if (!StringUtils.hasText(token)) {
//            throw new RuntimeException("Token is empty，令牌为空");
//        }
//        // 3.校验令牌
//        Claims claims = JwtUtil.parseToken(token);
//        String loginUserString = claims.getSubject();
//        // 4.用FastJson把json转成LoginUser对象
//        LoginUser loginUser = JSON.parseObject(loginUserString, LoginUser.class);
//
//        if (loginUser != null) {
//            System.out.println("JwtAuthenticationTokenFilter调试loginUser" + loginUser.toString());
//        }
//
//
//        // 5.把验证完的用户信息再次放到security的上下文中
//        // 参数一：用户信息，参数二：密码，参数三：权限
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//        // 没有问题，放行
//        filterChain.doFilter(request, response);
//    }


    // ============================改造后===========================================


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String requestURI = request.getRequestURI();// uri就是url的一部分，url:localhost:8080/hello uri:/hello
            // 1.不是登录接口，就要进行token校验，看看是不是系统发放的正确token
            if (!requestURI.equals("/auth/login") && !requestURI.equals("/auth/register")) {
                this.validateToken(request);
            }
        } catch (AuthenticationException e) {
            loginFailureHandler.onAuthenticationFailure(request, response, e);
            // 发生了异常，代表token有问题，不能在这里放行
        }
        // 是登录接口，直接放行
        filterChain.doFilter(request, response);
    }

    // 用于非登录接口的校验方法
    private void validateToken(HttpServletRequest request) {
        // 2.判断令牌是不是空的
        String token = request.getHeader("Authorization");

        if (ObjectUtils.isEmpty(token)) {
            // 有些人喜欢把参数放在链接里，www.baidu.com/xxx?jwt=xxsgss
            token = request.getParameter("Authorization");
        }
        if (ObjectUtils.isEmpty(token)) { // token还是空的，提示token为空
            throw new CustomerAuthenticationException("token为空");
        }

        // 判断jwt在redis里面有没有过期
        String redisJwt = stringRedisTemplate.opsForValue().get("token_" + token);
        System.out.println("redisJwt = " + redisJwt);
        if (ObjectUtils.isEmpty(redisJwt)) {
            throw new CustomerAuthenticationException("redis中的token已过期！");
        }


        // 3.校验令牌
        LoginUser loginUser = null;
        try {
            Claims claims = JwtUtil.parseToken(token);
            String loginUserString = claims.getSubject();
            // 4.用FastJson把json转成LoginUser对象
            loginUser = JSON.parseObject(loginUserString, LoginUser.class);

        } catch (Exception e) {
            throw new CustomerAuthenticationException("token校验失败");
        }


        // 5.把验证完的用户信息再次放到security的上下文中
        // 参数一：用户信息，参数二：密码，参数三：权限
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    }
}
