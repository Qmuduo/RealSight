package com.qm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qm.entity.User;
import com.qm.mapper.UserMapper;
import com.qm.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
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
}