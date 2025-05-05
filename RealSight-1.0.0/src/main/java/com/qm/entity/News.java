package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 新闻实体类
 */
@Data
@TableName("news")
public class News {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String title;
    
    private String content;
    
    private String summary;
    
    private Long mediaId;
    
    private Long categoryId;
    
    private Long hotEventId;
    
    private String author;
    
    private LocalDateTime publishedAt;
    
    private String sourceUrl;
    
    private String imageUrl;
    
    private Integer viewCount;
    
    private Integer likeCount;
    
    private Integer commentCount;
    
    private Integer shareCount;
    
    private Boolean isBreaking;
    
    private String language;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    /**
     * 状态：1:已发布, 0:草稿, -1:已删除
     */
    private Integer status;
}