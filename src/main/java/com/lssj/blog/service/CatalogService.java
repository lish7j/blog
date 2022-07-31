package com.lssj.blog.service;

import com.lssj.blog.domain.Catalog;
import com.lssj.blog.domain.User;

import java.util.List;

/**
 * Catalog 服务接口.
 */
public interface CatalogService {
	/**
	 * 保存Catalog
	 */
	void saveCatalog(Catalog catalog);
	
	/**
	 * 删除Catalog
	 */
	void removeCatalog(Long id);

	/**
	 * 根据id获取Catalog
	 */
	Catalog getCatalogById(Long id);
	
	/**
	 * 获取Catalog列表
	 */
	List<Catalog> listCatalogs(User user);
}
