package com.fengxi.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单树VO
 *
 * @author wujiuhe
 * @description: TODO
 * @title: UserVO
 * @projectName FengXiDemo
 * @date 2023/1/29 10:18:45
 */
@Data
@ApiModel(value = "用户VO")
public class UserVO {

    private Long id;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "联系方式")
    private String phone;

    @ApiModelProperty(value = "是否有头像")
    private Boolean headPictureId;

    @ApiModelProperty(value = "所属角色")
    private List<Long> roleIds;

    @ApiModelProperty(value = "子项")
    private List<MenuTreeVO> children = new ArrayList<>();
}
