package com.lssj.blog.dao;

import com.lssj.blog.domain.Blog;
import com.lssj.blog.domain.Catalog;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.recycler.Recycler;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Component
@Slf4j
class BlogMapperTest {

    @Autowired
    private BlogMapper blogMapper;

    @Test
    void insertBlog() {
        Blog blog = new Blog();
        blog.setUserId(6L);
        blog.setCommentSize(0);
        blog.setContent("2");
        Timestamp time = new Timestamp(System.currentTimeMillis());
        blog.setCreateTime(time);
        blog.setUpdateTime(time);
        blog.setHtmlContent("2");
        blog.setReadSize(1);
        blog.setSummary("2");
        blog.setTitle("22");
        blog.setVoteSize(2);
        blog.setUserId(2L);
        Catalog catalog = new Catalog();
        catalog.setId(2L);
        blog.setCatalog(catalog);
        blogMapper.insertBlog(blog);
    }
}