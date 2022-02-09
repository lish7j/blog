/**
 * 
 */
package com.tshaojl.blog.service;

import com.tshaojl.blog.domain.Authority;
import com.tshaojl.blog.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Authority 服务.
 */
@Service
public class AuthorityServiceImpl  implements AuthorityService {
	
	private final AuthorityRepository authorityRepository;

	@Autowired
	public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
		this.authorityRepository = authorityRepository;
	}

	@Override
	public Authority getAuthorityById(Long id) {
		return authorityRepository.findById(id).orElse(null);
	}

}
