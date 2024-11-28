package com.travelauth.service.impl;

import com.travelauth.dao.IUserDao;
import com.travelauth.entity.UserEntity;
import com.travelauth.entity.dto.UserTokenDTO;
import com.travelauth.jwt.JwtUtil;
import com.travelauth.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dapeng
 * @Date: 2024/11/11/14:35
 * @Description:
 */
@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private IUserDao iUserDao;

    @Override
    public UserTokenDTO findUserById(Integer id) {
        UserEntity entity = iUserDao.findUserById(id);

        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setId(String.valueOf(entity.getId()));
        userTokenDTO.setName(entity.getName());

        userTokenDTO.setToken(JwtUtil.sign(entity.getName(), String.valueOf(System.currentTimeMillis())));

        return userTokenDTO;
    }

    @Override
    public UserTokenDTO findUserByName(String name) {
        UserEntity entity = iUserDao.findUserByName(name);

        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setId(String.valueOf(entity.getId()));
        userTokenDTO.setName(entity.getName());

        userTokenDTO.setToken(JwtUtil.sign(entity.getName(), String.valueOf(System.currentTimeMillis())));

        return userTokenDTO;
    }

    @Override
    public List<UserEntity> findUserAll() {
        return iUserDao.findUserAll();
    }

    @Override
    public List<UserEntity> findKbIdByName(String name) {
        List<UserEntity> entities = iUserDao.findKbIdByName(name);
        return entities;
    }

    @Override
    public List<String> getPermission(String token) {
        String username = JwtUtil.getClaim(token, JwtUtil.ACCOUNT);
        List<UserEntity> entities = iUserDao.findKbIdByName(username);
        // 创建一个 kb_id 列表
        List<String> kbIds = new ArrayList<>();

// 遍历 UserEntity 列表，提取 kb_id
        for (UserEntity entity : entities) {
            kbIds.add(entity.getKb_id()); // 假设有 getKbId() 方法
        }
        return kbIds;
    }
}
