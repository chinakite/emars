package com.ideamoment.emars.sale.controller;

import com.ideamoment.emars.model.ProductInfo;
import com.ideamoment.emars.sale.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Chinakite on 2018/6/19.
 */
@RestController
@RequestMapping("sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductInfo> products() {
        List<ProductInfo> products = saleService.listProducts();
        return products;
    }
}
