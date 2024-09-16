package com.yangjl.bigevent.controller;

import com.yangjl.bigevent.entity.Result;
import com.yangjl.bigevent.client.AliOssClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(value = "/upload")
@Slf4j
public class FileUploadController {
    @Autowired
    AliOssClient aliOssClient;

    @PostMapping
    public Result upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String extension = originalFilename.substring(index);
        String stem = originalFilename.substring(0, index);
        String filename = stem + '-' + UUID.randomUUID().toString().substring(0, 8) + extension;
        String url = aliOssClient.uploadFile(filename, file.getInputStream());
        log.info("文件 {} 上传成功：{}", filename, url);
        return Result.success(url);
    }
}
