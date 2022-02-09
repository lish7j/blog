package com.tshaojl.blog.service;

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

	private final CommentRepository commentRepository;

	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	@Override
	@Transactional
	public void removeComment(Long id) {
		commentRepository.deleteById(id);
	}
	@Override
	public Comment getCommentById(Long id) {
		return commentRepository.findById(id).orElse(null);
	}

}
