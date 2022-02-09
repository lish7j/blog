package com.tshaojl.blog.repository;

import com.tshaojl.blog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Comment 仓库.
 */
public interface CommentRepository extends JpaRepository<Comment, Long>{
 
}
