package com.qm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qm.entity.NewsTranslation;
import com.qm.mapper.NewsTranslationMapper;
import com.qm.service.NewsTranslationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 新闻翻译服务实现类
 */
@Service
public class NewsTranslationServiceImpl extends ServiceImpl<NewsTranslationMapper, NewsTranslation> implements NewsTranslationService {
    
    @Override
    public NewsTranslation getTranslation(Long newsId, String language) {
        LambdaQueryWrapper<NewsTranslation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NewsTranslation::getNewsId, newsId)
               .eq(NewsTranslation::getLanguage, language);
        return getOne(wrapper);
    }
    
    @Override
    public List<NewsTranslation> getTranslationsByNewsId(Long newsId) {
        LambdaQueryWrapper<NewsTranslation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NewsTranslation::getNewsId, newsId);
        return list(wrapper);
    }
    
    @Override
    public boolean saveOrUpdateTranslation(NewsTranslation translation) {
        if (translation.getId() == null) {
            translation.setCreatedAt(LocalDateTime.now());
        }
        translation.setUpdatedAt(LocalDateTime.now());
        
        NewsTranslation existingTranslation = getTranslation(translation.getNewsId(), translation.getLanguage());
        if (existingTranslation != null) {
            translation.setId(existingTranslation.getId());
            return updateById(translation);
        } else {
            return save(translation);
        }
    }
}