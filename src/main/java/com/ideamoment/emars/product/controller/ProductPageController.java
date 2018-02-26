package com.ideamoment.emars.product.controller;

import com.ideamoment.emars.model.ProductResultVo;
import com.ideamoment.emars.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yukiwang on 2018/2/23.
 */
@Controller
@RequestMapping("product")
public class ProductPageController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/productPage", method = RequestMethod.GET)
    public String productPage() {
        return "product/productPage";
    }

    @RequestMapping(value = "/productDetail")
    public String productDetail(long id) {
        ProductResultVo productResultVo = productService.findProduct(id);
        return "product/productDetail";
    }

}
