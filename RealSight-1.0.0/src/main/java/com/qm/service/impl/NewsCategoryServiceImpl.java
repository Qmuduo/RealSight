package com.qm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qm.entity.NewsCategory;
import com.qm.mapper.NewsCategoryMapper;
import com.qm.service.NewsCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 新闻分类服务实现类
 */
@Service
public class NewsCategoryServiceImpl extends ServiceImpl<NewsCategoryMapper, NewsCategory> implements NewsCategoryService {
    
    @Override
    public List<NewsCategory> getAllCategories() {
        return list();
    }
    
    @Override
    public NewsCategory getCategoryByName(String name) {
        LambdaQueryWrapper<NewsCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NewsCategory::getName, name);
        return getOne(wrapper);
    }
}