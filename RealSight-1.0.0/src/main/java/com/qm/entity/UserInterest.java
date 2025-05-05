package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户兴趣实体类
 */
@Data
@TableName("user_interests")
public class UserInterest {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private Long tagId;
    
    private Integer weight;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}