package com.shanchui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanchui.mapper.NoticeMapper;
import com.shanchui.domain.Notice;
import com.shanchui.service.NoticeService;
import org.springframework.util.StringUtils;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService{

    /**
     * 分页查询
     * @param page
     * @param title
     * @param startTime
     * @param endTime
     * @param status
     * @return
     */
    @Override
    public Page<Notice> findByPage(Page<Notice> page, String title, String startTime, String endTime, Integer status) {
        return page(page,new LambdaQueryWrapper<Notice>()
                .like(!StringUtils.isEmpty(title), Notice::getTitle, title)
                .between(
                        !StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime),
                        Notice::getCreated,
                        startTime, endTime + " 23:59:59")
                .eq(status != null, Notice::getStatus, status));
    }
}
