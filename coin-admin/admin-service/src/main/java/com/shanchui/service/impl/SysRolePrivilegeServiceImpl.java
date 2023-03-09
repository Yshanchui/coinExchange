package com.shanchui.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shanchui.domain.SysMenu;
import com.shanchui.domain.SysPrivilege;
import com.shanchui.model.RolePrivilegesParam;
import com.shanchui.service.SysMenuService;
import com.shanchui.service.SysPrivilegeService;
import com.shanchui.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanchui.mapper.SysRolePrivilegeMapper;
import com.shanchui.domain.SysRolePrivilege;
import com.shanchui.service.SysRolePrivilegeService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysRolePrivilegeServiceImpl extends ServiceImpl<SysRolePrivilegeMapper, SysRolePrivilege> implements SysRolePrivilegeService{

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysPrivilegeService sysPrivilegeService;

    @Autowired
    private SysRolePrivilegeService sysRolePrivilegeService;

    @Override
    public List<SysMenu> findSysMenuAndPrivilege(Long roleId) {
        List<SysMenu> list = sysMenuService.list(); //查询所有菜单
        // 页面显示的是二级菜单，以及菜单包含的权限
        if(CollectionUtil.isEmpty(list)){
            return Collections.emptyList();
        }
        List<SysMenu> rootMenus = list.stream().filter(sysMenu -> sysMenu.getParentId() == null).collect(Collectors.toList());
        if(CollectionUtil.isEmpty(rootMenus)){
            return Collections.emptyList();
        }
        List<SysMenu> subMenus = new ArrayList<>();
        for(SysMenu rootMenu : rootMenus) {
            subMenus.addAll(getChildMenus(rootMenu.getId(),roleId,list));
        }
        return subMenus;
    }

    /**
     * 授权
     * @param rolePrivilegesParame
     * @return
     */
    @Transactional
    @Override
    public boolean grantPrivileges(RolePrivilegesParam rolePrivilegesParame) {
        Long roleId = rolePrivilegesParame.getRoleId(); // 角色id
        //1 删除当前角色的所有权限
        sysRolePrivilegeService.remove(new LambdaQueryWrapper<SysRolePrivilege>().eq(SysRolePrivilege::getRoleId,roleId));
        List<Long> privilegeIds = rolePrivilegesParame.getPrivilegeIds();
        if(!CollectionUtil.isEmpty(privilegeIds)){
            List<SysRolePrivilege> sysRolePrivileges = new ArrayList<>();
            for (Long privilegeId : privilegeIds) {
                SysRolePrivilege sysRolePrivilege = new SysRolePrivilege();
                sysRolePrivilege.setRoleId(roleId);
                sysRolePrivilege.setPrivilegeId(privilegeId);
                sysRolePrivileges.add(sysRolePrivilege);
            }
            //2 重新授权
            boolean b = sysRolePrivilegeService.saveBatch(sysRolePrivileges);
            return b;
        }
        return true;
    }

    /**
     * 递归查询子菜单
     * @param parentId
     * @param roleId
     * @return
     */
    private List<SysMenu> getChildMenus(Long parentId, Long roleId,List<SysMenu> sources) {
        List<SysMenu> childs = new ArrayList<>();
        for (SysMenu source : sources){
            if(source.getParentId() == parentId){
                childs.add(source);
                source.setChilds(getChildMenus(source.getId(),roleId,sources));
                List<SysPrivilege> sysPrivileges = sysPrivilegeService.getAllSysPrivilege(source.getId(), roleId);
                source.setPrivileges(sysPrivileges);
            }
        }
        return childs;
    }
}
