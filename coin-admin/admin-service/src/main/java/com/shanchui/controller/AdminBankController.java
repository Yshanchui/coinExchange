package com.shanchui.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanchui.domain.AdminBank;
import com.shanchui.model.R;
import com.shanchui.service.AdminBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

@RestController
@RequestMapping("/adminBanks")
@Api(tags = "银行卡管理")
public class AdminBankController {

    @Autowired
    private AdminBankService adminBankService;

    @GetMapping
    @ApiOperation(value = "条件分页查询银行卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数"),
            @ApiImplicitParam(name = "name", value = "银行卡名称"),
    })
    @PreAuthorize("hasAuthority('admin_bank_query')")
    public R<Page<AdminBank>> findByPage(@ApiIgnore Page<AdminBank> page, String name) {
        return R.ok(adminBankService.findByPage(page, name));
    }

    @PostMapping
    @ApiOperation(value = "新增一个银行卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminBank", value = "adminBank的json数据")
    })
    @PreAuthorize("hasAuthority('admin_bank_create')")
    public R add(@RequestBody AdminBank adminBank) {
        return adminBankService.save(adminBank) ? R.ok("新增成功") : R.fail("新增失败");
    }

    @PatchMapping
    @ApiOperation(value = "修改一个银行卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminBank", value = "adminBank的json数据")
    })
    @PreAuthorize("hasAuthority('admin_bank_update')")
    public R update(@RequestBody AdminBank adminBank) {
        return adminBankService.updateById(adminBank) ? R.ok("修改成功") : R.fail("修改失败");
    }

    @PostMapping("/adminUpdateBankStatus")
    @ApiOperation(value = "修改银行卡状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "银行卡id"),
            @ApiImplicitParam(name = "status", value = "银行卡状态")
    })
    @PreAuthorize("hasAuthority('admin_bank_update')")
    public R changeStatus(Long bankId, Byte status) {
        AdminBank adminBank = new AdminBank();
        adminBank.setId(bankId);
        adminBank.setStatus(status);
        return adminBankService.updateById(adminBank) ? R.ok("修改成功") : R.fail("修改失败");
    }
}
