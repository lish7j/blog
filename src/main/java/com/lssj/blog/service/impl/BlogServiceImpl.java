package com.lssj.blog.service.impl;

import com.lssj.blog.dao.*;
import com.lssj.blog.domain.*;
import com.lssj.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
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

	private final CatalogMapper catalogMapper;

	private final VoteMapper voteMapper;

	private final CommentMapper commentMapper;

	@Autowired
	public BlogServiceImpl(BlogMapper blogMapper, UserMapper userMapper,
						   CatalogMapper catalogMapper,
						   VoteMapper voteMapper, CommentMapper commentMapper) {
		this.blogMapper = blogMapper;
		this.userMapper = userMapper;
		this.catalogMapper = catalogMapper;
		this.voteMapper = voteMapper;
		this.commentMapper = commentMapper;
	}

	@Transactional
	@Override
	public void saveBlog(Blog blog) {
		boolean isNew = (blog.getId() == null);

		if (blogMapper.findById(blog.getId()) == null) {
			blogMapper.insertBlog(blog);
		} else {
			blogMapper.updateBlog(blog);
		}
		Blog returnBlog = blogMapper.findById(blog.getId());
		User user = userMapper.findById(blog.getUserId());

	}

	@Transactional
	@Override
	public void removeBlog(Long id) {
		blogMapper.deleteById(id);
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
		User user = userMapper.findById(originalBlog.getUserId());
		Comment comment = new Comment();
		comment.setBlogId(blogId);
		comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
		comment.setContent(commentContent);
		comment.setUserId(user.getId());
		if (originalBlog != null) {
			originalBlog.setCommentSize(originalBlog.getCommentSize() + 1);
		}
		blogMapper.updateBlog(originalBlog);
		commentMapper.insert(comment);
	}

	@Override
	@Transactional
	public void removeComment(Long blogId, Long commentId) {
		Blog originalBlog = blogMapper.findById(blogId);
		if (originalBlog != null) {
			originalBlog.setCommentSize(originalBlog.getCommentSize() - 1);
		}
		blogMapper.updateBlog(originalBlog);
		commentMapper.deleteById(commentId);
	}

	@Override
	@Transactional()
	public void createVote(Long blogId) {
		Blog originalBlog = blogMapper.findById(blogId);
		User user = userMapper.findById(originalBlog.getUserId());
		Vote vote = voteMapper.findByUserIdAndBlogId(user.getId(), blogId);
		if (vote != null) {
			throw new IllegalArgumentException("该用户已经点过赞了");
		}
		vote = new Vote();
		vote.setCreateTime(new Timestamp(System.currentTimeMillis()));
		vote.setUserId(user.getId());
		vote.setBlogId(blogId);
		originalBlog.setVoteSize(originalBlog.getVoteSize() + 1);
		voteMapper.insert(vote);
		blogMapper.updateBlog(originalBlog);
	}

	@Override
	@Transactional
	public void removeVote(Long blogId, Long voteId) {
		Blog originalBlog = blogMapper.findById(blogId);
		if (originalBlog != null && originalBlog.getVoteSize() > 0) {
			originalBlog.setVoteSize(originalBlog.getVoteSize() - 1);
		}
		blogMapper.updateBlog(originalBlog);
	}

	@Override
	public List<Blog> findAll() {
		return blogMapper.findAll();
	}
}
