package com.tshaojl.blog.service;

import com.tshaojl.blog.domain.Tag;

public interface TagService {
    Tag findByName(String name);
    void saveOrUpdate(Tag tag);
}
