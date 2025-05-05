package com.qm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qm.entity.NewsCategory;

import java.util.List;

/**
 * 新闻分类服务接口
 */
public interface NewsCategoryService extends IService<NewsCategory> {
    
    /**
     * 获取所有新闻分类
     * @return 新闻分类列表
     */
    List<NewsCategory> getAllCategories();
    
    /**
     * 根据名称查询新闻分类
     * @param name 分类名称
     * @return 新闻分类
     */
    NewsCategory getCategoryByName(String name);
}