package com.travelauth.dao;

import com.travelauth.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: dapeng
 * @Date: 2024/11/11/14:31
 * @Description:
 */
@Mapper
public interface IUserDao {
    UserEntity findUserById(Integer id);

    UserEntity findUserByName(String name);

    List<UserEntity> findUserAll();
    UserEntity findKbIdByName(String name);
}
