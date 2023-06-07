package com.biology.commons.config;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-11 14:42
 * @desc:
 */
@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfig {

    /**
     * @author: lichong
     * @description: 若运行出现找不到${server.port}问题，
     * 则要使用maven clean 后然后install再运行
     * @date: 2022/4/25
     */
    @Value("${server.port}")
    private String port;

    @Value("${jwt.header}")
    private String header;

    @Bean
    public Docket createSysApi() {
        log.info("构建swagger的Sys系统配置");
        return new Docket(DocumentationType.SWAGGER_2)
                // 指定构建api文档的详细信息的方法：apiInfo()
                .apiInfo(apiInfo())
                .groupName("系统-后端接口文档")
                .select()
                // 指定要生成api接口的包路径，这里把controller作为包路径，生成controller中的所有接口
                .apis(RequestHandlerSelectors.basePackage("com.biology.api.controller.sys"))
                .paths(PathSelectors.any())
                .build()
                ///* 设置安全模式，swagger可以设置访问token */
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());

    }

    @Bean
    public Docket createBusinessApi() {
        log.info("构建swagger的business业务配置");
        return new Docket(DocumentationType.SWAGGER_2)
                // 指定构建api文档的详细信息的方法：apiInfo()
                .apiInfo(apiInfo())
                .groupName("系统业务-后端接口文档")
                .select()
                // 指定要生成api接口的包路径，这里把controller作为包路径，生成controller中的所有接口
                .apis(RequestHandlerSelectors.basePackage("com.biology.api.controller.business"))
                .paths(PathSelectors.any())
                .build()
                /* 设置安全模式，swagger可以设置访问token */
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());

    }

    @Bean
    public Docket createCommonApi() {
        log.info("构建swagger的common通用配置");
        return new Docket(DocumentationType.SWAGGER_2)
                // 指定构建api文档的详细信息的方法：apiInfo()
                .apiInfo(apiInfo())
                .groupName("系统通用-后端接口文档")
                .select()
                // 指定要生成api接口的包路径，这里把controller作为包路径，生成controller中的所有接口
                .apis(RequestHandlerSelectors.basePackage("com.biology.api.controller.common")).paths(PathSelectors.any()).build();
    }

    /**
     * 构建api文档的详细信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 设置页面标题
                .title("系统管理后端接口文档")
                // 设置接口描述
                .description("系统管理后端接口文档")
                // 设置联系方式
                .contact(new Contact("lichong", "http://localhost:" + port + "/", "1207239331@qq.com"))
                // 设置版本
                .version("1.0")
                // 构建
                .build();
    }


    /**
     * 安全模式，这里指定token通过Authorization头请求头传递
     */
    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList = new ArrayList<SecurityScheme>();
        apiKeyList.add(new ApiKey(header, header, In.HEADER.toValue()));
        return apiKeyList;
    }

    /**
     * 安全上下文
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .operationSelector(o -> o.requestMappingPattern().matches("/.*"))
                        .build());
        return securityContexts;
    }

    /**
     * 默认的安全上引用
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference(header, authorizationScopes));
        return securityReferences;
    }


}
