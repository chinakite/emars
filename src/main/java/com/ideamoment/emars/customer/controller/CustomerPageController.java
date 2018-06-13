package com.ideamoment.emars.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Chinakite on 2018/6/14.
 */
@Controller
@RequestMapping("system")
public class CustomerPageController {
    @RequestMapping(value = "/customerPage", method = RequestMethod.GET)
    public String customerPage() {
        return "customer/customerPage";
    }
}
