package com.qm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 新闻标签关联实体类
 */
@Data
@TableName("news_tags")
public class NewsTag {
    
    private Long newsId;
    
    private Long tagId;
}