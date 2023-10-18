package com.fengxi.auth.utils;

/**
 * 分页工具
 *
 * @author wujiuhe
 * @description: TODO
 * @title: PageUtil
 * @projectName FengXiDemo
 * @date 2023/2/6 15:42:12
 */
public class PageUtil {

    /**
     * 获取最开始的page
     * @param page
     * @param pageSize
     * @return
     */
    public static int getStartPage(int page, int pageSize) {
        return (page - 1) * pageSize;
    }
}
