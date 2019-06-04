package com.krxinuo.dao;

import com.krxinuo.entity.TUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDao extends CrudRepository<TUserEntity,Long> {

    /**
     * 分页带条件查询
     * @param specification
     * @param pageable
     * @return
     */
    Page<TUserEntity> findAll(Specification<TUserEntity> specification, Pageable pageable);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<TUserEntity> findAll(Pageable pageable);

    List<TUserEntity> findAll();

}
