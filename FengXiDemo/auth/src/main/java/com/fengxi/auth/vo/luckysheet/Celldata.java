package com.fengxi.auth.vo.luckysheet;

import lombok.Data;

/**
 * luckysheet的Celldata表格数据封装
 */
@Data
public class Celldata {
    //r、c是行列值，从（0，0）开始
    /**
     * 行
     */
    private String r;
    /**
     * 列
     */
    private String c;

    private V v = new V();
}
