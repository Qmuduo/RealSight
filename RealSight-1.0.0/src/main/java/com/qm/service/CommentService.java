package com.qm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qm.entity.Comment;

import java.util.List;

/**
 * 评论服务接口
 */
public interface CommentService extends IService<Comment> {
    
    /**
     * 根据新闻ID查询评论
     * @param newsId 新闻ID
     * @return 评论列表
     */
    List<Comment> getCommentsByNewsId(Long newsId);
    
    /**
     * 根据用户ID查询评论
     * @param userId 用户ID
     * @return 评论列表
     */
    List<Comment> getCommentsByUserId(Long userId);
    
    /**
     * 根据父评论ID查询回复
     * @param parentId 父评论ID
     * @return 回复列表
     */
    List<Comment> getRepliesByParentId(Long parentId);
    
    /**
     * 根据根评论ID查询所有回复
     * @param rootId 根评论ID
     * @return 回复列表
     */
    List<Comment> getRepliesByRootId(Long rootId);
    
    /**
     * 分页查询评论
     * @param page 分页参数
     * @param newsId 新闻ID
     * @return 评论分页列表
     */
    IPage<Comment> getCommentPage(Page<Comment> page, Long newsId);
    
    /**
     * 增加评论点赞数
     * @param commentId 评论ID
     * @return 是否成功
     */
    boolean incrementLikeCount(Long commentId);
    
    /**
     * 增加评论踩数
     * @param commentId 评论ID
     * @return 是否成功
     */
    boolean incrementDislikeCount(Long commentId);
    
    /**
     * 增加评论回复数
     * @param commentId 评论ID
     * @return 是否成功
     */
    boolean incrementReplyCount(Long commentId);
    
    /**
     * 删除评论（逻辑删除）
     * @param commentId 评论ID
     * @return 是否成功
     */
    boolean deleteComment(Long commentId);
    
    /**
     * 折叠评论（敏感词）
     * @param commentId 评论ID
     * @return 是否成功
     */
    boolean foldComment(Long commentId);
}