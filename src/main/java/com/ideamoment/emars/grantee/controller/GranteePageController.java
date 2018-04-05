package com.ideamoment.emars.grantee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Chinakite on 2018/4/5.
 */
@Controller
@RequestMapping("system")
public class GranteePageController {
    @RequestMapping(value = "/granteePage", method = RequestMethod.GET)
    public String granteePage() {
        return "grantee/granteePage";
    }
}
