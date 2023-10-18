package com.fengxi.auth.timer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fengxi.auth.dao.LogMapper;
import com.fengxi.auth.dto.CommonSettingDTO;
import com.fengxi.auth.entity.DeyiLog;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 日志定时任务
 */
@Component
@EnableScheduling
@Log4j2
public class LogTimer {

    @Autowired
    LogMapper logMapper;

    @Autowired
    CommonSettingDTO commonSettingDTO;

    /**
     * 每天执行一次
     * 删除一个月前的日志
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void timingDeleteLogs() {
        Long logSaveDay = commonSettingDTO.getLogSaveDay();
        if (Objects.isNull(logSaveDay)) {
            return;
        } else {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime localDateTime = now.minusDays(logSaveDay);
            List<DeyiLog> deyiLogs = logMapper.selectList(new LambdaQueryWrapper<DeyiLog>()
                    .notBetween(DeyiLog::getCreateTime, localDateTime, now)
            );
            if (deyiLogs.size() > 0) {
                logMapper.deleteBatchIds(deyiLogs.stream().map(DeyiLog::getId).collect(Collectors.toList()));
            }
        }
    }

}
