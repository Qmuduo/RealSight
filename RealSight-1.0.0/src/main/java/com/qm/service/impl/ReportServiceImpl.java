package com.qm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qm.entity.Report;
import com.qm.mapper.ReportMapper;
import com.qm.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 举报服务实现类
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {
    
    // 定义状态常量
    private static final int STATUS_HANDLED = 1;
    private static final int STATUS_REJECTED = 2;
    
    @Override
    public List<Report> getReportsByReporterId(Long reporterId) {
        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Report::getReporterId, reporterId)
               .orderByDesc(Report::getCreatedAt);
        return list(wrapper);
    }
    
    @Override
    public List<Report> getReportsByNewsId(Long newsId) {
        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Report::getNewsId, newsId)
               .orderByDesc(Report::getCreatedAt);
        return list(wrapper);
    }
    
    @Override
    public List<Report> getReportsByCommentId(Long commentId) {
        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Report::getCommentId, commentId)
               .orderByDesc(Report::getCreatedAt);
        return list(wrapper);
    }
    
    @Override
    public IPage<Report> getReportPage(Page<Report> page) {
        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Report::getCreatedAt);
        return page(page, wrapper);
    }
    
    @Override
    @Transactional
    public boolean handleReport(Long reportId) {
        Report report = getById(reportId);
        if (report != null) {
            report.setStatus(STATUS_HANDLED); // 已处理
            report.setHandledAt(LocalDateTime.now());
            return updateById(report);
        }
        return false;
    }
    
    @Override
    @Transactional
    public boolean rejectReport(Long reportId) {
        Report report = getById(reportId);
        if (report != null) {
            report.setStatus(STATUS_REJECTED); // 已拒绝
            report.setHandledAt(LocalDateTime.now());
            return updateById(report);
        }
        return false;
    }
}