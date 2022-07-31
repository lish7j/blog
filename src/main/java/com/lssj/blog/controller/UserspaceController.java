package com.lssj.blog.controller;

import com.lssj.blog.domain.*;
import com.lssj.blog.service.*;
import com.lssj.blog.util.ConstraintViolationExceptionHandler;
import com.lssj.blog.vo.Response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户主页空间控制器.
 */
@Controller
@RequestMapping("/u")
@Slf4j
public class UserspaceController {
	private final UserDetailsService userDetailsService;
	private final UserService userService;
	private final BlogService blogService;
	private final CatalogService catalogService;
	private final TagService tagService;

	private final VoteService voteService;

	@Autowired
	public UserspaceController(@Qualifier("UserService") UserDetailsService userDetailsService, UserService userService,
							   BlogService blogService, CatalogService catalogService, TagService tagService, VoteService voteService) {
		this.userDetailsService = userDetailsService;
		this.userService = userService;
		this.blogService = blogService;
		this.catalogService = catalogService;
		this.tagService = tagService;
		this.voteService = voteService;
	}


	@GetMapping("/{username}")
	public String userSpace(@PathVariable("username") String username, Model model) {
		User user = (User)userDetailsService.loadUserByUsername(username);
		model.addAttribute("user", user);
		return "redirect:/u/" + username + "/blogs";
	}
 
	@GetMapping("/{username}/profile")
	@PreAuthorize("authentication.name.equals(#username)") 
	public ModelAndView profile(@PathVariable("username") String username, Model model) {
		User  user = (User)userDetailsService.loadUserByUsername(username);
		model.addAttribute("user", user);
		return new ModelAndView("/userspace/profile", "userModel", model);
	}
 
	/**
	 * 保存个人设置
	 */
	@PostMapping("/{username}/profile")
	@PreAuthorize("authentication.name.equals(#username)") 
	public String saveProfile(@PathVariable("username") String username,User user) {
		User originalUser = userService.getUserById(user.getId());
		originalUser.setEmail(user.getEmail());
		originalUser.setName(user.getName());
		
		// 判断密码是否做了变更
		String rawPassword = originalUser.getPassword();
		PasswordEncoder  encoder = new BCryptPasswordEncoder();
		String encodePasswd = encoder.encode(user.getPassword());
		boolean isMatch = encoder.matches(rawPassword, encodePasswd);
		if (!isMatch) {
			originalUser.setEncodePassword(user.getPassword());
		}
		
		userService.saveUser(originalUser);
		return "redirect:/u/" + username + "/profile";
	}
	
	/**
	 * 获取编辑头像的界面
	 */
	@GetMapping("/{username}/avatar")
	@PreAuthorize("authentication.name.equals(#username)") 
	public ModelAndView avatar(@PathVariable("username") String username, Model model) {
		User  user = (User)userDetailsService.loadUserByUsername(username);
		model.addAttribute("user", user);
		return new ModelAndView("/userspace/avatar", "userModel", model);
	}
	
	
	/**
	 * 保存头像
	 */
	@PostMapping("/{username}/avatar")
	@PreAuthorize("authentication.name.equals(#username)") 
	public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
		String avatarUrl = user.getAvatar();
		
