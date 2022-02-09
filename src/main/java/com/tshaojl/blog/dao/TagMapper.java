package com.tshaojl.blog.dao;

import com.tshaojl.blog.domain.Tag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {
    Tag findByName(String name);
}
