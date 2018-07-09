package com.ideamoment.emars.product.controller;

import com.ideamoment.emars.copyright.service.CopyrightService;
import com.ideamoment.emars.make.service.MakeService;
import com.ideamoment.emars.model.CopyrightProductInfo;
import com.ideamoment.emars.model.ProductInfo;
import com.ideamoment.emars.model.ProductMakeContract;
import com.ideamoment.emars.model.ProductSaleContract;
import com.ideamoment.emars.product.service.ProductService;
import com.ideamoment.emars.sale.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("mediares")
public class MediaResourcePageController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CopyrightService copyrightService;

    @Autowired
    private MakeService makeService;

    @Autowired
    private SaleService saleService;

    @RequestMapping(value = "/mediaresPage", method = RequestMethod.GET)
    public String mediaresPage() {
        return "product/mediaresPage";
    }

    @RequestMapping(value = "/productDetail/{id}")
    public String productDetail(@PathVariable("id") long id, Model model) {
        ProductInfo product = productService.findProduct(id);
        CopyrightProductInfo copyrightProductInfo = copyrightService.queryProductCopyright(id);
        List<ProductMakeContract> makeContracts = makeService.findProductMakeContracts(id);
        List<ProductSaleContract> saleContracts = saleService.queryProductSale(id);

        model.addAttribute("product", product);
        model.addAttribute("productCopyright", copyrightProductInfo);
        model.addAttribute("makeContracts", makeContracts);
        model.addAttribute("saleContracts", saleContracts);
        return "product/mediaresDetail";
    }
}
