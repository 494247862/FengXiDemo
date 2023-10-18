package com.fengxi.auth.exception;

import lombok.*;

/**
 * 通用自定义异常
 *
 * @author wujiuhe
 * @description: TODO
 * @title: BaseKnowException
 * @projectName demo
 * @date 2023/1/8 19:41:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseKnowException extends RuntimeException {
    private Integer code;
    private String message;
}
