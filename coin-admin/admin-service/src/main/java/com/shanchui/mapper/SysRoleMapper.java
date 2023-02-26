package com.shanchui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanchui.domain.SysRole;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 判断用户是否是超级管理员
     * @param userId
     * @return
     */
    String getUserRoleCode(@Param("userId") Long userId);

}