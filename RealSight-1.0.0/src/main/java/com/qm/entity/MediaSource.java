package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 媒体源实体类
 */
@Data
@TableName("media_sources")
public class MediaSource {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String name;
    
    private Long countryId;
    
    private String logoUrl;
    
    private String websiteUrl;
    
    /**
     * 可信度评分：1-5星
     */
    private Integer credibilityScore;
    
    private String languages;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    /**
     * 状态：1:活跃, 0:禁用
     */
    private Integer status;
}