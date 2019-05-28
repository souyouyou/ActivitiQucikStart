package com.krxinuo.service;

import com.krxinuo.entity.UserEntity;
import org.springframework.data.domain.Page;

public interface UserService {
    public UserEntity saveUser(UserEntity entity);

    public Page<UserEntity> getUsers(Integer pageIndex,Integer pageSize,String sortOrder,String sortColumn);
}
