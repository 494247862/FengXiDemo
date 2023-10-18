package com.fengxi.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fengxi.auth.dao.RoleMapper;
import com.fengxi.auth.dto.RoleDTO;
import com.fengxi.auth.dto.RolePageDTO;
import com.fengxi.auth.entity.DeyiRole;
import com.fengxi.auth.entity.DeyiUser;
import com.fengxi.auth.exception.BaseKnowException;
import com.fengxi.auth.service.RoleService;
import com.fengxi.auth.utils.PageUtil;
import com.fengxi.auth.vo.ResultPageVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wujiuhe
 * @description: TODO
 * @title: RoleServiceImpl
 * @projectName FengXiDemo
 * @date 2023/1/28 17:33:19
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    RoleMapper roleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addRole(RoleDTO roleDTO) {

        DeyiRole check = roleMapper.selectOne(new LambdaQueryWrapper<DeyiRole>()
                .eq(DeyiRole::getIsDeleted, false)
                .eq(DeyiRole::getRoleCode, roleDTO.getRoleCode())
        );

        if (check != null)
            throw new BaseKnowException(10000, "该角色代码已存在");

        DeyiRole deyiRole = new DeyiRole();
        deyiRole.setRoleCode(roleDTO.getRoleCode());
        deyiRole.setRoleName(roleDTO.getRoleName());

        if (roleDTO.getMenuIds() != null && roleDTO.getMenuIds().size() > 0) {
            String ids = roleDTO.getMenuIds().stream().map(String::valueOf).collect(Collectors.joining(","));
            deyiRole.setMenuIds(ids);
        } else {
            deyiRole.setMenuIds(null);
        }

        if (roleDTO.getButtonIds() != null && roleDTO.getButtonIds().size() > 0) {
            String ids = roleDTO.getButtonIds().stream().map(String::valueOf).collect(Collectors.joining(","));
            deyiRole.setButtonIds(ids);
        } else {
            deyiRole.setButtonIds(null);
        }

        roleMapper.insert(deyiRole);
        return "Success";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateRole(RoleDTO roleDTO) {

        DeyiRole role = roleMapper.selectOne(new LambdaQueryWrapper<DeyiRole>()
                .eq(DeyiRole::getIsDeleted, false)
                .eq(DeyiRole::getRoleCode, roleDTO.getRoleCode())
        );

        if (role == null)
            throw new BaseKnowException(10000, "该角色不存在");

        role.setRoleCode(roleDTO.getRoleCode());
        role.setRoleName(roleDTO.getRoleName());

        if (roleDTO.getMenuIds() != null && roleDTO.getMenuIds().size() > 0) {
            String ids = roleDTO.getMenuIds().stream().map(String::valueOf).collect(Collectors.joining(","));
            role.setMenuIds(ids);
        } else {
            role.setMenuIds(null);
        }

        if (roleDTO.getButtonIds() != null && roleDTO.getButtonIds().size() > 0) {
            String ids = roleDTO.getButtonIds().stream().map(String::valueOf).collect(Collectors.joining(","));
            role.setButtonIds(ids);
        } else {
            role.setButtonIds(null);
        }

        roleMapper.updateById(role);
        return "Success";
    }

    @Override
    public List<DeyiRole> getAllRoles() {
        return roleMapper.selectList(new LambdaQueryWrapper<DeyiRole>().eq(DeyiRole::getIsDeleted, false));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteRole(Long id) {
        roleMapper.update(new DeyiRole(), new LambdaUpdateWrapper<DeyiRole>()
                .set(DeyiRole::getIsDeleted, true)
                .eq(DeyiRole::getId, id)
        );
        return "Success";
    }

    @Override
    public ResultPageVO<DeyiRole> QueryRolePage(RolePageDTO rolePageDTO) {
        rolePageDTO.setPage(PageUtil.getStartPage(rolePageDTO.getPage(), rolePageDTO.getPageSize()));
        List<DeyiRole> data = roleMapper.QueryRolePage(rolePageDTO);
        Integer count = roleMapper.getCount(rolePageDTO);
        return ResultPageVO.returnData(data, count);
    }

    @Override
    public List<DeyiUser> getUserByCode(String code) {
        return roleMapper.getUserByCode(code);
    }
}
