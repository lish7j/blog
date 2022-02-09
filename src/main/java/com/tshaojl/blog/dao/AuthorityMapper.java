package com.tshaojl.blog.dao;

import com.tshaojl.blog.domain.Authority;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthorityMapper {
    Authority findById(Long id);
}
