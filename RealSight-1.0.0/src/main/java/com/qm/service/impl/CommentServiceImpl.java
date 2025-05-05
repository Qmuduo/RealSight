package com.qm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qm.entity.Comment;
import com.qm.mapper.CommentMapper;
import com.qm.service.CommentService;
import com.qm.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 评论服务实现类
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    
    @Autowired
    private NewsService newsService;
    
    @Override
    public List<Comment> getCommentsByNewsId(Long newsId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getNewsId, newsId)
               .eq(Comment::getStatus, 1)
               .eq(Comment::getLevel, 1)  // 只查询一级评论
               .orderByDesc(Comment::getCreatedAt);
        return list(wrapper);
    }
    
    @Override
    public List<Comment> getCommentsByUserId(Long userId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getUserId, userId)
               .eq(Comment::getStatus, 1)
               .orderByDesc(Comment::getCreatedAt);
        return list(wrapper);
    }
    
    @Override
    public List<Comment> getRepliesByParentId(Long parentId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getParentId, parentId)
               .eq(Comment::getStatus, 1)
               .orderByAsc(Comment::getCreatedAt);
        return list(wrapper);
    }
    
    @Override
    public List<Comment> getRepliesByRootId(Long rootId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getRootId, rootId)
               .eq(Comment::getStatus, 1)
               .orderByAsc(Comment::getCreatedAt);
        return list(wrapper);
    }
    
    @Override
    public IPage<Comment> getCommentPage(Page<Comment> page, Long newsId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getNewsId, newsId)
               .eq(Comment::getStatus, 1)
               .eq(Comment::getLevel, 1)  // 只查询一级评论
               .orderByDesc(Comment::getCreatedAt);
        return page(page, wrapper);
    }
    
    @Override
    public boolean incrementLikeCount(Long commentId) {
        return update(new LambdaUpdateWrapper<Comment>()
                .eq(Comment::getId, commentId)
                .setSql("like_count = like_count + 1"));
    }
    
    @Override
    public boolean incrementDislikeCount(Long commentId) {
        return update(new LambdaUpdateWrapper<Comment>()
                .eq(Comment::getId, commentId)
                .setSql("dislike_count = dislike_count + 1"));
    }
    
    @Override
    public boolean incrementReplyCount(Long commentId) {
        return update(new LambdaUpdateWrapper<Comment>()
                .eq(Comment::getId, commentId)
                .setSql("reply_count = reply_count + 1"));
    }
    
    @Override
    public boolean deleteComment(Long commentId) {
        Comment comment = getById(commentId);
        if (comment != null) {
            comment.setStatus(-1);
            return updateById(comment);
        }
        return false;
    }
    
    @Override
    public boolean foldComment(Long commentId) {
        Comment comment = getById(commentId);
        if (comment != null) {
            comment.setStatus(2); // 2表示折叠状态
            return updateById(comment);
        }
        return false;
    }
}