package com.shanchui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoController {

    /*
    * 当前登陆的用户对象
    * */
    @GetMapping("/user/info")
    public Principal getUserInfo(Principal pringcipal){
        //使用ThreadLocal来实现的

        return pringcipal;
    }
}
