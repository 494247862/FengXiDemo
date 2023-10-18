package com.fengxi.auth.handler;

import com.fengxi.auth.exception.BaseKnowException;
import com.fengxi.auth.vo.ResultVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一返回时异常处理
 *
 * @author wujiuhe
 * @description: TODO
 * @title: CustomerExceptionHandler
 * @projectName demo
 * @date 2023/1/8 19:05:55
 */
@RestControllerAdvice
@Log4j2
public class CustomerExceptionHandler {

    /**
     * 自定义异常处理 ServerBusyException
     */
    @ExceptionHandler(BaseKnowException.class)
    public ResultVO ErrorHandler(BaseKnowException e) {
        log.error(e.getMessage(), e);
        return ResultVO.error(e.getCode(), e.getMessage());
    }

    /**
     * 统一未知异常处理
     */
    @ExceptionHandler(Exception.class)
    public ResultVO HandlerExecption(Exception e) {
        log.error(e.getMessage(), e);
        return ResultVO.error(500, e.getMessage());
    }
}
