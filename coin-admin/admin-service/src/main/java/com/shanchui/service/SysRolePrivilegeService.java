package com.shanchui.service;

import com.shanchui.domain.SysMenu;
import com.shanchui.domain.SysRolePrivilege;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanchui.model.RolePrivilegesParam;

import java.util.List;

public interface SysRolePrivilegeService extends IService<SysRolePrivilege>{


    List<SysMenu> findSysMenuAndPrivilege(Long roleId);

    boolean grantPrivileges(RolePrivilegesParam rolePrivilegesParame);
}
