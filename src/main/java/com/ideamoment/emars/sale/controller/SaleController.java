package com.ideamoment.emars.sale.controller;

import com.ideamoment.emars.model.ProductInfo;
import com.ideamoment.emars.model.Sale;
import com.ideamoment.emars.model.SaleContractQueryVo;
import com.ideamoment.emars.sale.service.SaleService;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.Page;
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

    @RequestMapping(value = "/saleContracts", method = RequestMethod.GET)
    public DataTableSource<Sale> saleContracts(
            int draw,
            int start,
            int length,
            String productName,
            String code,
            String targetType
    ) {
        SaleContractQueryVo condition = new SaleContractQueryVo();
        condition.setProductName(productName);
        condition.setCode(code);
        condition.setTargetType(targetType);
        Page<Sale> saleContractPage = saleService.pageSaleContracts(condition, start, length);
        DataTableSource<Sale> dts = new DataTableSource().convertToDataTableSource(draw, start, length, saleContractPage.getData(), saleContractPage.getTotalPage());
        return dts;
    }
}
