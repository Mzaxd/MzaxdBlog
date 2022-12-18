package com.mzaxd.service;

import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author root
* @description 针对表【sg_comment(评论表)】的数据库操作Service
* @createDate 2022-11-27 04:57:46
*/
public interface CommentService extends IService<Comment> {

    /**
     * 返回评论List
     *
     * @param commentType 评论类型
     * @param articleId 文章ID
     * @param pageNum 页数
     * @param pageSize 每页大小
     * @return ResponseResult
     * @author mzaxd
     * @date 11/27/22 5:07 AM
     */
    ResponseResult<?> commentList(Integer commentType, Long articleId, Integer pageNum, Integer pageSize);

    /**
     * 添加评论
     *
     * @author mzaxd
     * @date 11/27/22 7:13 AM
     * @param comment
     * @return ResponseResult
     */
    ResponseResult<?> addComment(Comment comment);
}
