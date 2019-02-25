package com.ideamoment.emars.usercustomize.service.impl;

import com.ideamoment.emars.model.UserListCustomize;
import com.ideamoment.emars.model.enumeration.UserCustomizePage;
import com.ideamoment.emars.model.enumeration.UserCustomizePosition;
import com.ideamoment.emars.usercustomize.dao.UserCustomizeMapper;
import com.ideamoment.emars.usercustomize.service.UserCustomizeService;
import com.ideamoment.emars.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserCustomizeServiceImpl implements UserCustomizeService {

    @Autowired
    private UserCustomizeMapper userCustomizeMapper;

    @Override
    @Transactional
    public Map<String, List<UserListCustomize>> queryUserCustomize(Long userId, String page) {
        List<UserListCustomize> searchCustomizes = userCustomizeMapper.queryUserListCustomize(userId, page, UserCustomizePosition.SEARCH);
        if(searchCustomizes == null || searchCustomizes.size() == 0) {
            searchCustomizes = userCustomizeMapper.queryDefaultUserListCustomize(page, UserCustomizePosition.SEARCH);
        }

        List<UserListCustomize> tableCustomizes = userCustomizeMapper.queryUserListCustomize(userId, page, UserCustomizePosition.TABLE);
        if(tableCustomizes == null || tableCustomizes.size() == 0) {
            tableCustomizes = userCustomizeMapper.queryDefaultUserListCustomize(page, UserCustomizePosition.TABLE);
        }

        Map<String, List<UserListCustomize>> result = new HashMap<String, List<UserListCustomize>>();
        result.put(UserCustomizePosition.SEARCH_TEXT_EN, searchCustomizes);
        result.put(UserCustomizePosition.TABLE_TEXT_EN, tableCustomizes);

        return result;
    }

    @Override
    @Transactional
    public void saveUserCustomizes(HashMap<String, ArrayList<UserListCustomize>> data) {
        Long userId = UserContext.getUserId();

        List<UserListCustomize> searchCustomizes = data.get("search");

        List<UserListCustomize> tableCustomizes = data.get("table");

        Date curTime = new Date();

        userCustomizeMapper.deleteUserCustomize(userId, UserCustomizePage.PRODUCT);

        saveUserCustomize(userId, searchCustomizes, curTime);
        saveUserCustomize(userId, tableCustomizes, curTime);
    }

    private void saveUserCustomize(Long userId, List<UserListCustomize> tableCustomizes, Date curTime) {
        int c = 0;
        for(UserListCustomize tc : tableCustomizes) {
            tc.setCreator(userId);
            tc.setCreateTime(curTime);
            tc.setSort(c++);
            tc.setUserId(userId);
            userCustomizeMapper.saveUserCustomize(tc);
        }
    }
}
