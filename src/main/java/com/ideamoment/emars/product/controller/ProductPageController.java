package com.ideamoment.emars.product.controller;

import com.ideamoment.emars.copyright.service.CopyrightService;
import com.ideamoment.emars.make.service.MakeService;
import com.ideamoment.emars.model.*;
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

    @Autowired
    private MakeService makeService;

    @RequestMapping(value = "/productPage", method = RequestMethod.GET)
    public String productPage() {
        return "product/productPage";
    }

    //TODO
    @RequestMapping(value = "/productDetail/{id}")
    public String productDetail(@PathVariable("id") long id, Model model) {
        ProductInfo product = productService.findProduct(id);
        CopyrightProductInfo copyrightProductInfo = copyrightService.queryProductCopyright(id);
        List<ProductMakeContract> makeContracts = makeService.findProductMakeContracts(id);
        model.addAttribute("product", product);
        model.addAttribute("productCopyright", copyrightProductInfo);
        model.addAttribute("makeContracts", makeContracts);
        return "product/productDetail";
    }

}
