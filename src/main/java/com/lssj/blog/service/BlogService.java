package com.lssj.blog.service;

import com.lssj.blog.domain.Catalog;
import com.lssj.blog.domain.User;
import com.lssj.blog.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Blog 服务接口.
 */
public interface BlogService {
	/**
	 * 保存Blog
	 */
	void saveBlog(Blog blog);
	
	/**
	 * 删除Blog
	 */
	void removeBlog(Long id);
	
	/**
	 * 根据id获取Blog
	 */
	Blog getBlogById(Long id);
	
	/**
	 * 根据用户名进行分页模糊查询（最新）
	 */
	List<Blog> listBlogsByTitleVote(User user, String title, Integer start, Integer limit);
 
	/**
	 * 根据用户名进行分页模糊查询（最热）
	 */
	List<Blog> listBlogsByTitleVoteAndSort(User user, String title, Integer start, Integer limit);
	
	/**
	 * 根据分类进行查询
	 */
	List<Blog> listBlogsByCatalog(Long catalogId, Integer start, Integer limit);
	/**
	 * 阅读量递增
	 */
	void readingIncrease(Long id);
	
	/**
	 * 发表评论
	 */
	void createComment(Long blogId, String commentContent);
	
	/**
	 * 删除评论
	 */
	void removeComment(Long blogId, Long commentId);
	
	/**
	 * 点赞
	 */
	void createVote(Long blogId);
	
	/**
	 * 取消点赞
	 */
	void removeVote(Long blogId, Long voteId);
}
