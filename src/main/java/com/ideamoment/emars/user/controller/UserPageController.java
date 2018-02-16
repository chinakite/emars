package com.ideamoment.emars.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Chinakite on 2018/2/16.
 */
@Controller
public class UserPageController {
    @RequestMapping(value="/userIndexPage", method = RequestMethod.GET)
    public String userIndexPage() {
        return "user/userList";
    }
}
