package com.krxinuo.dao;

import com.krxinuo.entity.TRoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

public interface RoleDao extends CrudRepository<TRoleEntity,Long> {

    /**
     * 分页带条件查询
     * @param specification
     * @param pageable
     * @return
     */
    Page<TRoleEntity> findAll(Specification<TRoleEntity> specification, Pageable pageable);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<TRoleEntity> findAll(Pageable pageable);

}
