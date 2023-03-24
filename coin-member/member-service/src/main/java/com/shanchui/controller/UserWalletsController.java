package com.shanchui.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanchui.domain.UserWallet;
import com.shanchui.model.R;
import com.shanchui.service.UserWalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "用户的提币地址")
@RequestMapping("/userWallets")
public class UserWalletsController {

    @Autowired
    private UserWalletService userWalletService;

    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "usrId", value = "用户ID")
    })
    @PreAuthorize("hasAuthority('user_wallet_query')")
    public R<Page<UserWallet>> findByPage(Page<UserWallet> page, Long userId) {
        page.addOrder(OrderItem.desc("last_update_time"));
        return R.ok(userWalletService.findByPage(page, userId));
    }
}
