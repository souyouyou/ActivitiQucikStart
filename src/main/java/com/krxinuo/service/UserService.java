package com.krxinuo.service;

import com.krxinuo.entity.TUserEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    TUserEntity saveUser(TUserEntity entity);

    Page<TUserEntity> getUsers(Integer page, Integer limit, String sortOrder, String sortColumn);

    TUserEntity getUserById(Long uid);

    List<TUserEntity> getUserList();

}
