package com.qm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qm.entity.News;
import com.qm.mapper.NewsMapper;
import com.qm.service.NewsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 新闻服务实现类
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {
    
    @Override
    public List<News> getNewsByCategory(Long categoryId) {
        LambdaQueryWrapper<News> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(News::getCategoryId, categoryId)
               .eq(News::getStatus, 1)
               .orderByDesc(News::getPublishedAt);
        return list(wrapper);
    }
    
    @Override
    public List<News> getNewsByHotEvent(Long hotEventId) {
        LambdaQueryWrapper<News> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(News::getHotEventId, hotEventId)
               .eq(News::getStatus, 1)
               .orderByDesc(News::getPublishedAt);
        return list(wrapper);
    }
    
    @Override
    public IPage<News> getNewsPage(Page<News> page) {
        LambdaQueryWrapper<News> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(News::getStatus, 1)
               .orderByDesc(News::getPublishedAt);
        return page(page, wrapper);
    }
    
    @Override
    public boolean incrementViewCount(Long newsId) {
        return update(new LambdaUpdateWrapper<News>()
                .eq(News::getId, newsId)
                .setSql("view_count = view_count + 1"));
    }
    
    @Override
    public boolean incrementLikeCount(Long newsId) {
        return update(new LambdaUpdateWrapper<News>()
                .eq(News::getId, newsId)
                .setSql("like_count = like_count + 1"));
    }
    
    @Override
    public boolean incrementCommentCount(Long newsId) {
        return update(new LambdaUpdateWrapper<News>()
                .eq(News::getId, newsId)
                .setSql("comment_count = comment_count + 1"));
    }
    
    @Override
    public boolean incrementShareCount(Long newsId) {
        return update(new LambdaUpdateWrapper<News>()
                .eq(News::getId, newsId)
                .setSql("share_count = share_count + 1"));
    }
    
    @Override
    public boolean publishNews(Long newsId) {
        News news = getById(newsId);
        if (news != null) {
            news.setStatus(1);
            return updateById(news);
        }
        return false;
    }
    
    @Override
    public boolean deleteNews(Long newsId) {
        News news = getById(newsId);
        if (news != null) {
            news.setStatus(-1);
            return updateById(news);
        }
        return false;
    }
}