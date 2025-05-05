package com.qm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qm.entity.News;

import java.util.List;

/**
 * 新闻服务接口
 */
public interface NewsService extends IService<News> {
    
    /**
     * 根据分类ID查询新闻列表
     * @param categoryId 分类ID
     * @return 新闻列表
     */
    List<News> getNewsByCategory(Long categoryId);
    
    /**
     * 根据热点事件ID查询新闻列表
     * @param hotEventId 热点事件ID
     * @return 新闻列表
     */
    List<News> getNewsByHotEvent(Long hotEventId);
    
    /**
     * 分页查询新闻列表
     * @param page 分页参数
     * @return 新闻分页列表
     */
    IPage<News> getNewsPage(Page<News> page);
    
    /**
     * 增加新闻浏览量
     * @param newsId 新闻ID
     * @return 是否增加成功
     */
    boolean incrementViewCount(Long newsId);
    
    /**
     * 增加新闻点赞量
     * @param newsId 新闻ID
     * @return 是否增加成功
     */
    boolean incrementLikeCount(Long newsId);
    
    /**
     * 增加新闻评论量
     * @param newsId 新闻ID
     * @return 是否增加成功
     */
    boolean incrementCommentCount(Long newsId);
    
    /**
     * 增加新闻分享量
     * @param newsId 新闻ID
     * @return 是否增加成功
     */
    boolean incrementShareCount(Long newsId);
    
    /**
     * 发布新闻
     * @param newsId 新闻ID
     * @return 是否发布成功
     */
    boolean publishNews(Long newsId);
    
    /**
     * 删除新闻（逻辑删除）
     * @param newsId 新闻ID
     * @return 是否删除成功
     */
    boolean deleteNews(Long newsId);
}