package com.lssj.blog.dao;

import com.lssj.blog.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Component
@Slf4j
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void findByNameLike() {
        List<User> userList = userMapper.findByNameLike("xx", 0, 10);
        log.info(userList.toString());
    }

    @Test
    void findByUsername() {
        User user = userMapper.findByUsername("ekyu");
        log.info(user.toString());
    }

    @Test
    void findByUsernameIn() {
        List<String> usernames = new ArrayList<>();
        usernames.add("ekyu");
        usernames.add("ekuy");
        List<User> users = userMapper.findByUsernameIn(usernames);
        log.info(users.toString());
    }
}
