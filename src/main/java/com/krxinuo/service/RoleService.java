package com.krxinuo.service;

import com.krxinuo.entity.TRoleEntity;
import org.springframework.data.domain.Page;

public interface RoleService {
    TRoleEntity saveRole(TRoleEntity entity);

    Page<TRoleEntity> getRoles(Integer page, Integer limit, String sortOrder, String sortColumn);

    TRoleEntity getRoleById(Long rId);
}
