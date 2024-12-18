package com.travelauth.service;

import com.travelauth.entity.UserEntity;
import com.travelauth.entity.dto.UserTokenDTO;

import java.util.List;
import java.util.Optional;

/**
 * @Author: dapeng
 * @Date: 2024/11/11/14:34
 * @Description:
 */
public interface IUserService {
    UserTokenDTO findUserById(Integer id);

    Optional<UserTokenDTO> findUserByName(String name);

    List<UserEntity> findUserAll();

    List<UserEntity> findKbIdByName(String name);

    List<String> getPermission(String token);

    boolean checkPermission(String token, String kb_id, String action);
}
