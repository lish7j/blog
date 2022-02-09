package com.tshaojl.blog.dao;

import com.tshaojl.blog.domain.Tag;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Component
@Slf4j
class TagMapperTest {

    @Autowired
    private TagMapper tagMapper;

    @Test
    void findByName() {
        Tag tag = tagMapper.findByName("java");
        log.info(String.format("%d %s %d", tag.getId(), tag.getName(), tag.getCount()));
    }
}