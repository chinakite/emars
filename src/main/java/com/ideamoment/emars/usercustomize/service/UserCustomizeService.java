package com.ideamoment.emars.usercustomize.service;

import com.ideamoment.emars.model.UserListCustomize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserCustomizeService {
    /**
     * 获取用户界面定义
     *
     * @param userId
     * @param page
     * @return
     */
    Map<String, List<UserListCustomize>> queryUserCustomize(Long userId, String page);

    void saveUserCustomizes(HashMap<String, ArrayList<UserListCustomize>> data);
}
