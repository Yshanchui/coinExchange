package com.shanchui.controller;

import com.shanchui.domain.SysUser;
import com.shanchui.model.R;
import com.shanchui.service.SysUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "后台管理系统的测试接口")
public class TestController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "获取用户信息",authorizations = {@Authorization("Authorization")})
    @GetMapping("/user/info/{id}")
    @ApiImplicitParams(
            @ApiImplicitParam(name= "id", value = "用户id")
    )
    public R<SysUser> getSysUserInfo(@PathVariable("id")Long id){
        SysUser sysUser = sysUserService.getById(id);
        return R.ok(sysUser);
    }
}
