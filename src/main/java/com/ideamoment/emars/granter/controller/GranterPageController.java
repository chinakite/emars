package com.ideamoment.emars.granter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Chinakite on 2018/4/6.
 */
@Controller
@RequestMapping("system")
public class GranterPageController {
    @RequestMapping(value = "/granterPage", method = RequestMethod.GET)
    public String granterPage() {
        return "granter/granterPage";
    }
}
