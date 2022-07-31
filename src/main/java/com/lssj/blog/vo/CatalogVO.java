package com.lssj.blog.vo;


import com.lssj.blog.domain.Catalog;
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
