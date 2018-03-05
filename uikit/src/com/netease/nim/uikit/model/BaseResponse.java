package com.netease.nim.uikit.model;

/**
 * Created by SNAIL on 2017/3/9.
 */

public class BaseResponse<T> {

    //private Object status;
    private int code;

    private T data;

    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }


}
