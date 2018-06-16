package com.ideamoment.emars.sale.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Chinakite on 2018/6/16.
 */
@Controller
@RequestMapping("sale")
public class SalePageController {
    @RequestMapping(value = "/salePage", method = RequestMethod.GET)
    public String salePage() {
        return "sale/salePage";
    }

}
