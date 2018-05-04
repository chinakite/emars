package com.ideamoment.emars.announcer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("system")
public class AnnouncerPageController {
    @RequestMapping(value = "/announcerPage", method = RequestMethod.GET)
    public String announcerPage() {
        return "announcer/announcerPage";
    }
}
