package com.ideamoment.emars.make.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yukiwang on 2018/3/5.
 */
@Controller
@RequestMapping("make")
public class MakePageController {

    @RequestMapping(value = "/taskProductPage", method = RequestMethod.GET)
    public String taskProductPage() {
        return "make/taskProductPage";
    }

}
