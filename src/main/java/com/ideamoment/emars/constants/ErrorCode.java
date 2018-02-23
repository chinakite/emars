package com.ideamoment.emars.constants;

import java.util.HashMap;

public class ErrorCode {
    public static final String UNKNOWN_ERROR = "0-0001";
    public static final String FORM_INVALID = "0-0002";

    //登录登出
    public static final String UNAUTH_ERROR = "A-0001";
    public static final String LOGIN_ERROR = "A-0002";

    //该用户已存在
    public static final String USER_EXISTS = "A-0003";
    //该用户不存在
    public static final String USER_NOT_EXISTS = "A-0004";
    //超级管理员不能删除
    public static final String SUPERADMIN_CANT_OPERATE = "A-0005";
    //作品题材已存在
    public static final String SUBJECT_EXISTS = "B-0003";
    //作品题材不存在
    public static final String SUBJECT_NOT_EXISTS = "B-0004";
    //作者已存在
    public static final String AUTHOR_EXISTS = "C-0003";
    //作者不存在
    public static final String AUTHOR_NOT_EXISTS = "C-0004";
    //作者不能被删除
    public static final String AUTHOR_CANNOT_DELETE = "C-0005";

    public static final String SUBJECT_EXISTS = "B-0003";

    public static final String SUBJECT_NOT_EXISTS = "B-0004";

    public static final String EMAIL_SEND_ERROR = "C-0001";

    public static final HashMap<String, String> ERROR_MSG = new HashMap<String, String>();

    static {
        ERROR_MSG.put(UNKNOWN_ERROR, "系统发生了未知错误，请联系管理员。");
        ERROR_MSG.put(FORM_INVALID, "服务器端校验不通过，请正确输入。");
        ERROR_MSG.put(USER_EXISTS, "该用户名已存在");
        ERROR_MSG.put(USER_NOT_EXISTS, "该用户不存在或已被删除");
        ERROR_MSG.put(SUPERADMIN_CANT_OPERATE, "不能操作超级管理员");
        ERROR_MSG.put(EMAIL_SEND_ERROR, "发送邮件时发生异常");
    }

}
