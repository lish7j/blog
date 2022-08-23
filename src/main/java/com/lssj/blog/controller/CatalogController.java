package com.lssj.blog.controller;

import com.lssj.blog.domain.Catalog;
import com.lssj.blog.domain.User;
import com.lssj.blog.service.CatalogService;
import com.lssj.blog.service.UserService;
import com.lssj.blog.util.ConstraintViolationExceptionHandler;
import com.lssj.blog.vo.CatalogVO;
import com.lssj.blog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;


/**
 * 分类 控制器.
 */
@Controller
@RequestMapping("/catalogs")
public class CatalogController {
	
	private final CatalogService catalogService;

	private final UserService userService;

	@Autowired
	public CatalogController(CatalogService catalogService, UserService userService) {
		this.catalogService = catalogService;
		this.userService = userService;
	}

	/**
	 * 获取分类列表
	 */
	@GetMapping
	public String listComments(@RequestParam(value="username") String username, Model model) {
		User user = userService.findByUsername(username);
		List<Catalog> catalogs = catalogService.listCatalogs(user);

		// 判断操作用户是否是分类的所有者
		boolean isOwner = true;
		model.addAttribute("isCatalogsOwner", isOwner);
		model.addAttribute("catalogs", catalogs);
		return "/userspace/u :: #catalogRepleace";
	}
	/**
	 * 发表分类
	 */
	@PostMapping
	public ResponseEntity<Response> create(@RequestBody CatalogVO catalogVO) {
		
		String username = catalogVO.getUsername();
		Catalog catalog = catalogVO.getCatalog();
		
		User user = userService.findByUsername(username);
		
		try {
			catalog.setUserId(user.getId());
			catalogService.saveCatalog(catalog);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
	
	/**
	 * 删除分类
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> delete(String username, @PathVariable("id") Long id) {
		try {
			catalogService.removeCatalog(id);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
	
	/**
	 * 获取分类编辑界面
	 */
	@GetMapping("/edit")
	public String getCatalogEdit(Model model) {
		Catalog catalog = new Catalog();
		model.addAttribute("catalog",catalog);
		return "/userspace/catalogedit";
	}
	/**
	 * 根据 Id 获取分类信息
	 */
	@GetMapping("/edit/{id}")
	public String getCatalogById(@PathVariable("id") Long id, Model model) {
		Catalog catalog = catalogService.getCatalogById(id);
		model.addAttribute("catalog",catalog);
		return "/userspace/catalogedit";
	}
	
}
