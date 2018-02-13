package com.ideamoment.emars.dashboard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DashboardController {
    private final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String dashboardPage() {
        return "dashboard";
    }
}
