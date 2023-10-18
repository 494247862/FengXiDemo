package com.fengxi.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fengxi.auth.entity.DeyiLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日志Mapper
 */
@Mapper
public interface LogMapper extends BaseMapper<DeyiLog> {
}
