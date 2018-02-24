package com.ideamoment.emars.copyright.controller;

import com.ideamoment.emars.copyright.service.CopyrightService;
import com.ideamoment.emars.model.Copyright;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yukiwang on 2018/2/24.
 */
@RestController
@RequestMapping("copyright")
public class CopyrightController {

    @Autowired
    private CopyrightService copyrightService;

    @RequestMapping(value = "/copyrights", method = RequestMethod.GET)
    public DataTableSource<Copyright> queryCopyrights(
            int draw,
            int start,
            int length,
            String code,
            String owner,
            String auditState
    ) {
        Copyright condition = new Copyright();
        condition.setCode(code);
        condition.setOwner(owner);
        condition.setAuditState(auditState);
        Page<Copyright> copyrights = copyrightService.listCopyrights(condition, start, length);
        DataTableSource<Copyright> dts = convertProductsToDataTableSource(draw, copyrights);
        return dts;
    }

    private DataTableSource<Copyright> convertProductsToDataTableSource(int draw, Page<Copyright> productsPage) {
        DataTableSource<Copyright> dts = new DataTableSource<Copyright>();

        dts.setDraw(draw);
        dts.setRecordsTotal(productsPage.getTotalRecord());
        dts.setRecordsFiltered(productsPage.getTotalRecord());
        dts.setData(productsPage.getData());

        return dts;
    }
}
