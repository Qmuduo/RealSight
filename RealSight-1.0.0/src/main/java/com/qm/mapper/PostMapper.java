package com.qm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qm.entity.Post;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户发帖Mapper接口
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {
}