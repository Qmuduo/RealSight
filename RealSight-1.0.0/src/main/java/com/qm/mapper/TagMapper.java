package com.qm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qm.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签Mapper接口
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
}