package com.krxinuo.dao;

import com.krxinuo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<UserEntity,String> {

    /**
     * 分页带条件查询
     * @param specification
     * @param pageable
     * @return
     */
    Page<UserEntity> findAll(Specification<UserEntity> specification, Pageable pageable);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<UserEntity> findAll(Pageable pageable);

}
