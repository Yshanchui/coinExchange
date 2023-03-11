package com.shanchui.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanchui.domain.Config;
import com.shanchui.model.R;
import com.shanchui.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/configs")
@Api(tags = "参数管理")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping
    @ApiOperation(value = "条件分页查询参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数"),
            @ApiImplicitParam(name = "name", value = "参数名称"),
            @ApiImplicitParam(name = "type", value = "参数类型"),
            @ApiImplicitParam(name = "code", value = "参数编码"),
    })
    @PreAuthorize("hasAuthority('config_query')")
    public R<Page<Config>> findByPage(@ApiIgnore Page<Config> page, String name, String type, String code) {
        return R.ok(configService.findByPage(page, name, type, code));
    }
}
