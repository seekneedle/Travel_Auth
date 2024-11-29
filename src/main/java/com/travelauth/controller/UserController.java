package com.travelauth.controller;

import com.travelauth.common.Result;
import com.travelauth.common.ResultUtil;
import com.travelauth.entity.UserEntity;
import com.travelauth.entity.dto.ActionDTO;
import com.travelauth.entity.dto.UserTokenDTO;
import com.travelauth.interceptor.BasicAuthInterceptor;
import com.travelauth.service.IUserService;
import com.travelauth.common.ResultEnum;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * @Author: dapeng
 * @Date: 2024/11/11/14:38
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private IUserService iUserService;

    @RequestMapping("getUser/{id}")
    public Result<Object> getUser(@PathVariable int id) {
        UserTokenDTO user = iUserService.findUserById(id);
        if (user != null) {
            return ResultUtil.success(ResultEnum.SUCCESS, user);
        } else {
            return ResultUtil.error(ResultEnum.UNKONW_ERROR);
        }
    }

    @PostMapping("login")
    public Result<Object> login(@RequestBody UserTokenDTO userTokenDTO) {
        try {
            Optional<UserTokenDTO> user = iUserService.findUserByName(userTokenDTO.getName());
            if (user.isPresent()) {
                return ResultUtil.success(ResultEnum.SUCCESS, user);
            } else {
                return ResultUtil.error(ResultEnum.UNKONW_USER);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResultUtil.error(e.getMessage());
        }
    }

    @PostMapping("getPermissions")
    public Result<Object> getPermission(@RequestHeader("Authorization") String token) {
        try {
            List<String> kb_ids = iUserService.getPermission(token);
            return ResultUtil.success(ResultEnum.SUCCESS, kb_ids);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResultUtil.error(e.getMessage());
        }
    }

    @PostMapping("checkPermission")
    public Result<Object> getPermission(@RequestHeader("Authorization") String token, @RequestBody ActionDTO actionDTO) {
        try {
            boolean permission = iUserService.checkPermission(token, actionDTO.getKb_id(), actionDTO.getAction());
            return ResultUtil.success(ResultEnum.SUCCESS, permission);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResultUtil.error(e.getMessage());
        }
    }
}
