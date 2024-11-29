package com.travelauth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: dapeng
 * @Date: 2024/11/11/14:27
 * @Description:
 */
@Getter
@Setter
@ToString
public class UserEntity {
    private int id;
    private String name;
    private String kb_id;
    private String action;
}
