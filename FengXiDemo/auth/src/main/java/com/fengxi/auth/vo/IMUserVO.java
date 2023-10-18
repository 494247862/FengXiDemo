package com.fengxi.auth.vo;

import lombok.Data;

import java.util.HashMap;

@Data
public class IMUserVO {
    //请求处理的结果，OK 表示处理成功，FAIL 表示失败
    private String ActionStatus;
    //错误码，0表示成功，非0表示失败
    private Integer ErrorCode;
    //错误信息
    private String ErrorInfo;

    private HashMap[] ResultItem;
}
