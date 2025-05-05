package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 举报实体类
 */
@Data
@TableName("reports")
public class Report {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Long reporterId;
    
    private Long newsId;
    
    private Long postId;
    
    private Long commentId;
    
    private String reason;

    /**
     * 跟踪举报处理的时间
     */
    private LocalDateTime handledAt;
    
    /**
     * 状态：0:待处理, 1:已处理, -1:已拒绝
     */
    private Integer status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}