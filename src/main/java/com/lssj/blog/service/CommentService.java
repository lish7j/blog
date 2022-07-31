package com.lssj.blog.service;

import com.lssj.blog.domain.Comment;

/**
 * Comment 服务接口.
 */
public interface CommentService {
	/**
	 * 根据id获取 Comment
	 */
	Comment getCommentById(Long id);
	/**
	 * 删除评论
	 */
	void removeComment(Long id);
}
