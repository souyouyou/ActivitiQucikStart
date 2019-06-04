package com.krxinuo.service.impl;

import com.krxinuo.dao.SysLogDao;
import com.krxinuo.entity.SysLogEntity;
import com.krxinuo.service.SysLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public SysLogEntity saveLog(SysLogEntity entity) {
        return sysLogDao.save(entity);
    }

    @Override
    public Page<SysLogEntity> getLogs(Integer page, Integer limit, String sortOrder, String sortColumn) {

        Sort sort = new Sort(sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn);
        Pageable pageable = new PageRequest(page, limit, sort);

        return sysLogDao.findAll(pageable);
    }

    @Override
    public SysLogEntity getLogById(Long id) {
        return sysLogDao.findOne(id);
    }

    @Override
    public Page<SysLogEntity> getConditionLogs(SysLogEntity sysLogEntity, Integer page, Integer limit, String sortOrder, String sortColumn) {

        Sort sort = new Sort(sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn);
        Pageable pageable = new PageRequest(page, limit, sort);

        Specification<SysLogEntity> specification = (root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if (!StringUtils.isEmpty(sysLogEntity.getOptContent())) {
                list.add(cb.like(root.get("optContent").as(String.class), "%" + sysLogEntity.getOptContent() + "%"));
            }

            return cb.and(list.toArray(new Predicate[list.size()]));
        };

        return sysLogDao.findAll(specification,pageable);

    }

}
