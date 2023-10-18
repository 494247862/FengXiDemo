package com.fengxi.auth.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页统一返回VO
 *
 * @author wujiuhe
 * @description: TODO
 * @title: ResultPageVO
 * @projectName FengXiDemo
 * @date 2023/2/6 14:23:48
 */
@Data
@NoArgsConstructor
public class ResultPageVO<T> {
    /**
     * 总条数
     */
    private int count;

    /**
     * 返回数据
     */
    private List<T> data;

    public ResultPageVO(List<T> data, int count) {
        this.data = data;
        this.count = count;
    }

    /**
     * 返回数据
     *
     * @param <T>
     * @return
     */
    public static <T> ResultPageVO<T> returnData(List<T> data, int count) {
        return new ResultPageVO(data, count);
    }
}
