package com.shanchui.model;

import com.shanchui.domain.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "登录的结果")
public class LoginResult {

    /**
     * 登录成功的token 来自我们的authorization-server里面的
     */
    @ApiModelProperty(value = "登录成功的token")
    private String token;

    /**
     * 该用户的菜单数据
     */
    @ApiModelProperty(value = "该用户的菜单数据")
    private List<SysMenu> menus;

    /**
     * 该用户的权限数据
     */
    @ApiModelProperty(value = "该用户的权限数据")
    private List<SimpleGrantedAuthority> authorities;

}