package com.ideamoment.emars.usercustomize.controller;

import com.ideamoment.emars.model.UserListCustomize;
import com.ideamoment.emars.usercustomize.service.UserCustomizeService;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserCustomizeController {

    @Autowired
    private UserCustomizeService userCustomizeService;


    @RequestMapping(value="/usercustomizes", method= RequestMethod.GET)
    public Map<String, List<UserListCustomize>> queryUserCustomize(String page) {
        Long userId = UserContext.getUserId();

        Map<String, List<UserListCustomize>> result = userCustomizeService.queryUserCustomize(userId, page);

        return result;
    }

    @RequestMapping(value="/usercustomizes", method= RequestMethod.POST)
    public JsonData saveUserCustomize(@RequestBody HashMap<String, ArrayList<UserListCustomize>> data) {
        userCustomizeService.saveUserCustomizes(data);
        return JsonData.SUCCESS;
    }


    public UserCustomizeService getUserCustomizeService() {
        return userCustomizeService;
    }

    public void setUserCustomizeService(UserCustomizeService userCustomizeService) {
        this.userCustomizeService = userCustomizeService;
    }
}
