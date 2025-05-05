package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 帖子实体类
 */
@Data
@TableName("posts")
public class Post {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String title;
    
    private String content;
    
    private Long userId;
    
    private Long relatedNewsId;
    
    private LocalDateTime scheduledTime;
    
    private Integer viewCount;
    
    private Integer likeCount;
    
    private Integer commentCount;
    
    private Integer shareCount;
    
    private Boolean isDraft;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    /**
     * 状态：1:已发布, 0:草稿, -1:已删除
     */
    private Integer status;
}