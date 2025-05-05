package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 帖子媒体实体类
 */
@Data
@TableName("post_media")
public class PostMedia {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Long postId;
    
    /**
     * 类型：image, video, audio
     */
    private String type;
    
    private String url;
    
    private String caption;
    
    private Integer orderIndex;
    
    private LocalDateTime createdAt;
}