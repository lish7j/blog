package com.tshaojl.blog.repository;

import com.tshaojl.blog.domain.Catalog;
import com.tshaojl.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Catalog 仓库.
 */
public interface CatalogRepository extends JpaRepository<Catalog, Long>{
	
	/**
	 * 根据用户查询
	 */
	List<Catalog> findByUser(User user);
	
	/**
	 * 根据用户查询
	 */
	List<Catalog> findByUserAndName(User user, String name);
}
