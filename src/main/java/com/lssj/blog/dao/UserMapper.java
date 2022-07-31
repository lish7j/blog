package com.lssj.blog.dao;

import com.lssj.blog.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.Collection;
import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findByNameLike(String name, Integer start, Integer limit);

    /**
     * 根据名称查询
     */
    User findByUsername(String username);
    /**
     * 根据ID查询
     */
    User findById(Long id);

    /**
     * 根据名称列表查询
     */
    List<User> findByUsernameIn(Collection<String> usernames);

    void addUser(@Param("user") User user);

    void deleteById(Long id);

    void deleteInBatch(List<User> users);

    void updateUser(User user);

    List<User> findAll();
}
