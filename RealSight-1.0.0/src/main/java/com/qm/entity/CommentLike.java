package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论点赞实体类
 */
@Data
@TableName("comment_likes")
public class CommentLike {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long commentId;

    private Long userId;

    private Boolean isLike;

    private LocalDateTime createdAt;
}