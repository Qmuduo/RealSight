package com.qm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qm.entity.NewsFavorite;
import org.apache.ibatis.annotations.Mapper;

/**
 * 新闻收藏Mapper接口
 */
@Mapper
public interface NewsFavoriteMapper extends BaseMapper<NewsFavorite> {
}