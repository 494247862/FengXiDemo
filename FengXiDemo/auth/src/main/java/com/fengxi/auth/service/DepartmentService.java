package com.fengxi.auth.service;

import com.fengxi.auth.entity.DeyiDepartment;
import com.fengxi.auth.vo.DepartmentTreeVO;

import java.util.List;

/**
 * @author wujiuhe
 * @description: TODO
 * @title: DepartmentService
 * @projectName FengXiDemo
 * @date 2023/1/29 17:36:06
 */
public interface DepartmentService {

    /**
     * 新增部门
     *
     * @param deyiDepartment
     * @return
     */
    public String addDepartment(DeyiDepartment deyiDepartment);

    /**
     * 获取部门树
     *
     * @return
     */
    List<DepartmentTreeVO> getDepartmentTree();

    /**
     * 删除部门
     * @param id
     * @return
     */
    String deleteDepartment(Long id);

    /**
     * 更新部门
     * @param deyiDepartment
     * @return
     */
    String updateDepartment(DeyiDepartment deyiDepartment);
}
