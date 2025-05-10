package com.qm.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qm.common.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @program: springsecurity-hello
 * @description: 用户登录时的异常捕捉（不是系统用户，密码错误等等），需要注册到security的配置类里
 * @author: ZhangQingMin
 * @create: 2025-05-09 13:28
 **/
@Component
public class AnonymousAuthenticationHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 设置响应类型
        response.setContentType("application/json;charset=utf-8");
        // 构建输出流对象
        ServletOutputStream outputStream = response.getOutputStream();

        String jsonString = "";

        if (authException instanceof BadCredentialsException) { // 用户名密码错误
            // 调用fastJson，第二个参数用于去除重复引用
            jsonString = JSON.toJSONString(Result.error().message(authException.getMessage()).code(HttpServletResponse.SC_UNAUTHORIZED), SerializerFeature.DisableCircularReferenceDetect);

        } else if (authException instanceof InternalAuthenticationServiceException) {
            jsonString = JSON.toJSONString(Result.error().message("用户名为空").code(HttpServletResponse.SC_UNAUTHORIZED), SerializerFeature.DisableCircularReferenceDetect);

        } else {
            jsonString = JSON.toJSONString(Result.error().message("匿名用户无权限访问！").code(600), SerializerFeature.DisableCircularReferenceDetect);
        }



        outputStream.write(jsonString.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
