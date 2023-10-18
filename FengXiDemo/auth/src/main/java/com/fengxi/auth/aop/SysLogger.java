package com.fengxi.auth.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fengxi.auth.dao.LogMapper;
import com.fengxi.auth.entity.DeyiLog;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Aspect
@Component
@Log4j2
public class SysLogger {
    @Autowired
    LogMapper logMapper;

    @Pointcut(value = "execution(public * com.fengxi.auth.controller.*.*(..))")
    public void controllerMethod() {
    }

//    @Before("controllerMethod()")
//    public void logBefore(JoinPoint joinPoint) {
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        StringBuilder requestLog = new StringBuilder();
//        Signature signature = joinPoint.getSignature();
//        Method method = ((MethodSignature) signature).getMethod();
//        // 打印请求内容
//        log.info("===============请求内容开始===============");
//        log.info("请求地址:" + request.getRequestURL().toString());
//        log.info("请求IP:" + request.getRemoteAddr());
//        log.info("请求类方法:" + joinPoint.getSignature());
//        log.info("请求类方法参数值:" + Arrays.toString(joinPoint.getArgs()));
//        // 处理请求参数
//        String[] paramNames = ((MethodSignature) signature).getParameterNames();
//        Object[] paramValues = joinPoint.getArgs();
//        int paramLength = null == paramNames ? 0 : paramNames.length;
//        if (paramLength == 0) {
//            requestLog.append("请求参数 = {} ");
//        } else {
//            requestLog.append("请求参数 = [");
//            for (int i = 0; i < paramLength - 1; i++) {
//                requestLog.append(paramNames[i]).append("=").append(JSONObject.toJSONString(paramValues[i])).append(",");
//            }
//            requestLog.append(paramNames[paramLength - 1]).append("=").append(JSONObject.toJSONString(paramValues[paramLength - 1])).append("]");
//        }
//        log.info("请求参数明细:" + requestLog.toString());
//        log.info("===============请求内容结束===============");
//    }

    @AfterReturning(returning = "o", pointcut = "controllerMethod()")
    public void logResultVOInfo(JoinPoint joinPoint, Object o) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        StringBuilder requestLog = new StringBuilder();
        Signature signature = joinPoint.getSignature();
        Method method = ((MethodSignature) signature).getMethod();
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);// 获取swagger的注解数据
        // 打印请求内容
        log.info("===============请求内容开始===============");
        log.info("请求地址:" + request.getRequestURL().toString());
        log.info("请求IP:" + request.getRemoteAddr());
        log.info("请求类方法:" + joinPoint.getSignature());
        // 处理请求参数
        String[] paramNames = ((MethodSignature) signature).getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        String requestParam = "";
        int paramLength = null == paramNames ? 0 : paramNames.length;
        if (paramLength == 0) {
            requestParam = "";
        } else {
            requestParam = JSON.toJSONString(paramValues, SerializerFeature.IgnoreErrorGetter);
        }
        log.info("请求参数明细:" + requestParam);
        log.info("===============请求内容结束===============");
        log.info("--------------返回内容开始----------------");
        log.info("Response内容:" + JSON.toJSONString(o));
        log.info("--------------返回内容结束----------------");

        DeyiLog deyiLog = new DeyiLog();
        deyiLog.setIsSuccess(true);
        deyiLog.setRequestIp(request.getRemoteAddr());
        deyiLog.setRequestParam(requestParam);
        deyiLog.setRequestResponse(JSON.toJSONString(o));
        deyiLog.setUrl(request.getRequestURL().toString());
        if (!Objects.isNull(annotation)) {
            deyiLog.setMethodName(annotation.value());
        }
        if (deyiLog.getRequestResponse().length() > 512) {
            deyiLog.setRequestResponse(null);
        }
        logMapper.insert(deyiLog);
    }

    @AfterThrowing(pointcut = "controllerMethod()", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Exception ex) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        StringBuilder requestLog = new StringBuilder();
        Signature signature = joinPoint.getSignature();
        Method method = ((MethodSignature) signature).getMethod();
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);// 获取swagger的注解数据
        // 打印请求内容
        log.info("===============请求内容开始===============");
        log.info("请求地址:" + request.getRequestURL().toString());
        log.info("请求IP:" + request.getRemoteAddr());
        log.info("请求类方法:" + joinPoint.getSignature());
        // 处理请求参数
        String[] paramNames = ((MethodSignature) signature).getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        String requestParam = "";
        int paramLength = null == paramNames ? 0 : paramNames.length;
        if (paramLength == 0) {
            requestParam = "";
        } else {
            requestParam = JSON.toJSONString(paramValues, SerializerFeature.IgnoreErrorGetter);
        }
        log.info("请求参数明细:" + requestParam);

        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        log.info("连接点方法为：" + methodName + ",参数为：" + args + ",异常为：" + ex);

        DeyiLog deyiLog = new DeyiLog();
        deyiLog.setIsSuccess(false);
        deyiLog.setRequestIp(request.getRemoteAddr());
        deyiLog.setRequestParam(requestParam);
        deyiLog.setUrl(request.getRequestURL().toString());
        deyiLog.setExceptionMsg(ex.getMessage());
        if (!Objects.isNull(annotation)) {
            deyiLog.setMethodName(annotation.value());
        }
        logMapper.insert(deyiLog);
    }
}
