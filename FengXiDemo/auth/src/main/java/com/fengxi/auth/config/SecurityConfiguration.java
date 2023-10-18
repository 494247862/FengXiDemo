package com.fengxi.auth.config;

import com.fengxi.auth.dto.CommonSettingDTO;
import com.fengxi.auth.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.annotation.Resource;

/**
 * Security配置
 *
 * @author wujiuhe
 * @description: TODO
 * @title: SecurityConfig
 * @projectName demo
 * @date 2023/1/21 23:51:22
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Resource
    CommonSettingDTO commonSettingDTO;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    /**
     * 用于加密和校验
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                // 不通过session获取securitycontext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许访问
                .antMatchers("/user/login").anonymous()
                // 除上面的全部需要认证
                .anyRequest().authenticated();
        // 配置过滤器
        http.addFilterBefore(new JwtAuthenticationTokenFilter(commonSettingDTO, stringRedisTemplate, resolver), UsernamePasswordAuthenticationFilter.class);

        // 允许跨域
        http.cors();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        String[] replace = commonSettingDTO.getNotAuthUrls().replace(" ", "").split(",");
        return (web) -> web.ignoring()
                .antMatchers(replace);
//        return (web) -> web.ignoring()
//                .antMatchers(
//                        "/swagger-ui.html",
//                        "/v2/api-docs", // swagger api json
//                        "/swagger-resources/configuration/ui", // 用来获取支持的动作
//                        "/swagger-resources", // 用来获取api-docs的URI
//                        "/swagger-resources/configuration/security", // 安全选项
//                        "/swagger-resources/**",
//                        //补充路径，近期在搭建swagger接口文档时，通过浏览器控制台发现该/webjars路径下的文件被拦截，故加上此过滤条件即可。
//                        "/webjars/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
