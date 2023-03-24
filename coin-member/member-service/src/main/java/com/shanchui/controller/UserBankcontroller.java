package com.shanchui.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanchui.domain.UserBank;
import com.shanchui.model.R;
import com.shanchui.service.UserBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userBanks")
@Api(tags = "用户银行卡管理")
public class UserBankcontroller {

    @Autowired
    private UserBankService userBankService;

    @GetMapping
    @ApiOperation(value = "查询用户银行卡列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "usrId", value = "用户ID"),
    })
    @PreAuthorize("hasAuthority('user_bank_query')")
    public R<Page<UserBank>> findbyPage(Page<UserBank> page, Long usrId) {
        page.addOrder(OrderItem.desc("last_update_time"));
        return R.ok(userBankService.findByPage(page, usrId));
    }

    @PostMapping("/status")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户银行卡ID"),
            @ApiImplicitParam(name = "status", value = "状态"),
    })
    public R updateStaus(Long id, Byte status) {
        UserBank userBank = new UserBank();
        userBank.setId(id);
        userBank.setStatus(status);
        return userBankService.updateById(userBank) ? R.ok ("修改成功") : R.fail("修改失败");
    }

    @PatchMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userBank", value = "用户银行卡"),
    })
    public R update(@RequestBody UserBank userBank) {
        return userBankService.updateById(userBank) ? R.ok("修改成功") : R.fail("修改失败");
    }
}
