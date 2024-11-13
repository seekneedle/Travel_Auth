package com.travelauth.common;

/**
 * @Author: dapeng
 * @Date: 2024/11/11/14:42
 * @Description:
 */
public class ResultUtil {
    public static Result<Object> success(ResultEnum resultEnum, Object object) {
        Result<Object> result = new Result<>();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        result.setData(object);
        return result;
    }

    public static Result<Object> error(ResultEnum resultEnum) {
        Result<Object> result = new Result<>();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        return result;
    }

    public static Result<Object> error(String msg) {
        Result<Object> result = new Result<>();
        result.setCode(423);
        result.setMsg(msg);
        return result;
    }
}
