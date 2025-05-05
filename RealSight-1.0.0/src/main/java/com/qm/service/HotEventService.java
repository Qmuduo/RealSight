package com.qm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qm.entity.HotEvent;

import java.util.List;

/**
 * 热点事件服务接口
 */
public interface HotEventService extends IService<HotEvent> {
    
    /**
     * 获取所有热点事件
     * @return 热点事件列表
     */
    List<HotEvent> getAllHotEvents();
    
    /**
     * 获取活跃的热点事件
     * @return 活跃的热点事件列表
     */
    List<HotEvent> getActiveHotEvents();
}