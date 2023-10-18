package com.fengxi.auth.dto;

import com.fengxi.auth.entity.DeyiRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色分页查询DTO
 *
 * @author wujiuhe
 * @description: TODO
 * @title: RolePageDTO
 * @projectName FengXiDemo
 * @date 2023/2/7 16:06:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("角色分页查询DTO")
public class RolePageDTO extends DeyiRole {
    @ApiModelProperty("页数")
    private int page = 1;

    @ApiModelProperty("条数")
    private int pageSize = 10;
}
