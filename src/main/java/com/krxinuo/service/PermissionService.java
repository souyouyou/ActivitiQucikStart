package com.krxinuo.service;

import com.krxinuo.entity.TPermissionEntity;
import org.springframework.data.domain.Page;

public interface PermissionService {
    TPermissionEntity savePermission(TPermissionEntity entity);

    Page<TPermissionEntity> getPermissions(Integer page, Integer limit, String sortOrder, String sortColumn);

    TPermissionEntity getPermissionById(Long pid);
}
