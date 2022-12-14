package com.lssj.blog.dao;

import com.lssj.blog.domain.Catalog;
import com.lssj.blog.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CatalogMapper {
    /**
     * 根据用户查询
     */
    List<Catalog> findByUser(User user);

    List<Catalog> findByUserId(Long userId);

    /**
     * 根据用户查询
     */
    Catalog findByUserAndName(Long userId, String name);

    void deleteById(Long id);

    Catalog findById(Long id);

    void insert(Catalog catalog);
}
