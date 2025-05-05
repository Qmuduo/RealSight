package com.qm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 帖子标签关联实体类
 */
@Data
@TableName("post_tags")
public class PostTag {
    
    private Long postId;
    
    private Long tagId;
}