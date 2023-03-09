package com.shanchui.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanchui.domain.Notice;
import com.shanchui.model.R;
import com.shanchui.service.NoticeService;
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
@Api(tags = "公告管理")
@RequestMapping("/notices")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping
    @ApiOperation(value = "条件分页查询公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数"),
    })
    @PreAuthorize("hasAuthority('notice_query')")
    public R<Page<Notice>> findByPage(@ApiIgnore Page<Notice> page,
                                      String title,
                                      String startTime,
                                      String endTime,
                                      Integer status) {
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<Notice> noticePage = noticeService.findByPage(page, title, startTime, endTime, status);
        return R.ok(noticePage);
    }

    @PostMapping
    @ApiOperation(value = "新增公告")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Notice", value = "公告对象"),
    })
    @PreAuthorize("hasAuthority('notice_create')")
    public  R addNotice(@RequestBody Notice notice) {
        notice.setStatus(1);
        return  noticeService.save(notice) ? R.ok() : R.fail("新增失败");

    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除公告")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "ids", value = "公告id集合"),
    })
    @PreAuthorize("hasAuthority('notice_delete')")
    public  R deleteNotice(@RequestBody String[] ids) {
        return  noticeService.removeByIds(Arrays.asList(ids)) ? R.ok() : R.fail("删除失败");
    }

    @PostMapping("/updateStatus")
    @ApiOperation(value = "修改公告状态")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "公告id"),
        @ApiImplicitParam(name = "status", value = "公告状态"),
    })
    @PreAuthorize("hasAuthority('notice_update')")
    public R updateStatus(@RequestBody  Long id , Integer status) {
        Notice notice = new Notice();
        notice.setId(id);
        notice.setStatus(status);
        return  noticeService.updateById(notice) ? R.ok() : R.fail("修改失败");
    }

    @PatchMapping
    @ApiOperation(value = "修改公告")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Notice", value = "公告对象"),
    })
    @PreAuthorize("hasAuthority('notice_update')")
    public R updateNotice(@RequestBody Notice notice) {
        return  noticeService.updateById(notice) ? R.ok() : R.fail("更新失败");
    }
}
