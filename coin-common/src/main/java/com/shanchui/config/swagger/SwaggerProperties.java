package com.shanchui.config.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "swagger2")
public class SwaggerProperties {

    /**
     * 包扫描的路径
     */
    private  String basePackage;

    /**
     * 联系人的名称
     */
    private String name;

    /**
     * 联系人的url
     */
    private String url;

    /**
     * 联系人的email
     */
    private String email;

    /**
     * api的标题
     */
    private String title;

    /**
     * api的描述
     */
    private String description;

    /**
     * api的版本
     */
    private String version;

    /**
     * api的服务团队
     */
    private String termsOfServiceUrl;
}
