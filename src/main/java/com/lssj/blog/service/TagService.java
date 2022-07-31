package com.lssj.blog.service;

import com.lssj.blog.domain.Tag;

public interface TagService {
    Tag findByName(String name);
    void saveOrUpdate(Tag tag);
}
