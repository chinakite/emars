package com.ideamoment.emars.sale.controller;

import com.ideamoment.emars.model.SaleContract;
import com.ideamoment.emars.sale.service.ContractService;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yukiwang on 2018/3/19.
 */
@RestController
@RequestMapping("sale")
public class ContractContraller {

    @Autowired
    private ContractService contractService;

    @RequestMapping(value = "/contracts", method = RequestMethod.GET)
    public DataTableSource<SaleContract> contracts(
            int draw,
            int start,
            int length,
            String code,
            String owner,
            String state
    ) {
        SaleContract condition = new SaleContract();
        condition.setCode(code);
        condition.setOwner(owner);
        condition.setState(state);
        Page<SaleContract> contracts = contractService.pageContracts(condition, start, length);
        DataTableSource<SaleContract> dts = convertProductsToDataTableSource(draw, contracts);
        return dts;
    }

    private DataTableSource<SaleContract> convertProductsToDataTableSource(int draw, Page<SaleContract> productsPage) {
        DataTableSource<SaleContract> dts = new DataTableSource<SaleContract>();

        dts.setDraw(draw);
        dts.setRecordsTotal(productsPage.getTotalRecord());
        dts.setRecordsFiltered(productsPage.getTotalRecord());
        dts.setData(productsPage.getData());

        return dts;
    }

}