		User originalUser = userService.getUserById(user.getId());
		originalUser.setAvatar(avatarUrl);
		userService.saveUser(originalUser);
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));
	}
	
	
	@GetMapping("/{username}/blogs")
	public String listBlogsByOrder(@PathVariable("username") String username,
			@RequestParam(value="order",required=false,defaultValue="new") String order,
			@RequestParam(value="catalog",required=false ) Long catalogId,
			@RequestParam(value="keyword",required=false,defaultValue="" ) String keyword,
			@RequestParam(value="async",required=false) boolean async,
			@RequestParam(value= "start",required=false,defaultValue="0") int start,
			@RequestParam(value= "limit",required=false,defaultValue="10") int limit,
			Model model) {
		
		User user = (User)userDetailsService.loadUserByUsername(username);
 		
		List<Blog> page = null;
		
		if (catalogId != null && catalogId > 0) { // 分类查询
			page = blogService.listBlogsByCatalog(catalogId, start, limit);
			order = "";
		} else if (order.equals("hot")) { // 最热查询
			//Sort sort = new Sort(Direction.DESC,"readSize","commentSize","voteSize");
			Sort sort = Sort.by("readSize", "commentSize", "voteSize").descending();
			page = blogService.listBlogsByTitleVoteAndSort(user, keyword, start, limit);
		} else if (order.equals("new")) { // 最新查询
			page = blogService.listBlogsByTitleVote(user, keyword, start, limit);
		}


		List<Blog> list = null;	// 当前所在页面数据列表


		model.addAttribute("user", user);
		model.addAttribute("order", order);
		model.addAttribute("catalogId", catalogId);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("blogList", list);
		return (async ?"/userspace/u :: #mainContainerRepleace":"/userspace/u");
	}
	
	/**
	 * 获取博客展示界面
	 */
	@GetMapping("/{username}/blogs/{id}")
	public String getBlogById(@PathVariable("username") String username,@PathVariable("id") Long id, Model model) {
		User principal = null;
		Blog blog = blogService.getBlogById(id);
		
		// 每次读取，简单的可以认为阅读量增加1次
		blogService.readingIncrease(id);

		// 判断操作用户是否是博客的所有者
		boolean isBlogOwner = false;
		if (SecurityContextHolder.getContext().getAuthentication() !=null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				 &&  !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
			principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
			if (principal !=null && username.equals(principal.getUsername())) {
				isBlogOwner = true;
			} 
		}
		
		// 判断操作用户的点赞情况
		List<Vote> votes = voteService.getVoteByBlogId(blog.getId());
		Vote currentVote = null; // 当前用户的点赞情况
		User blogUser = userService.getUserById(blog.getUserId());

		if (principal !=null) {
			for (Vote vote : votes) {
				User user = userService.getUserById(vote.getUserId());
				if (user.getUsername().equals(principal.getUsername())) {
					currentVote = vote;
				}
			}
		}

		model.addAttribute("isBlogOwner", isBlogOwner);
		model.addAttribute("blogModel",blog);
		model.addAttribute("user", blogUser);
		model.addAttribute("currentVote",currentVote);
		
		return "/userspace/blog";
	}
	
	
	/**
	 * 删除博客
	 */
	@DeleteMapping("/{username}/blogs/{id}")
	@PreAuthorize("authentication.name.equals(#username)") 
	public ResponseEntity<Response> deleteBlog(@PathVariable("username") String username,@PathVariable("id") Long id) {
		
		try {
			blogService.removeBlog(id);
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		String redirectUrl = "/u/" + username + "/blogs";
		return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
	}
	
	/**
	 * 获取新增博客的界面
	 */
	@GetMapping("/{username}/blogs/edit")
	public ModelAndView createBlog(@PathVariable("username") String username, Model model) {
		User user = (User)userDetailsService.loadUserByUsername(username);
		List<Catalog> catalogs = catalogService.listCatalogs(user);
		
		model.addAttribute("blog", new Blog(null, null, null));
		model.addAttribute("catalogs", catalogs);
		return new ModelAndView("/userspace/blogedit", "blogModel", model);
	}
	
	/**
	 * 获取编辑博客的界面
	 */
	@GetMapping("/{username}/blogs/edit/{id}")
	public ModelAndView editBlog(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
		// 获取用户分类列表
		User user = (User)userDetailsService.loadUserByUsername(username);
		List<Catalog> catalogs = catalogService.listCatalogs(user);
		
		model.addAttribute("blog", blogService.getBlogById(id));
		model.addAttribute("catalogs", catalogs);
		return new ModelAndView("/userspace/blogedit", "blogModel", model);
	}
	
	/**
	 * 保存博客
	 */
	@PostMapping("/{username}/blogs/edit")
	@PreAuthorize("authentication.name.equals(#username)") 
	public ResponseEntity<Response> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {
		// 对 Catalog 进行空处理
		if (blog.getCatalog() == null || blog.getCatalog().getId() == null) {
			return ResponseEntity.ok().body(new Response(false,"未选择分类"));
		}
		if (blog.getCreateTime() == null) {
			blog.setCreateTime(new Timestamp(System.currentTimeMillis()));
		}
		blog.setUpdateTime(new Timestamp(System.currentTimeMillis()));

		try {

			// 判断是修改还是新增

			log.info("编辑博客时 " + blog.getTags() + " id " + (blog.getId() == null));
			if (blog.getId()!=null) {
				Blog orignalBlog = blogService.getBlogById(blog.getId());
				String[] tags = blog.getTags().split(",");
				String[] originTags = orignalBlog.getTags().split(",");
				orignalBlog.setTitle(blog.getTitle());
				orignalBlog.setContent(blog.getContent());
				orignalBlog.setSummary(blog.getSummary());
				orignalBlog.setCatalog(blog.getCatalog());
				orignalBlog.setTags(blog.getTags());
				blogService.saveBlog(orignalBlog);
				Map<String, Long> tagMap = new HashMap<>();
				for (String tagStr : tags) {
					Tag tag = tagService.findByName(tagStr);
					if (tag == null) {
						tag = new Tag(tagStr, 0L);
					}
					tag.setCount(tag.getCount() + 1) ;
					tagService.saveOrUpdate(tag);
				}
				for (String tagStr: originTags) {
					Tag tag = tagService.findByName(tagStr);
					if (tag == null) {
						tag = new Tag(tagStr, 0L);
					}
					tag.setCount(tag.getCount() - 1) ;
					tagService.saveOrUpdate(tag);
				}
	        } else {
	    		User user = (User)userDetailsService.loadUserByUsername(username);
	    		blog.setUserId(user.getId());
				blogService.saveBlog(blog);
				String[] tags = blog.getTags().split(",");
				for (String tagStr : tags) {
					Tag tag = tagService.findByName(tagStr);
					if (tag == null) {
						tag = new Tag(tagStr, 0L);
					}
					tag.setCount(tag.getCount() + 1) ;
					tagService.saveOrUpdate(tag);
				}

	        }
			
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
		return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
	}
}
