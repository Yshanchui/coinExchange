package com.shanchui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanchui.domain.SysUserRole;
import com.shanchui.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanchui.mapper.SysUserMapper;
import com.shanchui.domain.SysUser;
import com.shanchui.service.SysUserService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    @Autowired
    private SysUserRoleService sysUserRoleService;


    @Override
    public Page<SysUser> findByPage(Page<SysUser> page, String mobile, String fullname) {
        Page<SysUser> pageData = page(page, new LambdaQueryWrapper<SysUser>()
                .like(!StringUtils.isEmpty(mobile), SysUser::getMobile, mobile)
                .like(!StringUtils.isEmpty(fullname), SysUser::getFullname, fullname)
        );
        List<SysUser> records = pageData.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            for (SysUser record : records) {
                List<SysUserRole> userRoles = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, record.getId()));
                if (!CollectionUtils.isEmpty(userRoles)) {
                    record.setRole_strings(userRoles.stream()
                            .map(sysUserRole -> sysUserRole.getRoleId().toString())
                            .collect(Collectors.joining(",")));
                }
            }
        }
        return pageData;
    }

    @Override
    @Transactional
    public boolean addUser(SysUser sysUser) {
        String password = sysUser.getPassword();
        String roleStrings = sysUser.getRole_strings();
        sysUser.setPassword(new BCryptPasswordEncoder().encode(password));
        boolean save = super.save(sysUser);
        if(save){
            // 保存用户角色关系
            if(!StringUtils.isEmpty(roleStrings)){
                String[] roleIds = roleStrings.split(",");
                List<SysUserRole> sysUserRoleList = new ArrayList<>(roleIds.length);
                for(String roleId : roleIds){
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(sysUser.getId());
                    sysUserRole.setRoleId(Long.valueOf(roleId));
                    sysUserRoleList.add(sysUserRole);
                }
                sysUserRoleService.saveBatch(sysUserRoleList);
            }
        }
        return save;
    }

    /**
     * 更新员工信息
     * @param sysUser
     * @return
     */
    @Override
    @Transactional
    public boolean updateUser(SysUser sysUser) {
        String roleStrings = sysUser.getRole_strings();
        sysUser.setPassword(new BCryptPasswordEncoder().encode(sysUser.getPassword()));
        boolean b = super.updateById(sysUser);
        if(b){
            // 保存用户角色关系
            if(!StringUtils.isEmpty(roleStrings)){
                String[] roleIds = roleStrings.split(",");
                List<SysUserRole> sysUserRoleList = new ArrayList<>(roleIds.length);
                for(String roleId : roleIds){
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(sysUser.getId());
                    sysUserRole.setRoleId(Long.valueOf(roleId));
                    sysUserRoleList.add(sysUserRole);
                }
                sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId,sysUser.getId()));
                sysUserRoleService.saveBatch(sysUserRoleList);
            }
        }
        return b;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        boolean b = super.removeByIds(idList);
        sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId,idList));
        return b;
    }
}
