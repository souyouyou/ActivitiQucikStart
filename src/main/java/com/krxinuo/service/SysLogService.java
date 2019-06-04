package com.krxinuo.service;

import com.krxinuo.entity.SysLogEntity;
import org.springframework.data.domain.Page;

public interface SysLogService {
    SysLogEntity saveLog(SysLogEntity entity);

    Page<SysLogEntity> getLogs(Integer page, Integer limit, String sortOrder, String sortColumn);

    SysLogEntity getLogById(Long id);

    Page<SysLogEntity> getConditionLogs(SysLogEntity sysLogEntity,Integer page, Integer limit, String sortOrder, String sortColumn);

}
