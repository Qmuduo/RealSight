package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@TableName("users")
public class User {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String username;
    
    private String password;
    
    private String email;
    
    private String phone;

    private String nickname;
    
    private String avatar;
    
    private String bio;
    
    private LocalDateTime lastLoginTime;
    
    private String lastLoginIp;
    
    private Boolean isActive;
    
    private Boolean isVerified;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

}