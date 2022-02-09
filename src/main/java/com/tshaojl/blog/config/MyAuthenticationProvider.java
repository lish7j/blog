package com.tshaojl.blog.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


@Component("MyAuthenticationProvider")
public class MyAuthenticationProvider extends DaoAuthenticationProvider {
    @Autowired
   public MyAuthenticationProvider(@Qualifier("UserService")UserDetailsService userDetailsService, MyPasswordEncoder passwordEncoder) {
       super.setUserDetailsService(userDetailsService);
       super.setPasswordEncoder(passwordEncoder); // 设置密码加密方式
   }
}
