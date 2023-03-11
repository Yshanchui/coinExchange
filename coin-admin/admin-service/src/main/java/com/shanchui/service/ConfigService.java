package com.shanchui.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanchui.domain.Config;
import com.baomidou.mybatisplus.extension.service.IService;
public interface ConfigService extends IService<Config>{


    Page<Config> findByPage(Page<Config> page, String name, String type, String code);
}
