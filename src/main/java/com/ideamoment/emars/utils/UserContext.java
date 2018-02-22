package com.ideamoment.emars.utils;

import java.util.Map;

/**
 * 用户上下文，用于各分层之间方便地获取用户信息
 *
 * 用户信息来自于Cookie
 *
 */
public class UserContext {

    private static ThreadLocal<Map<String, Object>> userInfo = new ThreadLocal<Map<String, Object>>();

    public static void set(Map<String, Object> userInfo) {
        UserContext.userInfo.set(userInfo);
    }

    public static Map<String, Object> get() {
        return UserContext.userInfo.get();
    }

    public static Long getUserId(){
        return (Long)UserContext.get().get(UserCookie.USERID);
    };

}
