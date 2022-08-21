package com.lssj.blog.controller;

import com.lssj.blog.domain.Authority;
import com.lssj.blog.domain.User;
import com.lssj.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页控制器.
 */
@Controller
@Slf4j
public class MainController {
	private static final Long ROLE_USER_AUTHORITY_ID = 2L;
	private final UserService userService;


	@Autowired
	public MainController(UserService userService) {
		this.userService = userService;

	}

	@GetMapping("/")
	public String root() {
		return "redirect:index";
	}
	
	@GetMapping("/index")
	public String index() {
		return "redirect:blogs";
	}
	/**
	 * 获取登录界面
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		model.addAttribute("errorMsg", "登陆失败，账号或者密码错误！");
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	/**
	 * 注册用户
	 */
	@PostMapping("/register")
	public String registerUser(User user) {
		List<Authority> authorities = new ArrayList<>();


		user.setEncodePassword(user.getPassword()); // 加密密码
		userService.saveUser(user);
		log.debug(user.toString());
		return "redirect:/login";
	}
	
	@GetMapping("/search")
	public String search() {
		return "search";
	}
}
