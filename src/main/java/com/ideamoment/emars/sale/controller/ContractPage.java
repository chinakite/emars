package com.ideamoment.emars.sale.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yukiwang on 2018/3/19.
 */
@Controller
@RequestMapping("sale")
public class ContractPage {

    @RequestMapping(value = "/contractPage", method = RequestMethod.GET)
    public String contractPage() {
        return "sale/contractPage";
    }
}
