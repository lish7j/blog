package com.lssj.blog.controller;

import com.lssj.blog.domain.Authority;
import com.lssj.blog.domain.User;
import com.lssj.blog.util.ConstraintViolationExceptionHandler;
import com.lssj.blog.vo.Response;
import com.lssj.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;


/**
 * 用户控制器.
 */
@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;


	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 查询所用用户
	 */
	@GetMapping
	public ModelAndView list(@RequestParam(value="async",required=false) boolean async,
			@RequestParam(value= "start",required=false,defaultValue="0") int start,
			@RequestParam(value= "limit",required=false,defaultValue="10") int limit,
			@RequestParam(value="name",required=false,defaultValue="") String name,
			Model model) {


		List<User> users = userService.listUsersByNameLike(name, start, limit);
		// 当前所在页面数据列表
		
//		model.addAttribute("page", page);
		model.addAttribute("userList", users);
		return new ModelAndView(async ?"users/list :: #mainContainerRepleace":"users/list", "userModel", model);
	}

	/**
	 * 获取 form 表单页面
	 */
	@GetMapping("/add")
	public ModelAndView createForm(Model model) {
		model.addAttribute("user", new User(null, null, null, null));
		return new ModelAndView("users/add", "userModel", model);
	}

	/**
	 * 新建用户
	 */
	@PostMapping
	public ResponseEntity<Response> create(User user, Long authorityId) {
		List<Authority> authorities = new ArrayList<>();
		
		if(user.getId() == null) {

		}else {
			// 判断密码是否做了变更
			User originalUser = userService.getUserById(user.getId());
			String rawPassword = originalUser.getPassword();
		}
		
		try {
			userService.saveUser(user);
		}  catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		}
		return ResponseEntity.ok().body(new Response(true, "处理成功", user));
	}

	/**
	 * 删除用户
	 */
	@DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
		try {
			userService.removeUser(id);
		} catch (Exception e) {
			return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
		}
		return  ResponseEntity.ok().body( new Response(true, "处理成功"));
	}
	
	/**
	 * 获取修改用户的界面，及数据
	 */
	@GetMapping(value = "edit/{id}")
	public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return new ModelAndView("users/edit", "userModel", model);
	}
}
