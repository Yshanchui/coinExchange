package com.shanchui.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanchui.domain.SysRole;
import com.shanchui.mapper.SysRoleMapper;
import com.shanchui.service.SysRoleService;
import org.springframework.util.StringUtils;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     *
     * @param userId
     * @return
     */
    @Override
    public boolean isSuperAdmin(Long userId) {
        //当用户角色code为 ROLE_ADMIN 时，为超级管理员
        // 用户的id->用户的角色->该角色的code是否为 ROLE_ADMIN
        String roleCode = sysRoleMapper.getUserRoleCode(userId);
        return !StringUtils.isEmpty(roleCode) && roleCode.equals("ROLE_ADMIN");
    }
}
