package com.shanchui.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanchui.mapper.SysPrivilegeMapper;
import com.shanchui.domain.SysPrivilege;
import com.shanchui.service.SysPrivilegeService;
@Service
public class SysPrivilegeServiceImpl extends ServiceImpl<SysPrivilegeMapper, SysPrivilege> implements SysPrivilegeService{

    @Autowired
    private SysPrivilegeMapper sysPrivilegeMapper;
    /**
     * 查询所有权限
     *
     * @param menuId
     * @param roleId
     * @return
     */
    @Override
    public List<SysPrivilege> getAllSysPrivilege(Long menuId, Long roleId) {
        List<SysPrivilege> sysPrivileges = list(new LambdaQueryWrapper<SysPrivilege>().eq(SysPrivilege::getMenuId, menuId));
        if(CollectionUtil.isEmpty(sysPrivileges)){
            return Collections.emptyList();
        }
        // 2 当前传递的角色使用包含该权限信息也要放进去
        for (SysPrivilege sysPrivilege: sysPrivileges) {
            Set<Long> currentRoleSysPrivilegeIds = sysPrivilegeMapper.getPrivilegesByRoleId(roleId);
            if (currentRoleSysPrivilegeIds.contains(sysPrivilege.getId())) {
                sysPrivilege.setOwn(1);
            }
        }
        return sysPrivileges;
    }
}
