package com.qm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qm.entity.NewsTranslation;

import java.util.List;

/**
 * 新闻翻译服务接口
 */
public interface NewsTranslationService extends IService<NewsTranslation> {
    
    /**
     * 根据新闻ID和语言获取翻译
     * @param newsId 新闻ID
     * @param language 语言
     * @return 新闻翻译
     */
    NewsTranslation getTranslation(Long newsId, String language);
    
    /**
     * 根据新闻ID获取所有翻译
     * @param newsId 新闻ID
     * @return 新闻翻译列表
     */
    List<NewsTranslation> getTranslationsByNewsId(Long newsId);
    
    /**
     * 创建或更新翻译
     * @param translation 翻译对象
     * @return 是否成功
     */
    boolean saveOrUpdateTranslation(NewsTranslation translation);
}