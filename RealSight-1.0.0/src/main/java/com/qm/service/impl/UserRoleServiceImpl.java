package com.qm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qm.entity.Role;
import com.qm.entity.UserRole;
import com.qm.mapper.RoleMapper;
import com.qm.mapper.UserRoleMapper;
import com.qm.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色关联服务实现类
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    
    @Autowired
    private RoleMapper roleMapper;
    
    @Override
    public List<Role> getRolesByUserId(Long userId) {
        // 查询用户角色关联表
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        List<UserRole> userRoles = list(wrapper);
        
        // 获取角色ID列表
        List<Integer> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        
        // 如果没有角色，返回空列表
        if (roleIds.isEmpty()) {
            return List.of();
        }
        
        // 查询角色信息
        LambdaQueryWrapper<Role> roleWrapper = new LambdaQueryWrapper<>();
        roleWrapper.in(Role::getId, roleIds);
        return roleMapper.selectList(roleWrapper);
    }
    
    @Override
    public boolean assignRole(Long userId, Integer roleId) {
        // 检查是否已经分配
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId)
               .eq(UserRole::getRoleId, roleId);
        if (count(wrapper) > 0) {
            return true; // 已经分配过，视为成功
        }
        
        // 创建新的用户角色关联
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return save(userRole);
    }
    
    @Override
    public boolean removeRole(Long userId, Integer roleId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId)
               .eq(UserRole::getRoleId, roleId);
        return remove(wrapper);
    }
    
    @Override
    public boolean hasRole(Long userId, String roleName) {
        // 获取用户的所有角色
        List<Role> roles = getRolesByUserId(userId);
        
        // 检查是否包含指定角色名
        return roles.stream()
                .anyMatch(role -> role.getName().equals(roleName));
    }
}