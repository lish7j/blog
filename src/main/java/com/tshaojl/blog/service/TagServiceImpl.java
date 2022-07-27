package com.tshaojl.blog.service;


import com.tshaojl.blog.dao.TagMapper;
import com.tshaojl.blog.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public Tag findByName(String name) {
        Tag tag = tagMapper.findByName(name);
        return tag;
    }

    @Override
    public void saveOrUpdate(Tag tag) {
        Tag originTag = tagMapper.findByName(tag.getName());
        if (originTag == null) {
            tagMapper.insert(tag);
        } else {
            tag.setId(originTag.getId());
            tagMapper.update(tag);
        }
    }
}
