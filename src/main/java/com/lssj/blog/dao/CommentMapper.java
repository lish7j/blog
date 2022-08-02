package com.lssj.blog.dao;


import com.lssj.blog.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    void deleteById(Long id);
    Comment findById(Long id);

    List<Comment> findByBlogId(Long blogId);

    void insert(Comment comment);
}
