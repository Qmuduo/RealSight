package com.qm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qm.entity.Role;
import com.qm.entity.UserRole;

import java.util.List;

/**
 * 用户角色关联服务接口
 */
public interface UserRoleService extends IService<UserRole> {
    
    /**
     * 根据用户ID查询用户角色列表
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> getRolesByUserId(Long userId);
    
    /**
     * 为用户分配角色
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 是否成功
     */
    boolean assignRole(Long userId, Integer roleId);
    
    /**
     * 移除用户角色
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 是否成功
     */
    boolean removeRole(Long userId, Integer roleId);
    
    /**
     * 判断用户是否拥有指定角色
     * @param userId 用户ID
     * @param roleName 角色名称
     * @return 是否拥有
     */
    boolean hasRole(Long userId, String roleName);
}