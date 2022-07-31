package com.lssj.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Like 实体
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vote implements Serializable {

	private Long id; // 用户的唯一标识
	private Long userId;
	private Timestamp createTime;

}
