package com.qm.controller;

import com.qm.common.Result;
import com.qm.entity.User;
import com.qm.exception.CustomerAuthenticationException;
import com.qm.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: RealSight-1.0.0
 * @description: 登录，注册控制器
 * @author: ZhangQingMin
 * @create: 2025-05-10 17:10
 **/
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        String jwt = userService.login(user);
        if (StringUtils.hasLength(jwt)) {
            return Result.ok().message("登录成功").data("token", jwt);
        }
        return Result.error().message("<登录失败>");
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        try {
            // 参数校验
            if (user.getUsername() == null || user.getUsername().isEmpty()) {
                return Result.error().message("用户名不能为空");
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                return Result.error().message("密码不能为空");
            }
            
            // 调用注册服务
            boolean success = userService.register(user);
            
            if (success) {
                return Result.ok().message("注册成功");
            } else {
                return Result.error().message("注册失败");
            }
        } catch (Exception e) {
            return Result.error().message(e.getMessage());
        }
    }
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        // 在logout中要获取jwt
        String token = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        if (ObjectUtils.isEmpty(token)) {
            throw new CustomerAuthenticationException("token为空");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            // 1.清除当前用户在上下文中的认证器
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            // 2.清除redis
            // todo
            stringRedisTemplate.delete("token_" + token);
        }
        return Result.ok().message("退出系统成功！");
    }

}
