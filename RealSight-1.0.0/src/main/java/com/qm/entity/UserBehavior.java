package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户行为实体类
 */
@Data
@TableName("user_behaviors")
public class UserBehavior {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private Long newsId;
    
    private Long postId;
    
    /**
     * 行为类型：view, like, share, skip, comment
     */
    private String behaviorType;
    
    private Integer weight;
    
    private LocalDateTime createdAt;
}