package com.krxinuo.service.impl;

import com.krxinuo.dao.UserDao;
import com.krxinuo.entity.UserEntity;
import com.krxinuo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity saveUser(UserEntity entity) {
        UserEntity userEntity = userDao.save(entity);
        return userEntity;
    }

    @Override
    public Page<UserEntity> getUsers(Integer pageIndex, Integer pageSize, String sortOrder, String sortColumn) {

        Sort sort = new Sort(sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn);
        Pageable pageable = new PageRequest(pageIndex, pageSize, sort);

        Page<UserEntity> users = userDao.findAll(pageable);

        return users;
    }
}
