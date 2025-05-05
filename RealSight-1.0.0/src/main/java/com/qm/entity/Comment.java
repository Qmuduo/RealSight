package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论实体类
 */
@Data
@TableName("comments")
public class Comment {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String content;

    private Long userId;

    private Long newsId;

    private Long parentId;

    private Long rootId;

    private Integer level;

    private Integer likeCount;

    private Integer dislikeCount;

    private Integer replyCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * 状态：1:正常, 0:已删除, -1:已折叠(敏感词)
     */
    private Integer status;
}