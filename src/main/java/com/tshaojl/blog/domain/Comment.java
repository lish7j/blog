package com.tshaojl.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Comment 实体
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id; // 用户的唯一标识

	private String content;

	private Long userId;

	private Timestamp createTime;

}
