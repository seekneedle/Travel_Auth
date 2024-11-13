package com.travelauth.common;
import lombok.Getter;

/**
 * @Author: dapeng
 * @Date: 2024/11/11/14:42
 * @Description:
 */
@Getter
public enum ResultEnum {
    UNKONW_ERROR(1001, "未知错误"),
    SUCCESS(200, "成功")
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
