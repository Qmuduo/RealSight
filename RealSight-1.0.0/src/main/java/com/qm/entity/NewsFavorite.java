package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 新闻收藏实体类
 */
@Data
@TableName("news_favorites")
public class NewsFavorite {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Long newsId;
    
    private Long userId;
    
    private LocalDateTime createdAt;
}