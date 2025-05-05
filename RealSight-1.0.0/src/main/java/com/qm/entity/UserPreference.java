package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户偏好设置实体类
 */
@Data
@TableName("user_preferences")
public class UserPreference {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private String language;
    
    private Boolean darkMode;
    
    private Boolean pushNotification;
    
    /**
     * 通知频率：all, important_only, none
     */
    private String notificationFrequency;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}