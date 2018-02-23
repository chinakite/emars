package com.ideamoment.emars.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yukiwang on 2018/2/23.
 */
@Controller
@RequestMapping("copyright")
public class ProductPageController {

    @RequestMapping(value = "/productPage", method = RequestMethod.GET)
    public String productPage() {
        return "product/productPage";
    }

}
