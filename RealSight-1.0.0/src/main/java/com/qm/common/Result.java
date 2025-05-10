package com.qm.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: springsecurity-hello
 * @description: 相应类
 * @author: ZhangQingMin
 * @create: 2025-05-06 10:34
 **/
@Data // get set
public class Result {
    private Boolean success; // 返回的成功或者失败的标识符
    private Integer code; // 返回的状态码
    private String message; // 提示信息
    private Map<String, Object> data = new HashMap<String, Object>(); // 数据


    // 构造方法私有
    private Result() {}

    public static Result ok(){
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("成功");
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.ERROR);
        result.setMessage("");
        return result;
    }
    // 链式编程
    // Result.ok().success(true)
    // result.message("ok").data("item", list)

    public Result success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
    public Result message(String message) {
        this.setMessage(message);
        return this;
    }
    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
