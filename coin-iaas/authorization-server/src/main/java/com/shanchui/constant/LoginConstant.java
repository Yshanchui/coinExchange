package com.shanchui.constant;

/*
* 登录常量
* */
public class LoginConstant {

    /*后台管理人员*/
    public static final String ADMIN_TYPE= "admin_type";

    /*普通管理人员*/
    public static final String MEMBER_TYPE= "member_type";

    /*使用用户名查询用户*/
    public static final String QUERY_ADMIN_SQL = " SELECT `id`, `username`, `password`, `status` FROM `sys_user` WHERE `username` =?";

    /*查询用户的角色code*/
    public static final String QUERY_ROLE_CODE_SQL = " SELECT `code` FROM sys_role LEFT JOIN sys_user_role ON sys_role.id =sys_user_role.role_id WHERE sys_user_role.user_id  =?";

    /*查询所有的权限名称*/
    public static final String QUERY_ALL_PERMISSIONS = " SELECT `name` FROM sys_privilege";

    /*非管理员用户，我们需要先查询 role-permissionid-permission*/
    public static final String QUERY_PERMISSION_SQL = "SELECT `name` FROM sys_privilege LEFT JOIN sys_role_privilege ON sys_role_privilege.privilege_id = sys_privilege.id LEFT JOIN sys_user_role ON sys_role_privilege.role_id = sys_user_role.role_id WHERE sys_user_role.user_id =?";

    public static final String ADMIN_ROLE_CODE = "ROLE_ADMIN";

    /*会员查询sql*/
    public static final String QUERY_MEMBER_SQL = "SELECT `id`, `password`, `status` FROM `user` WHERE mobile =? or email =?";

    public static final String REFRESH_TYPE = "REFRESH_TOKEN";

    /*使用后台用户的id 查询用户名称*/
    public static final String QUERY_ADMIN_USER_WITH_ID = "SELECT `username` FROM `sys_user` WHERE `id` =?";

    /*使用用户的id 查询用户的名称*/
    public static final String QUERY_MEMBER_USER_WITH_ID = "SELECT `mobile` FROM `user` WHERE `id` =?";

}
