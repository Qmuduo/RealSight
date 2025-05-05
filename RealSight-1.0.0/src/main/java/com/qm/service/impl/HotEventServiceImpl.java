package com.qm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qm.entity.HotEvent;
import com.qm.mapper.HotEventMapper;
import com.qm.service.HotEventService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 热点事件服务实现类
 */
@Service
public class HotEventServiceImpl extends ServiceImpl<HotEventMapper, HotEvent> implements HotEventService {
    
    @Override
    public List<HotEvent> getAllHotEvents() {
        return list();
    }
    
    @Override
    public List<HotEvent> getActiveHotEvents() {
        LambdaQueryWrapper<HotEvent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HotEvent::getIsActive, true)
               .orderByDesc(HotEvent::getCreatedAt);
        return list(wrapper);
    }
}