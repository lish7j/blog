package com.lssj.blog.dao;

import com.lssj.blog.domain.Authority;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthorityMapper {
    Authority findById(Long id);
}
