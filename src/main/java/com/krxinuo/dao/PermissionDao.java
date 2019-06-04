package com.krxinuo.dao;

import com.krxinuo.entity.TPermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

public interface PermissionDao extends CrudRepository<TPermissionEntity,Long> {

    /**
     * 分页带条件查询
     * @param specification
     * @param pageable
     * @return
     */
    Page<TPermissionEntity> findAll(Specification<TPermissionEntity> specification, Pageable pageable);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<TPermissionEntity> findAll(Pageable pageable);

}
