package com.biology.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-24 14:12
 * @desc: 启动类
 */

@SpringBootApplication(scanBasePackages = {"com.biology"})
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
