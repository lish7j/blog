package com.tshaojl.blog.dao;

import com.tshaojl.blog.domain.Catalog;
import com.tshaojl.blog.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Component
@Slf4j
class CatalogMapperTest {

    @Autowired
    private CatalogMapper catalogMapper;

    @Test
    void findByUser() {
        User user = new User();
        user.setId(3L);
        List<Catalog> catalogList = catalogMapper.findByUser(user);
        for (Catalog catalog : catalogList) {

            //log.info(String.format("%d %s %d %s", catalog.getId(), catalog.getName(), catalog.getUser().getId(), catalog.getUser().getUsername()));
        }
    }

    @Test
    void findByUserId() {

        List<Catalog> catalogList = catalogMapper.findByUserId(3L);
        for (Catalog catalog : catalogList) {

           // log.info(String.format("%d %s %d %s", catalog.getId(), catalog.getName(), catalog.getUser().getId(), catalog.getUser().getUsername()));
        }
    }

    @Test
    void findByUserAndName() {
        User user = new User();
        user.setId(3L);
        Catalog catalog = catalogMapper.findByUserAndName(user.getId(), "java");
        log.info(String.format("%d %s %d %s", catalog.getId(), catalog.getName()));

    }
}