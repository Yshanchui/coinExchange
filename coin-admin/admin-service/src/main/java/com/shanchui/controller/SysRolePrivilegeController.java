package com.shanchui.controller;

import com.shanchui.domain.SysMenu;
import com.shanchui.model.R;
import com.shanchui.model.RolePrivilegesParam;
import com.shanchui.service.SysRolePrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "角色权限管理")
@RestController
public class SysRolePrivilegeController {

    @Autowired
    private SysRolePrivilegeService sysRolePrivilegeService;

    @GetMapping("/roles_privileges")
    @ApiOperation(value = "查询角色权限列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID")
    })
    public R<List<SysMenu>> findSysMenuAndPrivilege(Long roleId) {
        return R.ok(sysRolePrivilegeService.findSysMenuAndPrivilege(roleId));
    }

    @PostMapping("/grant_privileges")
    @ApiOperation(value = "角色授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rolePrivilegesParame", value = "角色权限参数"),
    })
    public R grantPrivileges(@RequestBody RolePrivilegesParam rolePrivilegesParame) {
        return sysRolePrivilegeService.grantPrivileges(rolePrivilegesParame) ? R.ok() : R.fail("授权失败");

    }
}
