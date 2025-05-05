package com.qm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户角色关联实体类
 */
@Data
@TableName("user_roles")
public class UserRole {
    
    private Long userId;
    
    private Integer roleId;
}