package com.tshaojl.blog.service;

import com.tshaojl.blog.dao.CommentMapper;
import com.tshaojl.blog.domain.Comment;
import com.tshaojl.blog.repository.CommentRepository;
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
