package com.shanchui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanchui.domain.WebConfig;
import com.shanchui.mapper.WebConfigMapper;
import com.shanchui.service.WebConfigService;
import org.springframework.util.StringUtils;

@Service
public class WebConfigServiceImpl extends ServiceImpl<WebConfigMapper, WebConfig> implements WebConfigService{

    @Override
    public Page<WebConfig> findByPage(Page<WebConfig> page, String name, String type) {
        return  page(page,new LambdaQueryWrapper<WebConfig>()
                .like(!StringUtils.isEmpty(name), WebConfig::getName, name)
                .like(!StringUtils.isEmpty(type), WebConfig::getType, type));

    }
}
