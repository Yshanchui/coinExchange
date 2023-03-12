package com.shanchui.controller;

import com.shanchui.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "会员测试")
public class MemberTestController {

    @GetMapping("/test")
    @ApiOperation(value = "测试",authorizations = {@Authorization("Authorization")})
    public R<String> test() {
        return R.ok("test");
    }
}
