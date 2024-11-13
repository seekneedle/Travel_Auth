package com.travelauth.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: dapeng
 * @Date: 2024/11/11/14:41
 * @Description:
 */
@Getter
@Setter
public class Result<T> {
    /**
     * 错误码.
     */
    private Integer code;

    /**
     * 提示信息.
     */
    private String msg;

    /**
     * 具体的内容.
     */
    private T data;
}
