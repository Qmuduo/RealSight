package com.qm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qm.entity.NewsShare;

import java.util.List;

/**
 * 新闻分享服务接口
 */
public interface NewsShareService extends IService<NewsShare> {
    
    /**
     * 根据新闻ID查询分享记录
     * @param newsId 新闻ID
     * @return 分享记录列表
     */
    List<NewsShare> getSharesByNewsId(Long newsId);
    
    /**
     * 根据用户ID查询分享记录
     * @param userId 用户ID
     * @return 分享记录列表
     */
    List<NewsShare> getSharesByUserId(Long userId);
    
    /**
     * 创建分享记录
     * @param newsId 新闻ID
     * @param userId 用户ID
     * @param platform 分享平台
     * @return 是否创建成功
     */
    boolean createShare(Long newsId, Long userId, String platform);
}