package com.lssj.blog.dao;

import com.lssj.blog.domain.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {
    Tag findByName(String name);

    void insert(Tag tag);

    void update(Tag tag);

    List<Tag> findAll();
}
