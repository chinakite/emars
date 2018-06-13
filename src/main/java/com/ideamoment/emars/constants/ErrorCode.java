package com.ideamoment.emars.constants;

import java.util.HashMap;

public class ErrorCode {
    public static final String UNKNOWN_ERROR = "0-0001";
    public static final String FORM_INVALID = "0-0002";
    public static final String UPLOAD_ERROR = "0-0003";

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
    //作品已存在
    public static final String PRODUCT_EXISTS = "E-0003";
    //作品不存在
    public static final String PRODUCT_NOT_EXISTS = "E-0004";
    //作品不能被删除
    public static final String PRODUCT_CANNOT_DELETE = "E-0005";
    //同名作品已存在
    public static final String PRODUCT_DUPLICATED = "E-0006";
    //作品ISBN号系统中已存在
    public static final String ISBN_DUPLICATED = "E-0007";
    //添加作品创建作者错误
    public static final String PRODUCT_AUTHOR_ERROR = "E-0008";
    //作品版权文件不全，无法入库
    public static final String PRODUCT_COPYRIGHT_FILES_NOT_COMPLETE="E-0009";
    //作品制作文件不全，无法入库
    public static final String PRODUCT_MAKE_FILES_NOT_COMPLETE="E-0009";

    //NAME不能为空
    public static final String NAME_REQUIED = "F-0001";

    public static final String EMAIL_SEND_ERROR = "D-0001";
    public static final String EMAIL_SAVE_ERROR = "D-0001";

    //签约主体已存在
    public static final String GRANTEE_EXISTS = "G-0003";
    //签约主体不存在
    public static final String GRANTEE_NOT_EXISTS = "G-0004";
    //签约主体不能被删除
    public static final String GRANTEE_CANNOT_DELETE = "G-0005";

    //授权方已存在
    public static final String GRANTER_EXISTS = "H-0003";
    //授权方不存在
    public static final String GRANTER_NOT_EXISTS = "H-0004";
    //授权方不能被删除
    public static final String GRANTER_CANNOT_DELETE = "H-0005";

    //版权不存在
    public static final String COPYRIGHT_NOT_EXISTS = "I-0004";

    //演播人已存在
    public static final String ANNOUNCER_EXISTS = "J-0003";
    //演播人不存在
    public static final String ANNOUNCER_NOT_EXISTS = "J-0004";
    //演播人不能被删除
    public static final String ANNOUNCER_CANNOT_DELETE = "J-0005";

    //制作方已存在
    public static final String MAKER_EXISTS = "K-0003";
    //制作方不存在
    public static final String MAKER_NOT_EXISTS = "K-0004";
    //制作方不能被删除
    public static final String MAKER_CANNOT_DELETE = "K-0005";

    //制作合同价格错误
    public static final String MAKECONTRACT_PIRCE_ERROR = "M-0001";
    //制作合同集数错误
    public static final String MAKECONTRACT_SECTION_ERROR = "M-0002";

    //客户已存在
    public static final String CUSTOMER_EXISTS = "N-0001";
    //客户不存在
    public static final String CUSTOMER_NOT_EXISTS = "N-0002";
    //客户不能被删除
    public static final String CUSTOMER_CANNOT_DELETE = "N-0003";

    public static final HashMap<String, String> ERROR_MSG = new HashMap<String, String>();

    static {
        ERROR_MSG.put(UPLOAD_ERROR, "上传文件时发生了错误，请联系管理员。");
        ERROR_MSG.put(UNKNOWN_ERROR, "系统发生了未知错误，请联系管理员。");
        ERROR_MSG.put(FORM_INVALID, "服务器端校验不通过，请正确输入。");
        ERROR_MSG.put(USER_EXISTS, "该用户名已存在");
        ERROR_MSG.put(USER_NOT_EXISTS, "该用户不存在或已被删除");
        ERROR_MSG.put(SUPERADMIN_CANT_OPERATE, "不能操作超级管理员");
        ERROR_MSG.put(EMAIL_SEND_ERROR, "发送邮件时发生异常");
        ERROR_MSG.put(EMAIL_SAVE_ERROR, "邮件设置保存失败");
        ERROR_MSG.put(GRANTEE_EXISTS, "该签约主体已存在");
        ERROR_MSG.put(GRANTEE_NOT_EXISTS, "该签约主体不存在");
        ERROR_MSG.put(GRANTEE_CANNOT_DELETE, "该签约主体有关联的版权合同，无法删除");
        ERROR_MSG.put(GRANTER_EXISTS, "该授权方已存在");
        ERROR_MSG.put(GRANTER_NOT_EXISTS, "该授权方不存在");
        ERROR_MSG.put(GRANTER_CANNOT_DELETE, "该授权方有关联的版权合同，无法删除");

        ERROR_MSG.put(MAKER_EXISTS, "该制作方已存在");
        ERROR_MSG.put(MAKER_NOT_EXISTS, "该制作方不存在");
        ERROR_MSG.put(MAKER_CANNOT_DELETE, "该制作方有关联的制作合同，无法删除");

        ERROR_MSG.put(ANNOUNCER_EXISTS, "该演播人已存在");
        ERROR_MSG.put(ANNOUNCER_NOT_EXISTS, "该演播人不存在");
        ERROR_MSG.put(ANNOUNCER_CANNOT_DELETE, "该演播人有关联的作品，无法删除");

        ERROR_MSG.put(PRODUCT_NOT_EXISTS, "该作品不存在或已被删除");
        ERROR_MSG.put(PRODUCT_COPYRIGHT_FILES_NOT_COMPLETE, "该作品版权文件不全，无法入库");
    }

}
