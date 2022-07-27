package com.tshaojl.blog.service;

import com.tshaojl.blog.dao.UserMapper;
import com.tshaojl.blog.domain.User;
import com.tshaojl.blog.repository.UserRepository;
import org.apache.lucene.analysis.standard.UAX29URLEmailAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

/**
 * User 服务.
 */
@Service("UserService")
public class UserServiceImpl  implements UserService , UserDetailsService {

	private final UserMapper userMapper;

	@Autowired
	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Transactional
	@Override
	public User saveUser(User user) {
		return userMapper.addUser(user);
	}

	@Transactional
	@Override
	public void removeUser(Long id) {
		userMapper.deleteById(id);
	}

	@Transactional
	@Override
	public void removeUsersInBatch(List<User> users) {
		userMapper.deleteInBatch(users);
	}
	
	@Transactional
	@Override
	public User updateUser(User user) {
		userMapper.updateUser(user);
		return userMapper.findById(user.getId());
	}

	@Override
	public User getUserById(Long id) {
		return userMapper.findById(id);
	}

	@Override
	public List<User> listUsers() {
		return userMapper.findAll();
	}

	@Override
	public List<User> listUsersByNameLike(String name, Integer start, Integer limit) {
		// 模糊查询
		return userMapper.findByNameLike(name, start, limit);
	}

	public UserDetails loadUserByUsername(String username){
		return userMapper.findByUsername(username);
	}

	@Override
	public List<User> listUsersByUsernames(Collection<String> usernames) {
		return userMapper.findByUsernameIn(usernames);
	}

}
