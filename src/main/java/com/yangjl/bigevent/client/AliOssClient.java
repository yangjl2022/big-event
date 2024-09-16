package com.yangjl.bigevent.client;

import com.aliyun.oss.*;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

@Slf4j
@Service
public class AliOssClient {
    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.access-key-id}")
    private String accessKeyId;

    @Value("${oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${oss.bucket-name}")
    private String bucketName;

    private OSS oss;

    @PostConstruct
    public void init() {
        oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    @PreDestroy
    public void destroy() {
        if (oss != null) {
            oss.shutdown();
        }
    }


    /**
     * 上传文件,返回文件的公网访问地址
     * @param objectName
     * @param inputStream
     * @return
     */
    public String uploadFile(String objectName, InputStream inputStream){
        //公文访问地址
        String url = "";
        try {
            // 创建存储空间。
            oss.createBucket(bucketName);
            oss.putObject(bucketName, objectName, inputStream);
            url = generatePresignedUrl(objectName, 12);
        } catch (OSSException oe) {
            log.info("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.info("Error Message: {}", oe.getErrorMessage());
            log.info("Error Code: {}" , oe.getErrorCode());
            log.info("Request ID: {}", oe.getRequestId());
            log.info("Host ID: {}", oe.getHostId());
        } catch (ClientException ce) {
            log.info("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.info("Error Message: {}", ce.getMessage());
        }
        return url;
    }

    /**
     * 产生带了访问文件权限的URL
     * @param objectName
     * @param expirationInHours
     * @return
     */
    public String generatePresignedUrl(String objectName, int expirationInHours) {
        // 设置 URL 过期时间
        Date expiration = new Date(new Date().getTime() + 1000L *60*60* expirationInHours);
        // 生成公开 URL
        URL url = oss.generatePresignedUrl(bucketName, objectName, expiration);
        return url.toString();
    }
}
