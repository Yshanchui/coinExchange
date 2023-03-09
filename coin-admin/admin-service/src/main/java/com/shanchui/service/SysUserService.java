package com.shanchui.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanchui.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SysUserService extends IService<SysUser>{


    /**
     * 使用手机号和姓名进行分页查询
     * @param page
     * @param mobile
     * @param fullname
     * @return
     */
    Page<SysUser> findByPage(Page<SysUser> page, String mobile, String fullname);

    /**
     * 添加用户
     * @param sysUser
     * @return
     */
    boolean addUser(SysUser sysUser);

    boolean updateUser(SysUser sysUser);
}
