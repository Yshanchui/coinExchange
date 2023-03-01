package com.shanchui.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanchui.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SysRoleService extends IService<SysRole>{


    /**
     * 判断用户是否是超级管理员
     * @param userId
     * @return
     */
    boolean isSuperAdmin(Long userId);

    Page<SysRole> findByPage(Page<SysRole> page, String name);
}
