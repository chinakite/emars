package com.ideamoment.emars.dashboard.controller;

import com.ideamoment.emars.copyright.service.CopyrightService;
import com.ideamoment.emars.make.service.MakeService;
import com.ideamoment.emars.model.CopyrightContract;
import com.ideamoment.emars.model.MakeContractQueryVo;
import com.ideamoment.emars.model.ProductInfo;
import com.ideamoment.emars.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {
    private final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private CopyrightService copyrightService;
    @Autowired
    private MakeService makeService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String dashboardPage(Model model) {
        CopyrightContract condition1 = new CopyrightContract();
        long countCopyrights = copyrightService.countCopyrights(condition1);

        MakeContractQueryVo condition2 = new MakeContractQueryVo();
        long countMakeContracts = makeService.listMakeContractsCount(condition2);

        //TODO 营销合同

        List<Map> productData1 = productService.selectProductCountWithCopyrightType();

        ProductInfo condition3 = new ProductInfo();
        long countProducts = productService.listProductsCount(condition3);

        List<Map> productData2 = productService.selectProductCountWhitSubject();

        model.addAttribute("countCopyrights", countCopyrights);
        model.addAttribute("countMakeContracts", countMakeContracts);
        model.addAttribute("productData1", productData1);
        model.addAttribute("countProducts", countProducts);
        model.addAttribute("productData2", productData2);

        return "dashboard";
    }
}
