package com.qm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qm.entity.Report;

import java.util.List;

/**
 * 举报服务接口
 */
public interface ReportService extends IService<Report> {
    
    /**
     * 根据举报者ID查询举报
     * @param reporterId 举报者ID
     * @return 举报列表
     */
    List<Report> getReportsByReporterId(Long reporterId);
    
    /**
     * 根据新闻ID查询举报
     * @param newsId 新闻ID
     * @return 举报列表
     */
    List<Report> getReportsByNewsId(Long newsId);
    
    /**
     * 根据评论ID查询举报
     * @param commentId 评论ID
     * @return 举报列表
     */
    List<Report> getReportsByCommentId(Long commentId);
    
    /**
     * 分页查询举报
     * @param page 分页参数
     * @return 举报分页列表
     */
    IPage<Report> getReportPage(Page<Report> page);
    
    /**
     * 处理举报
     * @param reportId 举报ID
     * @return 是否成功
     */
    boolean handleReport(Long reportId);
    
    /**
     * 拒绝举报
     * @param reportId 举报ID
     * @return 是否成功
     */
    boolean rejectReport(Long reportId);
}