package com.lssj.blog.service.impl;


import com.lssj.blog.dao.TagMapper;
import com.lssj.blog.domain.Tag;
import com.lssj.blog.service.TagService;
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
