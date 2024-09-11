package com.yangjl.bigevent.controller;

import com.yangjl.bigevent.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(value = "/upload")
public class FileUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping
    public Result upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String extension = originalFilename.substring(index);
        String stem = originalFilename.substring(0, index);
        String filename = stem + '-' + UUID.randomUUID().toString().substring(0, 8) + extension;
        file.transferTo(new File(uploadDir + filename));
        return Result.success("文件存储成功："+ filename);
    }
}
