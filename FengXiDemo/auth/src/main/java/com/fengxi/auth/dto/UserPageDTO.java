package com.fengxi.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户分页DTO
 *
 * @author wujiuhe
 * @description: TODO
 * @title: UserPageDTO
 * @projectName FengXiDemo
 * @date 2023/2/6 15:33:54
 */
@Data
@ApiModel("用户分页DTO")
public class UserPageDTO {

    @ApiModelProperty("页数")
    private int page = 1;

    @ApiModelProperty("条数")
    private int pageSize = 10;

    @ApiModelProperty("角色Id")
    private List<Long> roleIds;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("联系方式")
    private String phone;

    @ApiModelProperty("性别")
    private String sex;
}
