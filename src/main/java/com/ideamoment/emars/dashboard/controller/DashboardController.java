package com.ideamoment.emars.dashboard.controller;

import com.ideamoment.emars.copyright.service.CopyrightService;
import com.ideamoment.emars.dashboard.model.Contract;
import com.ideamoment.emars.dashboard.service.DashboardService;
import com.ideamoment.emars.make.service.MakeService;
import com.ideamoment.emars.model.CopyrightContract;
import com.ideamoment.emars.model.MakeContractQueryVo;
import com.ideamoment.emars.model.ProductInfo;
import com.ideamoment.emars.model.SaleContractQueryVo;
import com.ideamoment.emars.product.service.ProductService;
import com.ideamoment.emars.sale.service.SaleService;
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
    private SaleService saleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private DashboardService dashboardService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String dashboardPage(Model model) {
        CopyrightContract condition1 = new CopyrightContract();
        long countCopyrights = copyrightService.countCopyrights(condition1);

        MakeContractQueryVo condition2 = new MakeContractQueryVo();
        long countMakeContracts = makeService.listMakeContractsCount(condition2);

        SaleContractQueryVo condition3 = new SaleContractQueryVo();
        long countSaleContracts = saleService.countSaleContracts(condition3);

        List<Map> productData1 = productService.selectProductCountWithCopyrightType();

        ProductInfo condition4 = new ProductInfo();
        long countProducts = productService.listProductsCount(condition4);

        List<Map> productData2 = productService.selectProductCountWhitSubject();

        List<Contract> newestContract = dashboardService.newestContract();

        model.addAttribute("countCopyrights", countCopyrights);
        model.addAttribute("countMakeContracts", countMakeContracts);
        model.addAttribute("countSaleContracts", countSaleContracts);
        model.addAttribute("productData1", productData1);
        model.addAttribute("countProducts", countProducts);
        model.addAttribute("productData2", productData2);

        if(newestContract != null || newestContract.size() > 0) {
            int maxLength = 5;
            if(newestContract.size() < 5) {
                maxLength = newestContract.size();
            }
            model.addAttribute("newestContract", newestContract.subList(0, maxLength));
        }

        return "dashboard";
    }
}
