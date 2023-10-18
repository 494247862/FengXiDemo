package com.fengxi.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fengxi.auth.dao.DepartmentMapper;
import com.fengxi.auth.entity.DeyiDepartment;
import com.fengxi.auth.exception.BaseKnowException;
import com.fengxi.auth.service.DepartmentService;
import com.fengxi.auth.vo.DepartmentTreeVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wujiuhe
 * @description: TODO
 * @title: DepartmentServiceImpl
 * @projectName FengXiDemo
 * @date 2023/1/29 17:36:19
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    DepartmentMapper departmentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addDepartment(DeyiDepartment deyiDepartment) {

        DeyiDepartment check = departmentMapper.selectOne(new LambdaQueryWrapper<DeyiDepartment>()
                .eq(DeyiDepartment::getIsDeleted, false)
                .eq(DeyiDepartment::getDepartmentCode, deyiDepartment.getDepartmentCode())
        );

        if (!Objects.isNull(check))
            throw new BaseKnowException(10000, "该部门编号已存在");

        departmentMapper.insert(deyiDepartment);

        return "Success";
    }

    @Override
    public List<DepartmentTreeVO> getDepartmentTree() {
        List<DeyiDepartment> deyiDepartments = departmentMapper.selectList(new LambdaQueryWrapper<DeyiDepartment>().eq(DeyiDepartment::getIsDeleted, false));
        return getDepartmentTreeVOS(deyiDepartments);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteDepartment(Long id) {
        departmentMapper.update(new DeyiDepartment(), new LambdaUpdateWrapper<DeyiDepartment>()
                .set(DeyiDepartment::getIsDeleted, true)
                .eq(DeyiDepartment::getId, id)
        );
        return "Success";
    }

    @Override
    public String updateDepartment(DeyiDepartment deyiDepartment) {
        departmentMapper.update(new DeyiDepartment(), new LambdaUpdateWrapper<DeyiDepartment>()
                .set(DeyiDepartment::getDepartmentCode, deyiDepartment.getDepartmentCode())
                .set(DeyiDepartment::getDepartmentName, deyiDepartment.getDepartmentName())
                .eq(DeyiDepartment::getId, deyiDepartment.getId())
        );
        return "Success";
    }

    /**
     * 获取菜单树
     *
     * @return
     */
    private List<DepartmentTreeVO> getDepartmentTreeVOS(List<DeyiDepartment> departments) {
        List<DepartmentTreeVO> departmentTree = new ArrayList<>();
        // 获取所有一级菜单
        List<DeyiDepartment> oneDepartments = departments.stream().filter(t -> t.getLevel().equals(1)).collect(Collectors.toList());

        for (DeyiDepartment department : oneDepartments) {
            DepartmentTreeVO departmentTreeVO = buildChilTree(department, departments);
            departmentTree.add(departmentTreeVO);
        }
        return departmentTree;
    }


    /**
     * 递归，建立子树形结构
     *
     * @return
     */
    private DepartmentTreeVO buildChilTree(DeyiDepartment pNode, List<DeyiDepartment> departmentList) {
        List<DepartmentTreeVO> chilMenus = new ArrayList<>();
        DepartmentTreeVO departmentTreeVO = new DepartmentTreeVO();
        departmentTreeVO.setId(pNode.getId());
        departmentTreeVO.setLevel(pNode.getLevel());
        departmentTreeVO.setDepartmentCode(pNode.getDepartmentCode());
        departmentTreeVO.setDepartmentName(pNode.getDepartmentName());
        departmentTreeVO.setParentId(pNode.getParentId());

        for (DeyiDepartment deyiDepartment : departmentList) {
            if (pNode.getId().equals(deyiDepartment.getParentId())) {
                chilMenus.add(buildChilTree(deyiDepartment, departmentList));
            }
        }
        departmentTreeVO.setChildren(chilMenus);
        return departmentTreeVO;
    }
}
