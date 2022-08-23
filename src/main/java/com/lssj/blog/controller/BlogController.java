package com.lssj.blog.controller;

import com.lssj.blog.domain.Blog;
import com.lssj.blog.domain.User;
import com.lssj.blog.service.BlogService;
import com.lssj.blog.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页控制器.
 */
@Controller
@RequestMapping("/blogs")
public class BlogController {

    private BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public String listEsBlogs(
            @RequestParam(value = "order", required = false, defaultValue = "new") String order,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "async", required = false) boolean async,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            Model model) {

        boolean isEmpty = true; // 系统初始化时，没有博客数据
        try {
            if (order.equals("hot")) { // 最热查询

            } else if (order.equals("new")) { // 最新查询

            }

            isEmpty = false;
        } catch (Exception e) {
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
        }

        List<Blog> list = blogService.findAll();

        model.addAttribute("order", order);
        model.addAttribute("keyword", keyword);
//        model.addAttribute("page", page);
        model.addAttribute("blogList", list);

        // 首次访问页面才加载
        if (!async && !isEmpty) {
            List<Blog> newest = new ArrayList<>();
            model.addAttribute("newest", newest);
            List<Blog> hotest = new ArrayList<>();
            model.addAttribute("hotest", hotest);
            List<TagVO> tags = new ArrayList<>();
            model.addAttribute("tags", tags);
            List<User> users = new ArrayList<>();
            model.addAttribute("users", users);
        }

        return (async ? "/index :: #mainContainerRepleace" : "/index");
    }

    @GetMapping("/newest")
    public String listNewestEsBlogs(Model model) {

        List<Blog> newest = new ArrayList<>();
        model.addAttribute("newest", newest);
        return "newest";
    }

    @GetMapping("/hotest")
    public String listHotestEsBlogs(Model model) {
        List<Blog> hotest = new ArrayList<>();
        model.addAttribute("hotest", hotest);
        return "hotest";
    }


}
