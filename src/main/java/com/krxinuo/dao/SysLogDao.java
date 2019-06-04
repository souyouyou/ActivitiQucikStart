package com.krxinuo.dao;

import com.krxinuo.entity.SysLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

public interface SysLogDao extends CrudRepository<SysLogEntity,Long> {

    /**
     * 分页带条件查询
     * @param specification
     * @param pageable
     * @return
     */
    Page<SysLogEntity> findAll(Specification<SysLogEntity> specification, Pageable pageable);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<SysLogEntity> findAll(Pageable pageable);

}
