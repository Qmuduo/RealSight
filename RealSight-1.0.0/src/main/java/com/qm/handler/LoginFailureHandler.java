package com.qm.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qm.common.Result;
import com.qm.exception.CustomerAuthenticationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @program: springsecurity-hello
 * @description: 用户认证jwt校验失败的处理器
 * @author: ZhangQingMin
 * @create: 2025-05-09 14:28
 **/
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        // 设置响应类型
        response.setContentType("application/json;charset=utf-8");
        String message = "";
        int code = 500;


        if (authenticationException instanceof AccountExpiredException) {
            // 到LoginUser里可以去处理用户过期的情况，比如数据库表增加一个过期字段，LoginUser实现了一个父类方法，处理的就是这个
            message = "用户过期，登录失败";
        } else if (authenticationException instanceof BadCredentialsException) {
            // 密码错误
            message = "用户名或密码错误，请重新输入";
        } else if (authenticationException instanceof CredentialsExpiredException) {
            // 到LoginUser里可以去处理用户过期的情况，比如数据库表增加一个过期字段，LoginUser实现了一个父类方法，处理的就是这个
            message = "密码过期，登录失败";
        } else if (authenticationException instanceof DisabledException) {
            // 到LoginUser里可以去处理用户禁用的情况，比如数据库表增加一个过期字段，LoginUser实现了一个父类方法，处理的就是这个
            message = "账户被系统禁用，登录失败";
        } else if (authenticationException instanceof LockedException) {
            // 到LoginUser里可以去处理用户锁定的情况，比如数据库表增加一个过期字段，LoginUser实现了一个父类方法，处理的就是这个
            message = "账户被锁定，登录失败";
        } else if (authenticationException instanceof InternalAuthenticationServiceException) {
            message = "账户不存在，登录失败";
        } else if (authenticationException instanceof CustomerAuthenticationException) {
            message = authenticationException.getMessage();
            code = 600;
        } else {
            message = "登录失败";
        }

        // 构建输出流对象
        ServletOutputStream outputStream = response.getOutputStream();
        // 调用fastJson，第二个参数用于去除重复引用
        String jsonString = JSON.toJSONString(Result.error().message(message).code(code), SerializerFeature.DisableCircularReferenceDetect);
        outputStream.write(jsonString.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
