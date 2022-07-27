package com.tshaojl.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    private Long id; // 用户的唯一标识

    private String name;

    private Long count;

    public Tag(String name, Long count) {
        this.name = name;
        this.count = count;
    }
}
