package com.tshaojl.blog.service;


import com.tshaojl.blog.domain.Tag;
import com.tshaojl.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag findByName(String name) {
        Tag tag = tagRepository.findByName(name);
        return tag;
    }

    @Override
    public void saveOrUpdate(Tag tag) {
        tagRepository.save(tag);
    }
}
