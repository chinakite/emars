package com.ideamoment.emars.copyright.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yukiwang on 2018/2/24.
 */
@Controller
@RequestMapping("copyright")
public class CopyrightPageController {
    @RequestMapping(value = "/copyrightPage", method = RequestMethod.GET)
    public String copyrightPage() {
        return "copyright/copyrightPage";
    }

}
