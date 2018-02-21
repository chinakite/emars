package com.ideamoment.emars.user.controller;

import com.ideamoment.emars.base.BasePageController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Chinakite on 2018/2/16.
 */
@Controller
public class UserPageController extends BasePageController {
    @RequestMapping(value="/userIndexPage", method = RequestMethod.GET)
    public String userIndexPage(Model model) {
        super.setUserInfo(model);
        return "user/userList";
    }
}
