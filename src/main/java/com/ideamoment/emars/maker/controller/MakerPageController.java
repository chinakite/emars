package com.ideamoment.emars.maker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("system")
public class MakerPageController {
    @RequestMapping(value = "/makerPage", method = RequestMethod.GET)
    public String makerPage() {
        return "maker/makerPage";
    }
}
