package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 新闻翻译实体类
 */
@Data
@TableName("news_translations")
public class NewsTranslation {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Long newsId;
    
    private String language;
    
    private String title;
    
    private String content;
    
    private String summary;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}