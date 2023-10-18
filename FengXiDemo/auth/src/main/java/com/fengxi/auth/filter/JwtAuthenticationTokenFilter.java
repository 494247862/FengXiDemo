package com.fengxi.auth.filter;

import com.alibaba.fastjson.JSONObject;
import com.fengxi.auth.dto.CommonSettingDTO;
import com.fengxi.auth.entity.DeyiUser;
import com.fengxi.auth.exception.BaseKnowException;
import com.fengxi.auth.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * jwt认证过滤器
 *
 * @author wujiuhe
 * @description: TODO
 * @title: JwtAuthenticationTokenFilter
 * @projectName demo
 * @date 2023/1/25 13:46:50
 */
@RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    //    @Resource
    private final CommonSettingDTO commonSettingDTO;

    //    @Autowired
    private final StringRedisTemplate stringRedisTemplate;

    //    @Autowired
//    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver resolver;

    @Override
    public void destroy() {

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) { // 没有token
            // 放行
//            filterChain.doFilter(request, response);
            resolver.resolveException(request, response, null, new BaseKnowException(403, "用户尚未登录"));
            return;
        }
        // 解析token
        String userId;
        try {
            Claims claims = JwtUtil.parseToken(token, commonSettingDTO.getTokenKey());
            userId = claims.get("userId").toString();
        } catch (Exception e) {
            e.printStackTrace();
            resolver.resolveException(request, response, null, new BaseKnowException(403, "token非法"));
            return;
        }
        // 从redis获取用户信息
        String redisKey = commonSettingDTO.getRedisUserKey() + ":" + userId;
        String redisValue = stringRedisTemplate.opsForValue().get(redisKey);
        DeyiUser deyiUser = JSONObject.parseObject(redisValue, DeyiUser.class);
        if (Objects.isNull(deyiUser)) {
            resolver.resolveException(request, response, null, new BaseKnowException(403, "用户尚未登录"));
            return;
        }

        // 开启单点登录
        if (commonSettingDTO.getTokenSingle()) {
            if (!deyiUser.getToken().equals(token)) {
                resolver.resolveException(request, response, null, new BaseKnowException(403, "token已过期"));
                return;
            }
        }

        // 存入SecurityContextHolder
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(deyiUser,
                null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // 放行
        filterChain.doFilter(request, response);
    }
}
