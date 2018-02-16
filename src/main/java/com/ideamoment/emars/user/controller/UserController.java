package com.ideamoment.emars.user.controller;

import com.ideamoment.emars.model.User;
import com.ideamoment.emars.user.service.UserService;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/users", method=RequestMethod.GET)
    public JsonData listUsers(String key, String role, String status) {
        Page<User> users = userService.pageUsers();
    }
}
