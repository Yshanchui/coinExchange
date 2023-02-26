package com.shanchui.service;

import com.shanchui.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysMenuService extends IService<SysMenu>{


    /**
     * 通过用户id获取菜单
     * @param userId
     * @return
     */
    List<SysMenu> getMenusByUserId(Long userId);
}
