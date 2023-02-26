package com.shanchui.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanchui.mapper.SysUserMapper;
import com.shanchui.domain.SysUser;
import com.shanchui.service.SysUserService;
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

}
