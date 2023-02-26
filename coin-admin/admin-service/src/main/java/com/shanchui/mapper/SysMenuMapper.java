package com.shanchui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanchui.domain.SysMenu;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 通过用户id获取菜单
     * @param userId
     * @return
     */
    List<SysMenu> getMenusByUserId(Long userId);
}