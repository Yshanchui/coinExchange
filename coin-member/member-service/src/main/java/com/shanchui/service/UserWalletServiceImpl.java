package com.shanchui.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanchui.domain.UserWallet;
import com.shanchui.mapper.UserWalletMapper;
import com.shanchui.service.UserWalletService;
@Service
public class UserWalletServiceImpl extends ServiceImpl<UserWalletMapper, UserWallet> implements UserWalletService{

    @Override
    public Page<UserWallet> findByPage(Page<UserWallet> page, Long userId) {
        return page(page,new LambdaQueryWrapper<UserWallet>()
                .eq(UserWallet::getUserId, userId));
    }
}
