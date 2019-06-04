package com.krxinuo.service.impl;

import com.krxinuo.dao.PermissionDao;
import com.krxinuo.entity.TPermissionEntity;
import com.krxinuo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public TPermissionEntity savePermission(TPermissionEntity entity) {
        return permissionDao.save(entity);
    }

    @Override
    public Page<TPermissionEntity> getPermissions(Integer page, Integer limit, String sortOrder, String sortColumn) {
        Sort sort = new Sort(sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn);
        Pageable pageable = new PageRequest(page, limit, sort);

        return permissionDao.findAll(pageable);
    }

    @Override
    public TPermissionEntity getPermissionById(Long pid) {
        return permissionDao.findOne(pid);
    }
}
