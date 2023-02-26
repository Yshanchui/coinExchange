package com.shanchui.service;

import com.shanchui.model.LoginResult;

public interface SysLoginService {

    /**
     * 登录的实现
     * @param username
     * 用户命
     * @param password
     * 密码
     * @return
     */
    LoginResult login(String username, String password);
}
