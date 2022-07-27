package com.tshaojl.blog.dao;

import com.tshaojl.blog.domain.Blog;
import com.tshaojl.blog.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface BlogMapper {
    List<Blog> findByUserAndTitleLike(User user, String title, Integer start, Integer limit);
}
