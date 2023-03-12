package com.shanchui.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanchui.domain.User;
import com.shanchui.model.R;
import com.shanchui.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/users")
@Api(tags = "会员管理")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping
    @ApiOperation(value = "查询会员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数"),
            @ApiImplicitParam(name = "mobile", value = "手机号"),
            @ApiImplicitParam(name = "userId", value = "用户ID"),
            @ApiImplicitParam(name = "usreName", value = "用户名"),
            @ApiImplicitParam(name = "realName", value = "真实姓名"),
            @ApiImplicitParam(name = "Status", value = "状态"),
    })
    @PreAuthorize("hasAuthority('user_query')")
    public R<Page<User>> findByPage(@ApiIgnore Page<User> page,
                                    String mobile, Long userId, String userName, String realName, Integer status) {
        page.addOrder(OrderItem.desc("last_update_time"));
        return R.ok(userService.findByPage(page, mobile, userId, userName, realName, status));
    }

    @PostMapping("/status")
    @ApiOperation(value = "修改会员状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID"),
            @ApiImplicitParam(name = "status", value = "状态"),
    })
    @PreAuthorize("hasAuthority('user_update')")
    public R updateStatus(Long id, Byte status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        return userService.updateById(user) ? R.ok() : R.fail("修改失败");
    }

    @PatchMapping
    @ApiOperation(value = "修改会员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "user 的json数据")
    })
    @PreAuthorize("hasAuthority('user_update')")
    public R update(@RequestBody User user) {
        return userService.updateById(user) ? R.ok() : R.fail("修改失败");
    }

    @GetMapping("/info")
    @ApiOperation(value = "查询会员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID"),
    })
    @PreAuthorize("hasAuthority('user_query')")
    public R<User> userInfo(Long id) {
        return R.ok(userService.getById(id));
    }
}
