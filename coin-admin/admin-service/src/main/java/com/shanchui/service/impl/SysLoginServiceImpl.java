package com.shanchui.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.esotericsoftware.minlog.Log;
import com.shanchui.domain.SysMenu;
import com.shanchui.feign.JwtToken;
import com.shanchui.feign.OAuth2FeignClient;
import com.shanchui.model.LoginResult;
import com.shanchui.service.SysLoginService;
import com.shanchui.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysLoginServiceImpl implements SysLoginService {

    @Autowired
    private OAuth2FeignClient oAuth2FeignClient;

    @Autowired
    private SysMenuService sysMenuService;

    @Value("${basic.token:Basic Y29pbi1hcGk6Y29pbi1zZWNyZXQ=}")
    private String basicToken;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 登录的实现
     * @param username
     * 用户命
     * @param password
     * 密码
     * @return
     */
    @Override
    public LoginResult login(String username, String password) {
        Log.info("用户{}开始登录",username);
        // 1 获取token 需要远程调用authorization-server 服务
        JwtToken jwtToken = oAuth2FeignClient.getToken("password", username, password, "admin_type", basicToken);
        Log.info("远程调用授权服务器成功，获取的token为{}", JSON.toJSONString(jwtToken, true));
        String token = jwtToken.getAccessToken();
        // 2 查询菜单数据
        Jwt jwt = JwtHelper.decode(token);
        String jwtJsonStr = jwt.getClaims();
        JSONObject jwtJson = JSON.parseObject(jwtJsonStr);
        Long userId = Long.valueOf(jwtJson.getString("user_name"));
        List<SysMenu> menu=  sysMenuService.getMenusByUserId(userId);
        // 3 查询权限数据  不需要查询的，因为jwt中已经包含了权限数据
        JSONArray authirzationJsonArray = jwtJson.getJSONArray("authorities"); //组装权限数据
        List<SimpleGrantedAuthority> authorities = authirzationJsonArray.stream()
                .map(authorityJson -> new SimpleGrantedAuthority(authorityJson.toString()))
                .collect(Collectors.toList());
        // 1 将该token存入redis中，配置我们的网关做jwt的校验
        stringRedisTemplate.opsForValue().set(token,"",jwtToken.getExpiresIn(),TimeUnit.SECONDS);
        // 2 我们返回给前端的token数据 少一个bearer
        return new LoginResult(jwtToken.getTokenType()+" "+token,menu,authorities);
    }
}
