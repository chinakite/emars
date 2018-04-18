package com.ideamoment.emars.product.controller;

import com.ideamoment.emars.copyright.service.CopyrightService;
import com.ideamoment.emars.model.Copyright;
import com.ideamoment.emars.model.ProductCopyrightFile;
import com.ideamoment.emars.model.ProductInfo;
import com.ideamoment.emars.model.ProductResultVo;
import com.ideamoment.emars.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by yukiwang on 2018/2/23.
 */
@Controller
@RequestMapping("product")
public class ProductPageController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CopyrightService copyrightService;

    @RequestMapping(value = "/productPage", method = RequestMethod.GET)
    public String productPage() {
        return "product/productPage";
    }

    //TODO
    @RequestMapping(value = "/productDetail/{id}")
    public String productDetail(@PathVariable("id") long id, Model model) {
        ProductInfo product = productService.findProduct(id);
//        List<ProductCopyrightFile> copyrightFiles = productService.listProductCopyrightFiles(product.getId());
//        List<Copyright> contracts = copyrightService.listProductContracts(product.getId());

        model.addAttribute("product", product);
//        model.addAttribute("copyrightFiles", copyrightFiles);
//        model.addAttribute("contracts", contracts);

        return "product/productDetail";
    }

}
