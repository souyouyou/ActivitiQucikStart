package com.krxinuo.service.impl;

import com.krxinuo.dao.UserDao;
import com.krxinuo.entity.TUserEntity;
import com.krxinuo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public TUserEntity saveUser(TUserEntity entity) {
        TUserEntity TUserEntity = userDao.save(entity);
        return TUserEntity;
    }

    @Override
    public Page<TUserEntity> getUsers(Integer page, Integer limit, String sortOrder, String sortColumn) {

        Sort sort = new Sort(sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn);
        Pageable pageable = new PageRequest(page, limit, sort);

        Page<TUserEntity> users = userDao.findAll(pageable);

        return users;
    }

    @Override
    public TUserEntity getUserById(Long uid) {
        return userDao.findOne(uid);
    }

    @Override
    public List<TUserEntity> getUserList() {
        return userDao.findAll();
    }
}
