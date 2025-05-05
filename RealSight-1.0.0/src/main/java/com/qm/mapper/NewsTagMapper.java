package com.qm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qm.entity.NewsTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 新闻标签关联Mapper接口
 */
@Mapper
public interface NewsTagMapper extends BaseMapper<NewsTag> {
}