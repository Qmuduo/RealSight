package com.qm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qm.entity.NewsShare;
import com.qm.mapper.NewsShareMapper;
import com.qm.service.NewsService;
import com.qm.service.NewsShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 新闻分享服务实现类
 */
@Service
public class NewsShareServiceImpl extends ServiceImpl<NewsShareMapper, NewsShare> implements NewsShareService {
    
    @Autowired
    private NewsService newsService;
    
    @Override
    public List<NewsShare> getSharesByNewsId(Long newsId) {
        LambdaQueryWrapper<NewsShare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NewsShare::getNewsId, newsId)
               .orderByDesc(NewsShare::getCreatedAt);
        return list(wrapper);
    }
    
    @Override
    public List<NewsShare> getSharesByUserId(Long userId) {
        LambdaQueryWrapper<NewsShare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NewsShare::getUserId, userId)
               .orderByDesc(NewsShare::getCreatedAt);
        return list(wrapper);
    }
    
    @Override
    @Transactional
    public boolean createShare(Long newsId, Long userId, String platform) {
        NewsShare share = new NewsShare();
        share.setNewsId(newsId);
        share.setUserId(userId);
        share.setPlatform(platform);
        share.setCreatedAt(LocalDateTime.now());
        
        boolean result = save(share);
        if (result) {
            // 增加新闻分享数
            newsService.incrementShareCount(newsId);
        }
        return result;
    }
}