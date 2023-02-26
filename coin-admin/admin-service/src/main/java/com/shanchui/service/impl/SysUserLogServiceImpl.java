package com.shanchui.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanchui.mapper.SysUserLogMapper;
import com.shanchui.domain.SysUserLog;
import com.shanchui.service.SysUserLogService;
@Service
public class SysUserLogServiceImpl extends ServiceImpl<SysUserLogMapper, SysUserLog> implements SysUserLogService{

}
