package com.lssj.blog.controller;

import com.lssj.blog.service.UserService;
import com.lssj.blog.service.VoteService;
import com.lssj.blog.domain.User;
import com.lssj.blog.service.BlogService;
import com.lssj.blog.util.ConstraintViolationExceptionHandler;
import com.lssj.blog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;


/**
 * 点赞控制器.
 */
@Controller
@RequestMapping("/votes")
public class VoteController {

	private final UserService userService;
	private final BlogService blogService;
	private final VoteService voteService;

	@Autowired
	public VoteController(UserService userService, BlogService blogService, VoteService voteService) {
		this.userService = userService;
		this.blogService = blogService;
		this.voteService = voteService;
	}

	/**
	 * 发表点赞
	 */
	@PostMapping
	public ResponseEntity<Response> createVote(Long blogId) {
 
		try {
			blogService.createVote(blogId);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "点赞成功", null));
	}
	
	/**
	 * 删除点赞
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> delete(@PathVariable("id") Long id,
										   @RequestParam(value = "username") String username,
										   Long blogId) {
		
		boolean isOwner = false;
		Long userId = voteService.getVoteById(id).getUserId();
		User user = userService.getUserById(userId);
		
		// 判断操作用户是否是点赞的所有者
		if (user.getUsername().equals(username)) {
			isOwner = true;
		}
		if (!isOwner) {
			return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
		}
		
		try {
			blogService.removeVote(blogId, id);
			voteService.removeVote(id);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "取消点赞成功", null));
	}
}
