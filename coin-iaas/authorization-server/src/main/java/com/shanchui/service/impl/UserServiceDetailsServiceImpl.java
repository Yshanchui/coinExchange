package com.shanchui.service.impl;

import com.shanchui.constant.LoginConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String loginType = requestAttributes.getRequest().getParameter("login_type");//区分后台人员还是用户登录
        if (StringUtils.isEmpty(loginType)) {
            throw new AuthenticationServiceException("登录类型不能为null");
        }
        UserDetails userDetails = null;
        try {
            String grantType = requestAttributes.getRequest().getParameter("grant_type"); //refresh_token进行纠正
            if(LoginConstant.REFRESH_TYPE.equals(grantType.toUpperCase())){
            s = adjustLoginType(s,loginType);
            }
            switch (loginType){
                case LoginConstant.ADMIN_TYPE:
                    userDetails =  loadSysUserByUsername(s);
                    break;
                case LoginConstant.MEMBER_TYPE:
                    userDetails =  loadMemberUserByUsername(s);
                    break;
                default:
                    throw new AuthenticationServiceException("暂不支持的登录方式："+loginType);
            }
        }catch (IncorrectResultSizeDataAccessException e) { //用户名不存在
            throw new UsernameNotFoundException("用户名" + s + "不存在");
        }

        return userDetails;
    }

    /*纠正用户的名称*/
    private String adjustLoginType(String s, String loginType) {
        if(LoginConstant.ADMIN_TYPE.equals(loginType)){
            //管理员的纠正方式
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_USER_WITH_ID, String.class, s);
        }
        if(LoginConstant.MEMBER_TYPE.equals(loginType)){
            //会员的纠正方式
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_USER_WITH_ID, String.class, s);
        }
        return  s;
    }

    /*会员登录*/
    private UserDetails loadSysUserByUsername(String s) {
        //1 使用用户名查询用户信息
        return  jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_SQL, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                if (resultSet.wasNull()){
                    throw new UsernameNotFoundException("用户名"+ s +"不存在");
                }
                long id = resultSet.getLong("id");//用户的id
                String password = resultSet.getString("password");//用户的密码
                int status = resultSet.getInt("status");//用户的状态
                //3 封装成UserDetails对象返回;

                return new User(
                    String.valueOf(id),
                    password,
                    status==1,
                    true,
                    true,
                    true,
                    getSysUserPermissions(id)
                );
            }
        },s);
    }


    //2 查询这个用户对应的权限
    private Collection<? extends GrantedAuthority> getSysUserPermissions(long id) {
        //1 当用户为超级管理员时，他拥有所有的权限数据
        String roleCode = jdbcTemplate.queryForObject(LoginConstant.QUERY_ROLE_CODE_SQL, String.class, id);
        List<String> permissions = null; // 权限的名称
        if(LoginConstant.ADMIN_ROLE_CODE.equals(roleCode)){ //超级管理员
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_ALL_PERMISSIONS, String.class);
        }else{//2 普通用户，需要使用角色-》权限数据
            permissions =  jdbcTemplate.queryForList(LoginConstant.QUERY_PERMISSION_SQL, String.class, id);
        }
        if(permissions == null || permissions.isEmpty()){
            return Collections.emptyList();
        }
        return permissions.stream()
                .distinct() //去重
                .map(perm ->new SimpleGrantedAuthority(perm))
                .collect(Collectors.toSet());
    }

    /*后台人员登录*/
    private UserDetails loadMemberUserByUsername(String s) {
        return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_SQL, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                if (resultSet.wasNull()){
                    throw new UsernameNotFoundException("用户名"+ s +"不存在");
                }
                long id = resultSet.getLong("id");//会员的id
                String password = resultSet.getString("password");//会员的密码
                int status = resultSet.getInt("status");//会员的状态
                //3 封装成UserDetails对象返回;

                return new User(
                        String.valueOf(id),
                        password,
                        status==1,
                        true,
                        true,
                        true,
                        Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
                );
            }
        },s,s);
    }

}
