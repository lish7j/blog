package com.lssj.blog.service;

import com.lssj.blog.dao.BlogMapper;
import com.lssj.blog.dao.CatalogMapper;
import com.lssj.blog.dao.UserMapper;
import com.lssj.blog.domain.*;
import com.lssj.blog.domain.es.EsBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

/**
 * Blog 服务.
 */
@Service
public class BlogServiceImpl implements BlogService {

	private final BlogMapper blogMapper;

	private final UserMapper userMapper;
	private final EsBlogService esBlogService;

	private final CatalogMapper catalogMapper;

	@Autowired
	public BlogServiceImpl(BlogMapper blogMapper, UserMapper userMapper,
						   EsBlogService esBlogService, CatalogMapper catalogMapper) {
		this.blogMapper = blogMapper;
		this.userMapper = userMapper;
		this.esBlogService = esBlogService;
		this.catalogMapper = catalogMapper;
	}

	@Transactional
	@Override
	public void saveBlog(Blog blog) {
		boolean isNew = (blog.getId() == null);
		EsBlog esBlog;
		if (blogMapper.findById(blog.getId()) == null) {
			blogMapper.insertBlog(blog);
		} else {
			blogMapper.updateBlog(blog);
		}
		Blog returnBlog = blogMapper.findById(blog.getId());
		User user = userMapper.findById(blog.getUserId());
		if (isNew) {
			esBlog = new EsBlog(blog, user);
		} else {
			esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
			esBlog.update(returnBlog, user);
		}
		
		esBlogService.updateEsBlog(esBlog);
	}

	@Transactional
	@Override
	public void removeBlog(Long id) {
		blogMapper.deleteById(id);
		EsBlog esblog = esBlogService.getEsBlogByBlogId(id);
		esBlogService.removeEsBlog(esblog.getId());
	}

	@Override
	public Blog getBlogById(Long id) {
		Blog blog = blogMapper.findById(id);
		Catalog catalog = catalogMapper.findById(blog.getCatalogId());
		blog.setCatalog(catalog);
		return blog;
	}

	@Override
	public List<Blog> listBlogsByTitleVote(User user, String title, Integer start, Integer limit) {
		// 模糊查询
		title = "%" + title + "%";
		String tags = title;
		return blogMapper.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(title,user, tags,user, start, limit);
	}

	@Override
	public List<Blog> listBlogsByTitleVoteAndSort(User user, String title, Integer start, Integer limit) {
		// 模糊查询
		title = "%" + title + "%";
		return blogMapper.findByUserAndTitleLike(user, title, start, limit);
	}
	
	@Override
	public List<Blog> listBlogsByCatalog(Long catalogId, Integer start, Integer limit) {
		return blogMapper.findByCatalogId(catalogId, start, limit);
	}

	@Override
	@Transactional
	public void readingIncrease(Long id) {
		Blog blog = blogMapper.findById(id);
		if (blog != null) {
			blog.setReadSize(blog.getCommentSize()+1);
		}
		blogMapper.updateBlog(blog);
	}

	@Override
	@Transactional
	public void createComment(Long blogId, String commentContent) {
		Blog originalBlog = blogMapper.findById(blogId);
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		Comment comment = new Comment();
		comment.setContent(commentContent);
		comment.setUserId(user.getId());
		Timestamp nowTimestamp = new Timestamp(System.currentTimeMillis());
		comment.setCreateTime(nowTimestamp);
		if (originalBlog != null) {
			originalBlog.addComment(comment);
		}
		blogMapper.updateBlog(originalBlog);
	}

	@Override
	@Transactional
	public void removeComment(Long blogId, Long commentId) {
		Blog originalBlog = blogMapper.findById(blogId);
		if (originalBlog != null) {
			originalBlog.removeComment(commentId);
		}
		blogMapper.updateBlog(originalBlog);
	}

	@Override
	@Transactional
	public void createVote(Long blogId) {
		Blog originalBlog = blogMapper.findById(blogId);
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		Vote vote = new Vote();
		vote.setUserId(user.getId());
		vote.setCreateTime(new Timestamp(System.currentTimeMillis()));
		boolean isExist = false;
		if (originalBlog != null) {
			isExist = originalBlog.addVote(vote);
		}
		if (isExist) {
			throw new IllegalArgumentException("该用户已经点过赞了");
		}
		blogMapper.updateBlog(originalBlog);
	}

	@Override
	@Transactional
	public void removeVote(Long blogId, Long voteId) {
		Blog originalBlog = blogMapper.findById(blogId);
		if (originalBlog != null) {
			originalBlog.removeVote(voteId);
		}
		blogMapper.updateBlog(originalBlog);
	}
}
