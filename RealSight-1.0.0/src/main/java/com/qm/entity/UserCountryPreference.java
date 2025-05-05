package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户国家偏好实体类
 */
@Data
@TableName("user_country_preferences")
public class UserCountryPreference {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private String countryCode;
    
    private Integer priority;
    
    private LocalDateTime createdAt;
}