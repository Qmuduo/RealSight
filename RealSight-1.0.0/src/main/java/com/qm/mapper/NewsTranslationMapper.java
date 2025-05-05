package com.qm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qm.entity.NewsTranslation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 新闻翻译Mapper接口
 */
@Mapper
public interface NewsTranslationMapper extends BaseMapper<NewsTranslation> {
}