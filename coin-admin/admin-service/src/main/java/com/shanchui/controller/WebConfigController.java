package com.shanchui.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanchui.domain.WebConfig;
import com.shanchui.model.R;
import com.shanchui.service.WebConfigService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

@RestController
@Api(tags = "网站配置")
@RequestMapping("/webConfigs")
public class WebConfigController {

    @Autowired
    private WebConfigService webConfigService;

    @GetMapping
    @ApiOperation(value = "条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的大小"),
            @ApiImplicitParam(name = "name", value = "资源名称"),
            @ApiImplicitParam(name = "type", value = "资源类型"),
    })
    @PreAuthorize("hasAuthority('web_config_query')")
    public R<Page<WebConfig>> findByPage(@ApiIgnore Page<WebConfig> page, String name,String type) {
        page.addOrder(OrderItem.desc("created"));
        Page<WebConfig> Page = webConfigService.findByPage(page, name, type);
        return R.ok(Page);
    }

    @PostMapping
    @ApiOperation(value = "新增一个网站配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "webConfig", value = "webConfig的json数据")
    })
    @PreAuthorize("hasAuthority('web_config_create')")
    public R add(@RequestBody WebConfig webConfig) {
        return webConfigService.save(webConfig) ? R.ok("新增成功") : R.fail("新增失败");
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除一个网站配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "网站配置id数组")
    })
    @PreAuthorize("hasAuthority('web_config_delete')")
    public R delete(@RequestBody String[] ids) {
        return webConfigService.removeByIds(Arrays.asList(ids)) ? R.ok("删除成功") : R.fail("删除失败");
    }

    @PatchMapping
    @ApiOperation(value = "修改一个网站配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "webConfig", value = "webConfig的json数据")
    })
    @PreAuthorize("hasAuthority('web_config_update')")
    public R update(@RequestBody @Validated  WebConfig webConfig) {
        return webConfigService.updateById(webConfig) ? R.ok("修改成功") : R.fail("修改失败");
    }
}
