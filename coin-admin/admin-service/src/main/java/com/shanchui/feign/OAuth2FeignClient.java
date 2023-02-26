package com.shanchui.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "authorization-server")
public interface OAuth2FeignClient {

    /**
     *
     * @param grantType
     * @param username
     * @param password
     * @param longType
     * @param basicToken
     * @return
     */
    @PostMapping(value = "/oauth/token")
    JwtToken getToken(@RequestParam("grant_type") String grantType,
                                      @RequestParam("username") String username,
                                      @RequestParam("password") String password,
                                      @RequestParam("login_type") String longType,
                                      @RequestHeader("Authorization") String basicToken // 基础认证的token 由第三方客户端加密出来的值
    );
}
