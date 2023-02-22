package com.shanchui.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * web操作日志记录
 * elk
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WebLog {

    private String description;

    private String username;

    private Integer spendTime;

    private String basePath;

    private String uri;

    private String url;

    private String method;

    private String ip;

    private Object parameter;

    private Object result;

}