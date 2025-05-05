package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 通知实体类
 */
@Data
@TableName("notifications")
public class Notification {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    /**
     * 类型：comment, like, mention, breaking_news, system
     */
    private String type;
    
    private String title;
    
    private String content;
    
    /**
     * 相关内容ID
     */
    private Long relatedId;
    
    /**
     * 相关内容类型：news, comment, post
     */
    private String relatedType;
    
    private Boolean isRead;
    
    private LocalDateTime createdAt;
}