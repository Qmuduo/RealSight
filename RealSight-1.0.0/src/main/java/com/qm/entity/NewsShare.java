package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 新闻分享实体类
 */
@Data
@TableName("news_shares")
public class NewsShare {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Long newsId;
    
    private Long userId;
    
    /**
     * 分享平台
     */
    private String platform;
    
    private LocalDateTime createdAt;
}