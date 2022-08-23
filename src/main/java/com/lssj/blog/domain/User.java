package com.lssj.blog.domain;

import lombok.Data;



import java.io.Serializable;


/**
 * User 实体
 */
@Data
public class User implements Serializable {

	// 用户的唯一标识
	private Long id;

	// 用户昵称
	private String name;

	// 用户邮箱
	private String email;

	// 用户账号，用户登录时的唯一标识
	private String username;

	// 登录时密码
	private String password;

	// 头像图片地址
	private String avatar;

	public User() { // JPA 的规范要求无参构造函数；设为 protected 防止直接使用
	}

	public User(String name, String email, String username, String password) {
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
	}





	@Override
	public String toString() {
		return String.format("User[id=%d, username='%s', name='%s', email='%s', password='%s']", id, username, name, email,
				password);
	}
}
