package com.lssj.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Like 实体
 * @author lssj
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vote implements Serializable {

	private Long id;
	private Long userId;
	private Long blogId;
	private Timestamp createTime;

}
