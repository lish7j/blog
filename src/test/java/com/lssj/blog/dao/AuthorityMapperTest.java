package com.lssj.blog.dao;

import com.lssj.blog.domain.Authority;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Component
@Slf4j
class AuthorityMapperTest {

    @Autowired
    private AuthorityMapper authorityMapper;

    @Test
    void findById() {
        Authority authority = authorityMapper.findById(1L);
        log.info(String.format("id: %d, role: %s", authority.getId(), authority.getAuthority()));
    }
}