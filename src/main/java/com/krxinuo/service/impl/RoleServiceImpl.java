package com.krxinuo.service.impl;

import com.krxinuo.dao.RoleDao;
import com.krxinuo.entity.TRoleEntity;
import com.krxinuo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public TRoleEntity saveRole(TRoleEntity entity) {
        TRoleEntity roleEntity = roleDao.save(entity);
        return roleEntity;
    }

    @Override
    public Page<TRoleEntity> getRoles(Integer page, Integer limit, String sortOrder, String sortColumn) {

        Sort sort = new Sort(sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn);
        Pageable pageable = new PageRequest(page, limit, sort);

        Page<TRoleEntity> roles = roleDao.findAll(pageable);

        return roles;
    }

    @Override
    public TRoleEntity getRoleById(Long rId) {
        return roleDao.findOne(rId);
    }
}
