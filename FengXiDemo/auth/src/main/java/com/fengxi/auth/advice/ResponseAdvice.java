package com.fengxi.auth.advice;

import com.fengxi.auth.annotation.NotUseResultVO;
import com.fengxi.auth.vo.ResultVO;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一返回VO advice
 *
 * @author wujiuhe
 * @description: TODO
 * @title: ResponseAdvice
 * @projectName demo
 * @date 2023/1/8 17:46:55
 */
@RestControllerAdvice(basePackages = {"com.fengxi"})
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 是否开启功能 true：是
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        NotUseResultVO declaredAnnotation = returnType.getMethod().getDeclaredAnnotation(NotUseResultVO.class);
        if (declaredAnnotation != null && !declaredAnnotation.isUse()) {
            return false;
        }
        return true;
    }

    /**
     * 处理返回结果
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        //处理字符串类型数据
        if (body instanceof String) {
            return ResultVO.success(body);
        }
        //返回类型是否已经封装
        if (body instanceof ResultVO) {
            return body;
        }
        return ResultVO.success(body);
    }
}

