package com.shanchui.service;

import com.shanchui.domain.SysPrivilege;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysPrivilegeService extends IService<SysPrivilege>{


    List<SysPrivilege> getAllSysPrivilege(Long menuId, Long roleId);
}
