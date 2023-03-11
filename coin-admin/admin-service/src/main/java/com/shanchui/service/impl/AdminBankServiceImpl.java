package com.shanchui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanchui.mapper.AdminBankMapper;
import com.shanchui.domain.AdminBank;
import com.shanchui.service.AdminBankService;
import org.springframework.util.StringUtils;

@Service
public class AdminBankServiceImpl extends ServiceImpl<AdminBankMapper, AdminBank> implements AdminBankService{

    @Override
    public Page<AdminBank> findByPage(Page<AdminBank> page, String name) {
        return page(page, new LambdaQueryWrapper<AdminBank>()
                .like(!StringUtils.isEmpty(name), AdminBank::getName, name));
    }
}
