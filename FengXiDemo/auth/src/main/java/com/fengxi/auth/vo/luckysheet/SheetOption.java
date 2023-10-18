package com.fengxi.auth.vo.luckysheet;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * luckysheet封装类
 */
@Data
public class SheetOption {
    /**
     * 工作表名称
     */
    private String name;

    /**
     * 颜色
     */
    private String color;

    /**
     * 状态
     */
    private int status;

    /**
     * 索引
     */
    private String index;

    /**
     * 是否隐藏
     */
    private int hide;

    private List<Celldata> celldata = new ArrayList<Celldata>();
}
