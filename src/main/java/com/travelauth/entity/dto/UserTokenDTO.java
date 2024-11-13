package com.travelauth.entity.dto;

import lombok.Data;

/**
 * @Author: dapeng
 * @Date: 2024/11/11/16:24
 * @Description:
 */
@Data
public class UserTokenDTO {
    private String id;
    private String name;
    private String token;
}
