package com.lssj.blog.dao;

import com.lssj.blog.domain.Vote;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Component
@Slf4j
public class VoteMapperTest {
    @Autowired
    private VoteMapper voteMapper;

    @Test
    public void findByIdTest() {
        Vote vote = voteMapper.findById(3L);
        Assert.assertNotNull(vote);
        //log.info(String.format("%d %s %s", vote.getId(), vote.getCreateTime(), vote.getUser().toString()));
    }

    @Test
    public void deleteByIdTest() {
        Vote vote = voteMapper.findById(5L);
        Assert.assertNotNull(vote);
        //log.info(String.format("%d %s %s", vote.getId(), vote.getCreateTime(), vote.getUser().toString()));
        voteMapper.deleteById(vote.getId());
    }

}