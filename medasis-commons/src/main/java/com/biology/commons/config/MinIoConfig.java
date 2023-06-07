package com.biology.commons.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-06 18:04
 * @desc:
 */
@Data
@Configuration
public class MinIoConfig {

    /**
     * Minio 服务端ip
     */
    @Value("${min.io.endpoint}")
    private String endpoint;

    @Value("${min.io.accessKey}")
    private String accessKey;

    @Value("${min.io.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

}
