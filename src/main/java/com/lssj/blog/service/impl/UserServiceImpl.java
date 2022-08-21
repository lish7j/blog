package com.lssj.blog.service.impl;

import com.lssj.blog.dao.UserMapper;
import com.lssj.blog.domain.User;
import com.lssj.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User 服务.
 */
@Service("UserService")
@Slf4j
public class UserServiceImpl  implements UserService {

	private final UserMapper userMapper;

	@Autowired
	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Transactional
	@Override
	public void saveUser(User user) {
		userMapper.addUser(user);
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

	public User loadUserByUsername(String username){
		return userMapper.findByUsername(username);
	}

	@Override
	public List<User> listUsersByUsernames(Collection<String> usernames) {
		log.info(usernames.toString());
		if (usernames == null || usernames.size() == 0)
			return new ArrayList<>();
		return userMapper.findByUsernameIn(usernames);
	}

}
