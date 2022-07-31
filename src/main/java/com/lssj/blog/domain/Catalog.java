package com.lssj.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Catalog 实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catalog implements Serializable {

	private Long id;
	private String name;
	private Long userId;

}
