package com.lssj.blog.dao;


import com.lssj.blog.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    void deleteById(Long id);
    Comment findById(Long id);
}
