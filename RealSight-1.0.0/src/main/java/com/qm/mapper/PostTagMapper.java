package com.qm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qm.entity.PostTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 帖子标签关联Mapper接口
 */
@Mapper
public interface PostTagMapper extends BaseMapper<PostTag> {
}