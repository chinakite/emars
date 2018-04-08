package com.ideamoment.emars.copyright.controller;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.copyright.service.CopyrightService;
import com.ideamoment.emars.model.Copyright;
import com.ideamoment.emars.model.CopyrightContract;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
    public DataTableSource<CopyrightContract> queryCopyrights(
            int draw,
            int start,
            int length,
            String code,
            String owner,
            String auditState
    ) {
        CopyrightContract condition = new CopyrightContract();
//        condition.setCode(code);
//        condition.setOwner(owner);
//        condition.setAuditState(auditState);
        Page<CopyrightContract> copyrights = copyrightService.listCopyrights(condition, start, length);
        DataTableSource<CopyrightContract> dts = convertProductsToDataTableSource(draw, copyrights);
        return dts;
    }

    @RequestMapping(value = "/copyright", method = RequestMethod.GET)
    public JsonData findCopyright(long id) {
        CopyrightContract copyright = copyrightService.findCopyright(id);
        return JsonData.success(copyright);
    }

    @RequestMapping(value = "/createCopyrightContract", method = RequestMethod.POST)
    public JsonData<String> createCopyrightContract(
            @RequestBody CopyrightContract copyrightContract
    ) {
        if(copyrightContract.getId() > 0) {

        }else{
            copyrightService.createCopyrightContract(copyrightContract);
        }

        return JsonData.SUCCESS;
    }

    @RequestMapping(value = "/removeCopyright", method = RequestMethod.POST)
    public JsonData<String> removeCopyright(Long id) {
        String result = copyrightService.removeCopyright(id);

        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    private DataTableSource<CopyrightContract> convertProductsToDataTableSource(int draw, Page<CopyrightContract> productsPage) {
        DataTableSource<CopyrightContract> dts = new DataTableSource<CopyrightContract>();

        dts.setDraw(draw);
        dts.setRecordsTotal(productsPage.getTotalRecord());
        dts.setRecordsFiltered(productsPage.getTotalRecord());
        dts.setData(productsPage.getData());

        return dts;
    }


}
