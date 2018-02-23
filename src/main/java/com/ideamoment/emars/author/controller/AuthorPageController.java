package com.ideamoment.emars.author.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yukiwang on 2018/2/22.
 */
@Controller
@RequestMapping("system")
public class AuthorPageController {

    @RequestMapping(value = "/authorPage", method = RequestMethod.GET)
    public String authorPage() {
        return "author/authorPage";
    }

}
