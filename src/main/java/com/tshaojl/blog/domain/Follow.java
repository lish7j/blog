package com.tshaojl.blog.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Follow {
    private Long id;
    private Long followId;
    private Long followedId;
}
