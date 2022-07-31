/**
 * 
 */
package com.lssj.blog.service;

import com.lssj.blog.dao.AuthorityMapper;
import com.lssj.blog.domain.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Authority 服务.
 */
@Service
public class AuthorityServiceImpl  implements AuthorityService {

	private final AuthorityMapper authorityMapper;
	@Autowired
	public AuthorityServiceImpl(AuthorityMapper authorityMapper) {
		this.authorityMapper = authorityMapper;
	}

	@Override
	public Authority getAuthorityById(Long id) {
		return authorityMapper.findById(id);
	}

}
