package com.fengxi.auth.vo.luckysheet;

import lombok.Data;

@Data
public class V {
    /**
     * 显示值
     */
    private String m;

    /**
     * 原始值
     */
    private String v;

    /**
     * 字体颜色
     */
    private String fc;

    /**
     * 背景颜色
     */
    private String bg;

    private CT ct = new CT();
}
