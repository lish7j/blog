package com.tshaojl.blog.service;

import com.tshaojl.blog.dao.CatalogMapper;
import com.tshaojl.blog.domain.Catalog;
import com.tshaojl.blog.domain.User;
import com.tshaojl.blog.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Catalog 服务.
 */
@Service
public class CatalogServiceImpl implements CatalogService{

	private final CatalogMapper catalogMapper;

	@Autowired
	public CatalogServiceImpl(CatalogMapper catalogMapper) {
		this.catalogMapper = catalogMapper;
	}

	@Override
	public void saveCatalog(Catalog catalog) {
		// 判断重复
		Catalog oldCatalog = catalogMapper.findByUserAndName(catalog.getUserId(), catalog.getName());
		if (catalog != null) {
			throw new IllegalArgumentException("该分类已经存在了");
		}
		catalogMapper.insert(catalog);
	}

	@Override
	public void removeCatalog(Long id) {
		catalogMapper.deleteById(id);
	}

	@Override
	public Catalog getCatalogById(Long id) {
		return catalogMapper.findById(id);
	}

	@Override
	public List<Catalog> listCatalogs(User user) {
		return catalogMapper.findByUser(user);
	}

}
