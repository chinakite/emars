package com.ideamoment.emars.product.controller;

import com.ideamoment.emars.copyright.service.CopyrightService;
import com.ideamoment.emars.model.CopyrightProductInfo;
import com.ideamoment.emars.model.ProductInfo;
import com.ideamoment.emars.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("mediares")
public class MediaResourcePageController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CopyrightService copyrightService;

    @RequestMapping(value = "/mediaresPage", method = RequestMethod.GET)
    public String mediaresPage() {
        return "product/mediaresPage";
    }

    @RequestMapping(value = "/productDetail/{id}")
    public String productDetail(@PathVariable("id") long id, Model model) {
        ProductInfo product = productService.findProduct(id);
        CopyrightProductInfo copyrightProductInfo = copyrightService.queryProductCopyright(id);
        model.addAttribute("product", product);
        model.addAttribute("productCopyright", copyrightProductInfo);
        return "product/mediaresDetail";
    }
}
