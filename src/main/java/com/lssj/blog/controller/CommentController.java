package com.lssj.blog.controller;

import com.lssj.blog.domain.Blog;
import com.lssj.blog.domain.User;
import com.lssj.blog.service.BlogService;
import com.lssj.blog.service.UserService;
import com.lssj.blog.util.ConstraintViolationExceptionHandler;
import com.lssj.blog.vo.Response;
import com.lssj.blog.domain.Comment;
import com.lssj.blog.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;


/**
 * 评论 控制器.
 */
@Controller
@RequestMapping("/comments")
public class CommentController {
	
	private final BlogService blogService;

	private final UserService userService;
	
	private final CommentService commentService;

	@Autowired
	public CommentController(BlogService blogService, UserService userService, CommentService commentService) {
		this.blogService = blogService;
		this.userService = userService;
		this.commentService = commentService;
	}

	/**
	 * 获取评论列表
	 */
	@GetMapping
	public String listComments(@RequestParam("username") String username, @RequestParam(value="blogId") Long blogId, Model model) {
		Blog blog = blogService.getBlogById(blogId);
		List<Comment> comments = blog.getComments();
		User user = userService.findByUsername(username);
		// 判断操作用户是否是评论的所有者
		String commentOwner = "";
		if (user.getId().equals(blog.getUserId())) {
			commentOwner = username;
		}
		
		model.addAttribute("commentOwner", commentOwner);
		model.addAttribute("comments", comments);
		return "/userspace/blog :: #mainContainerRepleace";
	}
	/**
	 * 发表评论
	 */
	@PostMapping
	public ResponseEntity<Response> createComment(Long blogId, String commentContent) {
 
		try {
			blogService.createComment(blogId, commentContent);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
	
	/**
	 * 删除评论
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> delete(@RequestParam("username") String username, @PathVariable("id") Long id, Long blogId) {
		
		boolean isOwner = false;
		Long userId = commentService.getCommentById(id).getUserId();
		User user = userService.getUserById(userId);
		// 判断操作用户是否是评论的所有者
		if (user.getUsername().equals(username)) {
			isOwner = true;
		}
		
		if (!isOwner) {
			return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
		}
		
		try {
			blogService.removeComment(blogId, id);
			commentService.removeComment(id);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
}
