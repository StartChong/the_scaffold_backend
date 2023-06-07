package com.biology.commons.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-05 15:50
 * @desc:
 */
@Data
@Component
@ConfigurationProperties(prefix = "white-list.path") // 配置文件的前缀
public class WhiteListConfig {

    /*
     * 需要拦截的路径
     */
    private List<String> include;

}
