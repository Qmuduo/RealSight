package com.qm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qm.entity.NewsMedia;
import org.apache.ibatis.annotations.Mapper;

/**
 * 新闻媒体内容Mapper接口
 */
@Mapper
public interface NewsMediaMapper extends BaseMapper<NewsMedia> {
}