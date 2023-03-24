package com.shanchui.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanchui.domain.UserAddress;
import com.shanchui.model.R;
import com.shanchui.service.UserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "用户地址管理")
@RequestMapping("/userAddress")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "usrId", value = "用户ID")
            }
    )
    public R<Page<UserAddress>> findByPage(Page<UserAddress> page, Long userId) {
        page.addOrder(OrderItem.desc("last_update_time"));
        return R.ok(userAddressService.findByPage(page, userId));
    }


}
