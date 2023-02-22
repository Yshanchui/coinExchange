package com.shanchui.controller;

import com.shanchui.model.R;
import com.shanchui.model.WebLog;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@Api(tags = "测试接口")
public class TestController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/common/ test")
    @ApiOperation(value = "测试方法",authorizations = {@Authorization("Authorization")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "参数1",  dataType = "String", paramType = "query",example = "paramValue"),
            @ApiImplicitParam(name = "param1", value = "参数2", dataType = "String", paramType = "query",example = "paramValue1")
    })
    public R<String> testMethod(String param, String param1) {
        return R.ok("ok");
    }

    @GetMapping("/common/testData")
    @ApiOperation(value = "日期格式化测试",authorizations = {@Authorization("Authorization")})
    public R<Date> testData() {
        return R.ok(new Date());
    }

    @GetMapping("/common/testredis")
    @ApiOperation(value = "redis格式化测试",authorizations = {@Authorization("Authorization")})
    public R<String> testRedis() {
        WebLog webLog = new WebLog();
        webLog.setResult("ok");
        webLog.setMethod("com.shanchui.domain.WebLog.testRedis");
        webLog.setUsername("1110");
        redisTemplate.opsForValue().set("com.shanchui.domain.WebLog", webLog);
        return R.ok("ok");
    }
}
