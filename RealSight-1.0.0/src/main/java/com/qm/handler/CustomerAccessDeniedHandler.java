package com.qm.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qm.common.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @program: springsecurity-hello
 * @description: 已认证（登录）用户，访问资源权限不足处理器，给一个返回提示，需要在security的配置类里配置进去
 * @author: ZhangQingMin
 * @create: 2025-05-09 10:38
 **/
@Component
public class CustomerAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 设置响应类型
        response.setContentType("application/json;charset=utf-8");
        // 构建输出流对象
        ServletOutputStream outputStream = response.getOutputStream();
        // 调用fastJson，第二个参数用于去除重复引用
        String jsonString = JSON.toJSONString(Result.error().message("用户权限不足").code(700), SerializerFeature.DisableCircularReferenceDetect);
        outputStream.write(jsonString.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
