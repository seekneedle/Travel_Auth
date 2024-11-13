package com.travelauth.service;

import com.travelauth.entity.UserEntity;
import com.travelauth.entity.dto.UserTokenDTO;

import java.util.List;

/**
 * @Author: dapeng
 * @Date: 2024/11/11/14:34
 * @Description:
 */
public interface IUserService {
    UserTokenDTO findUserById(Integer id);

    UserTokenDTO findUserByName(String name);

    List<UserEntity> findUserAll();

    String findKbIdByName(String name);

    String getPermission(String token, String action);
}
