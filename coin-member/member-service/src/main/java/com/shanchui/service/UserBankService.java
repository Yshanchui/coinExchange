package com.shanchui.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanchui.domain.UserBank;
import com.baomidou.mybatisplus.extension.service.IService;
public interface UserBankService extends IService<UserBank>{


    Page<UserBank> findByPage(Page<UserBank> page, Long usrId);
}
