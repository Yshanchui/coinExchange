package com.shanchui.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanchui.domain.SysPrivilege;
import com.shanchui.model.R;
import com.shanchui.service.SysPrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

/**
 * 权限的控制器
 *
 */
@RestController
@RequestMapping("/privileges")
@Api(tags = "权限的控制器")
public class SysPrivilegeController {

    @Autowired
    private SysPrivilegeService sysPrivilegeService;
    /**
     * 权限数据的分页查询
     * @return
     */
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数")
    })
    @PreAuthorize("hasAuthority('sys_privilege_query')")
    public R<Page<SysPrivilege>> findByPage(@ApiIgnore Page<SysPrivilege> page) {

        // 查询时，我们要将最近新增的、修改的数据优先展示->排序->last_update_time
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<SysPrivilege> sysPrivilegePage = sysPrivilegeService.page(page);
        return R.ok(sysPrivilegePage);

    }

    @PostMapping
    @ApiOperation(value = "权限的新增")
    @PreAuthorize("hasAuthority('sys_privilege_create')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPrivilege", value = "权限的实体类")
    })
    public R  add(@RequestBody @Validated SysPrivilege sysPrivilege) {

        //新增时，我们需要给我们的新增对象填充一些属性
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        sysPrivilege.setCreateBy(Long.valueOf(userIdStr));
        sysPrivilege.setCreated(new Date());
        sysPrivilege.setLastUpdateTime(new Date());
        boolean save = sysPrivilegeService.save(sysPrivilege);
        if(save){
            return R.ok("权限新增成功");
        }else {
            return R.fail("权限新增失败");
        }
    }
}
