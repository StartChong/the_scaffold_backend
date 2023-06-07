package com.biology.threeparty.config;

import com.biology.commons.constant.Constants;
import com.biology.service.sys.SecurityUserService;
import com.biology.threeparty.filter.CustomizeAccessDecisionManager;
import com.biology.threeparty.filter.CustomizeFilterInvocationSecurityMetadataSource;
import com.biology.threeparty.filter.JwtAuthenticationTokenFilter;
import com.biology.threeparty.security.AuthenticationEntryPointHandler;
import com.biology.threeparty.security.CustomizeAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.CorsFilter;


/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-26 14:46
 * @desc: SpringSecurity的配置类
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private AuthenticationEntryPointHandler authenticationEntryPoint;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    // 过滤器-判断JWT是否有效
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private CustomizeAccessDecisionManager customizeAccessDecisionManager;

    @Autowired
    private CustomizeFilterInvocationSecurityMetadataSource customizeFilterInvocationSecurityMetadataSource;

    @Autowired
    private CustomizeAccessDeniedHandler customizeAccessDeniedHandler;



    /*
     * 白名单
     */
    @Value("${jwt.antMatchers}")
    private String antMatchers;

    @Autowired
    private CorsFilter corsFilter;

    /**
     * 解决 无法直接注入 AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 对请求进行鉴权的配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests().
                withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customizeAccessDecisionManager); // 访问决策管理器
                        o.setSecurityMetadataSource(customizeFilterInvocationSecurityMetadataSource); // 安全元数据源
                        return o;
                    }
                });
        log.info("白名单：" + antMatchers);
        http.authorizeRequests() // 权限配置
                // 对于登录login 注册register  允许匿名访问
                .antMatchers("/api/sys/login", "/api/sys/register").permitAll()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/profile/**",
                        "/doc.html",
                        "/v2/api-docs",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/*/api-docs",
                        "/druid/**"
                ).permitAll() // （不进行权限验证）
                .antMatchers(antMatchers.split(Constants.COMMA)).anonymous() // 获取白名单
                .antMatchers("/doc.html").anonymous()
                .antMatchers("/v2/api-docs").anonymous()
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                .antMatchers("/druid/**").anonymous()
                .anyRequest().authenticated() // 其他的需要登陆后才能访问
                .and()
                .headers().frameOptions().disable()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(customizeAccessDeniedHandler)
                .and()
                .formLogin()
                .permitAll()  //允许所有用户
                .and()
                .logout()
                .logoutUrl("/api/sys/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID");  //登出成功处理逻辑
        // 添加JWT filter
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加CORS filter
        http.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
        http.addFilterBefore(corsFilter, LogoutFilter.class);
        http.headers().cacheControl();
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService).passwordEncoder(passwordEncoder());
    }

    /**
     * 默认开启密码加密，前端传入的密码Security会在加密后和数据库中的密文进行比对，一致的话就登录成功
     * 所以必须提供一个加密对象，供security加密前端明文密码使用
     *
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

