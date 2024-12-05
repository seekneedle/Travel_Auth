package com.travelauth.service.impl;

import com.travelauth.dao.IUserDao;
import com.travelauth.entity.UserEntity;
import com.travelauth.entity.dto.UserTokenDTO;
import com.travelauth.jwt.JwtUtil;
import com.travelauth.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.Null;
import java.util.*;

import static com.travelauth.interceptor.BasicAuthInterceptor.BEAR_PREFIX;

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
    public Optional<UserTokenDTO> findUserByName(String name) {
        List<UserEntity> entities = iUserDao.findUserByName(name);
        if (!entities.isEmpty()) {
            UserEntity entity = entities.get(0);
            UserTokenDTO userTokenDTO = new UserTokenDTO();
            userTokenDTO.setId(String.valueOf(entity.getId()));
            userTokenDTO.setName(entity.getName());
            userTokenDTO.setToken(JwtUtil.sign(entity.getName(), String.valueOf(System.currentTimeMillis())));
            return Optional.of(userTokenDTO);
        }
        return Optional.empty();
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
        String bearToken = token.substring(BEAR_PREFIX.length()).trim();
        String username = JwtUtil.getClaim(bearToken, JwtUtil.ACCOUNT);
        List<UserEntity> entities = iUserDao.findKbIdByName(username);
        // 创建一个 kb_id 列表
        Set<String> kbIdSet = new HashSet<>();

        for (UserEntity entity : entities) {
            kbIdSet.add(entity.getKb_id()); // 假设有 getKbId() 方法
        }
        return new ArrayList<>(kbIdSet);
    }

    @Override
    public boolean checkPermission(String token, String kb_id, String action) {
        String bearToken = token.substring(BEAR_PREFIX.length()).trim();
        String username = JwtUtil.getClaim(bearToken, JwtUtil.ACCOUNT);
        List<UserEntity> entities = iUserDao.checkPermission(username, kb_id, action);
        return entities.size() > 0;
    }
}
