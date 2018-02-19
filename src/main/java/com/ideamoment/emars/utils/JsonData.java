package com.ideamoment.emars.utils;

import com.google.gson.Gson;

public class JsonData<T> {
    private String code;
    private String msg;
    private T data;

    public static final String SUCCESS_CODE = "0";
    public static final String SUCCESS_MSG = "success";

    public static final JsonData SUCCESS = new JsonData(SUCCESS_CODE, SUCCESS_MSG);

    public JsonData() {
    }

    public JsonData(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonData(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> JsonData<T> success(T data) {
        return new JsonData("0", "", data);
    }

    public static <T> JsonData<T> success(String msg, T data) {
        return new JsonData("0", msg, data);
    }

    public static JsonData error(String code, String msg) {
        return new JsonData(code, msg);
    }

    public static <T> JsonData<T> error(String code, String msg, T data) {
        return new JsonData(code, msg, data);
    }

    public static JsonData notLoginError() {
        return new JsonData("0-0004", "用户未登录");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
