package com.shanchui.controller;

import com.shanchui.model.LoginResult;
import com.shanchui.service.SysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录的控制器
 */

@RestController
@Api(tags = "后台管理系统的登录接口")
public class SysLoginController {

    @Autowired
    private SysLoginService loginService;

    @PostMapping("/login")
    @ApiOperation(value = "后台管理人员登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "password", value = "密码")}
    )
    public LoginResult login(
            @RequestParam(required = true) String username,
            @RequestParam(required = true) String password
    ){
        return loginService.login(username,password);
    }
}
