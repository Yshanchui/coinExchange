package com.shanchui.controller;


import cn.hutool.core.date.DateUtil;
import com.aliyun.oss.OSS;
import com.shanchui.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 完成文件上传的功能
 */
@RestController
@Api(tags = "文件上传的控制器")
public class FileController {

    @Autowired
    private OSS ossClient; //注入OSS客户端

    @Value("${oss.bucket.name:ehomedump}")
    private String bucketName;

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endPoint;
    @PostMapping("/image/AliYunImgUpload")
    @ApiOperation(value = "文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件"),
    })
    public R<String> fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        //
        String fileName = DateUtil.today().replaceAll("-", "/") + "/" + file.getOriginalFilename();
        ossClient.putObject(bucketName, fileName, file.getInputStream());
        return R.ok("https://"+bucketName+"."+endPoint+"/"+fileName);
    }
}
