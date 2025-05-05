package com.qm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qm.entity.NewsShare;
import org.apache.ibatis.annotations.Mapper;

/**
 * 新闻分享Mapper接口
 */
@Mapper
public interface NewsShareMapper extends BaseMapper<NewsShare> {
}