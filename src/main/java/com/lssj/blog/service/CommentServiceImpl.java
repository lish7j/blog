package com.lssj.blog.service;

import com.lssj.blog.dao.CommentMapper;
import com.lssj.blog.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Comment 服务.
 */
@Service
public class CommentServiceImpl implements CommentService {

	private final CommentMapper commentMapper;

	@Autowired
	public CommentServiceImpl(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}

	@Override
	@Transactional
	public void removeComment(Long id) {
		commentMapper.deleteById(id);
	}
	@Override
	public Comment getCommentById(Long id) {
		return commentMapper.findById(id);
	}

}
