package com.shanchui.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanchui.domain.SysUser;
import com.shanchui.model.R;
import com.shanchui.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

@RequestMapping("/users")
@Api(tags = "用户管理")
@RestController
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping
    @ApiOperation(value = "分页条件查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码"),
            @ApiImplicitParam(name = "size", value = "每页显示条数"),
            @ApiImplicitParam(name = "mobile", value = "员工手机号"),
            @ApiImplicitParam(name = "fullname", value = "姓名")
    })
    @PreAuthorize("hasAuthority('sys_user_query')")
    public R<Page<SysUser>> findByPage(@ApiIgnore Page<SysUser> page ,String mobile , String fullname) {
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<SysUser> pageDate = sysUserService.findByPage(page,mobile,fullname);
        return R.ok(pageDate);
    }

    @PostMapping
    @ApiOperation(value = "用户的新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "用户的实体类")
    })
    public R addUser(@RequestBody SysUser sysUser) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        sysUser.setCreateBy(userId);
        return sysUserService.addUser(sysUser) ? R.ok() : R.fail("新增失败");
    }

    @PatchMapping
    @ApiOperation(value = "用户的修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "用户的实体类")
    })
    public R updateUser(@RequestBody SysUser sysUser) {
        return sysUserService.updateUser(sysUser) ? R.ok() : R.fail("修改失败");
    }


    @PostMapping("/delete")
    @ApiOperation(value = "用户的删除")
    public R deleteUser(@RequestBody Long ids[]){
         return  sysUserService.removeByIds(Arrays.asList(ids)) ? R.ok() : R.fail("删除失败");
    }
}