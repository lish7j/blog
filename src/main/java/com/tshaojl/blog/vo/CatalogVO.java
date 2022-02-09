package com.tshaojl.blog.vo;


import com.tshaojl.blog.domain.Catalog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogVO implements Serializable {
	private String username;
	private Catalog catalog;
}
