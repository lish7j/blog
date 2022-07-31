package com.lssj.blog.dao;

import com.lssj.blog.domain.Vote;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VoteMapper {
    void deleteById(Long id);
    Vote findById(Long id);

    List<Vote> findByBlogId(Long blogId);
}
