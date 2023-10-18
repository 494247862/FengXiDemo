package com.fengxi.auth.service;

import com.fengxi.auth.dto.RoleDTO;
import com.fengxi.auth.dto.RolePageDTO;
import com.fengxi.auth.entity.DeyiRole;
import com.fengxi.auth.entity.DeyiUser;
import com.fengxi.auth.vo.ResultPageVO;

import java.util.List;

/**
 * @author wujiuhe
 * @description: TODO
 * @title: RoleService
 * @projectName FengXiDemo
 * @date 2023/1/28 17:33:05
 */
public interface RoleService {

    /**
     * 新增角色
     *
     * @param roleDTO
     * @return
     */
    public String addRole(RoleDTO roleDTO);

    /**
     * 编辑角色
     *
     * @param roleDTO
     * @return
     */
    public String updateRole(RoleDTO roleDTO);


    /**
     * 获取所有角色
     *
     * @return
     */
    List<DeyiRole> getAllRoles();

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    String deleteRole(Long id);

    /**
     * 分页获取角色数据
     *
     * @param rolePageDTO
     * @return
     */
    ResultPageVO<DeyiRole> QueryRolePage(RolePageDTO rolePageDTO);

    /**
     * 通过角色编号获取用户
     * @param code
     * @return
     */
    List<DeyiUser> getUserByCode(String code);
}
