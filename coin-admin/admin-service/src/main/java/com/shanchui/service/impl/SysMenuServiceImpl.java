package com.shanchui.service.impl;

import com.shanchui.domain.SysRoleMenu;
import com.shanchui.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanchui.mapper.SysMenuMapper;
import com.shanchui.domain.SysMenu;
import com.shanchui.service.SysMenuService;
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService{

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuMapper sysMenuMapper;
    /**
     * 通过用户id获取菜单
     * @param userId
     * @return
     */
    @Override
    public List<SysMenu> getMenusByUserId(Long userId) {
        // 1 如果用户是超级管理源 -> 拥有所有的菜单
        if (sysRoleService.isSuperAdmin(userId)){
            return list();
        }
        // 2 如果用户不是超级管理源 -> 根据用户id查询用户拥有的菜单
        return sysMenuMapper.getMenusByUserId(userId);
    }
}
