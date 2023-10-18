package com.fengxi.auth.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回VO
 * @author wujiuhe
 * @description: TODO
 * @title: ResultVO
 * @projectName demo
 * @date 2023/1/8 17:40:59
 */
@Data
@NoArgsConstructor
public class ResultVO<T> {
    private int code;
    private String message;
    private T data;

    public ResultVO(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 成功
     */
    public static <T> ResultVO<T> success(T data) {
        ResultVO<T> result = new ResultVO<T>();
        result.setCode(200);
        result.setData(data);
        return result;
    }
    /**
     * 失败
     */
    public static <T> ResultVO<T> error(int code, String message) {
        return new ResultVO(code, message);
    }
}
