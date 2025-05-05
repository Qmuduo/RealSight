package com.qm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qm.entity.User;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象
     */
    User getUserByUsername(String username);
    
    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return 用户对象
     */
    User getUserByEmail(String email);
    
    /**
     * 根据手机号查询用户
     * @param phone 手机号
     * @return 用户对象
     */
    User getUserByPhone(String phone);
    
    /**
     * 分页查询用户列表
     * @param page 分页参数
     * @return 用户分页列表
     */
    IPage<User> getUserPage(Page<User> page);
    
    /**
     * 更新用户最后登录信息
     * @param userId 用户ID
     * @param ip 登录IP
     * @return 是否更新成功
     */
    boolean updateLastLogin(Long userId, String ip);
    
    /**
     * 激活用户
     * @param userId 用户ID
     * @return 是否激活成功
     */
    boolean activateUser(Long userId);
    
    /**
     * 验证用户
     * @param userId 用户ID
     * @return 是否验证成功
     */
    boolean verifyUser(Long userId);
}