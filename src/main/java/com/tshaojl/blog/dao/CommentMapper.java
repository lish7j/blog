package com.tshaojl.blog.dao;


import com.tshaojl.blog.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    void deleteById(Long id);
    Comment findById(Long id);
}
