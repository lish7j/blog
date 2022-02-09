package com.tshaojl.blog.dao;

import com.tshaojl.blog.domain.Vote;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VoteMapper {
    void deleteById(Long id);
    Vote findById(Long id);
}
