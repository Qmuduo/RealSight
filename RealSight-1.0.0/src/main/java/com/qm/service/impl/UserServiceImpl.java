package com.qm.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qm.entity.User;
import com.qm.entity.vo.LoginUser;
import com.qm.mapper.UserMapper;
import com.qm.service.UserRoleService;
import com.qm.service.UserService;
import com.qm.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRoleService userRoleService;
    
    @Override
    public User getUserByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return getOne(wrapper);
    }
    
    @Override
    public User getUserByEmail(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        return getOne(wrapper);
    }
    
    @Override
    public User getUserByPhone(String phone) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        return getOne(wrapper);
    }
    
    @Override
    public IPage<User> getUserPage(Page<User> page) {
        return page(page);
    }
    
    @Override
    public boolean updateLastLogin(Long userId, String ip) {
        User user = getById(userId);
        if (user != null) {
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(ip);
            return updateById(user);
        }
        return false;
    }
    
    @Override
    public boolean activateUser(Long userId) {
        User user = getById(userId);
        if (user != null) {
            user.setIsActive(true);
            return updateById(user);
        }
        return false;
    }
    
    @Override
    public boolean verifyUser(Long userId) {
        User user = getById(userId);
        if (user != null) {
            user.setIsVerified(true);
            return updateById(user);
        }
        return false;
    }

    @Override
    public String login(User user) {

        // 1.封装Authentication对象
        /**
         *  作用：创建一个 UsernamePasswordAuthenticationToken 对象，封装用户提交的用户名和密码。
         *  底层原理：该对象是 Spring Security 中 Authentication 接口的一个实现类，用于表示未认证的凭据。
         */
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()); // 封装用户名密码的凭据对象
        // 2.用authenticationManager-》authenticate进行校验，自动用Bcrxxx进行校验密码

        /**
         *  Spring Security 会根据配置的 UserDetailsService 中的loadUserByUsername()方法加载用户信息包括权限。
         *  使用密码编码器（如 BCryptPasswordEncoder）验证密码是否正确。
         *  认证成功返回一个已认证的 Authentication 对象，失败则抛出异常（如 BadCredentialsException）。
         */
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 如果authentic是空的
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("<登录失败-authentic>");
        }

        // UserDetail的实现类
        //  获取认证后的用户详细信息（UserDetails 的实现类 LoginUser）。
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        // 用fastJson把对象转成Json
        String loginUserString = JSON.toJSONString(loginUser);

        String jwt = JwtUtil.createJWT(loginUserString, null);

        String tokenKey = "token_" + jwt;
        System.out.println("UserServiceImpl login token: " + tokenKey);
        // 存储redis白名单
        stringRedisTemplate.opsForValue().set(tokenKey, jwt, JwtUtil.EXPIRATION_TIME / 1000, TimeUnit.SECONDS);

        return jwt;
    }

    @Override
    @Transactional
    public boolean register(User user) {
        // 1. 检查用户名是否已存在
        User existingUser = getUserByUsername(user.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 2. 检查邮箱是否已存在
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            existingUser = getUserByEmail(user.getEmail());
            if (existingUser != null) {
                throw new RuntimeException("邮箱已被注册");
            }
        }
        
        // 3. 检查手机号是否已存在
        if (user.getPhone() != null && !user.getPhone().isEmpty()) {
            existingUser = getUserByPhone(user.getPhone());
            if (existingUser != null) {
                throw new RuntimeException("手机号已被注册");
            }
        }
        
        // 4. 设置用户默认信息
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setIsActive(true);  // 默认激活
        user.setIsVerified(false);  // 默认未验证
        
        // 5. 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // 6. 保存用户
        boolean saved = save(user);
        
        // 7. 给新注册的用户分配ROLE_USER角色
        if (saved) {
            // 假设ROLE_USER角色的ID为1，如果不是，需要根据实际情况调整
            userRoleService.assignRole(user.getId(), 1);
        }
        
        return saved;
    }
}