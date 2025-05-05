package com.qm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qm.entity.NewsCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 新闻分类Mapper接口
 */
@Mapper
public interface NewsCategoryMapper extends BaseMapper<NewsCategory> {
}