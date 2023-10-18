package com.fengxi.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Lob;
import java.io.Serializable;

/**
 * 日志实体类
 */
@Data
@Entity
@TableName("deyi_log")
@ApiModel("日志实体类")
public class DeyiLog extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("请求url")
    private String url;

    @ApiModelProperty("是否成功")
    private Boolean isSuccess;

    @ApiModelProperty("请求参数")
    @Lob
    private String requestParam;

    @ApiModelProperty("返回内容")
    @Lob
    private String requestResponse;

    @ApiModelProperty("请求IP")
    private String requestIp;

    @ApiModelProperty("异常信息")
    @Lob
    private String exceptionMsg;

    @ApiModelProperty("方法名称")
    private String methodName;

}
