package com.qm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qm.entity.CommentMention;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论@用户Mapper接口
 */
@Mapper
public interface CommentMentionMapper extends BaseMapper<CommentMention> {
}