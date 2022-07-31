package com.lssj.blog.dao;

import com.lssj.blog.domain.Blog;
import com.lssj.blog.domain.Catalog;
import com.lssj.blog.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;


@Mapper
public interface BlogMapper {
    /**
     * 根据用户名和博客类似名分页查询博客列表
     */
    @Select("select id, comment_size, content, create_time,\n" +
            "               update_time, html_content, read_size, summary,\n" +
            "               tags, title, vote_size, user_id, catalog_id\n" +
            "        from blog where user_id = #{user.id} and title LIKE\n" +
            "        #{title} LIMIT #{start}, #{limit}")
    List<Blog> findByUserAndTitleLike(@Param("user") User user, @Param("title") String title, @Param("start") Integer start, @Param("limit") Integer limit);
    /**
     * 根据用户名分页查询用户列表
     */
    @Select("select id, comment_size, content, create_time,\n" +
            "               update_time, html_content, read_size, summary,\n" +
            "               tags, title, vote_size, user_id, catalog_id\n" +
            " from blog where ( user_id = #{user.id} and title like #{title} ) or (user_id = #{user2.id} and tags like #{tags})")
    List<Blog> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(@Param("title") String title, @Param("user") User user, @Param("tags") String tags, @Param("user2") User user2, @Param("start") Integer start, @Param("limit") Integer limit);
    /**
     * 根据分类查询博客列表
     */

    List<Blog> findByCatalogId(Long catalogId, Integer start, Integer limit);

    Blog findById(Long id);

    void updateBlog(@Param("blog") Blog blog);

    Long findBlogCatalogId(Long id);

    void insertBlog(@Param("blog") Blog blog);

    void deleteById(Long id);

}
