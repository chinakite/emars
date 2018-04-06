package com.ideamoment.emars.granter.controller;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.granter.service.GranterService;
import com.ideamoment.emars.model.Granter;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("system")
public class GranterController {
    @Autowired
    private GranterService granterService;

    @RequestMapping(value = "/granters", method = RequestMethod.GET)
    public DataTableSource<Granter> queryGranters(int draw, int start, int length, String key) {
        Page<Granter> granters = granterService.listGranters(start, length);
        DataTableSource<Granter> dts = convertGrantersToDataTableSource(draw, granters);
        return dts;
    }

    @RequestMapping(value = "/createGranter", method = RequestMethod.POST)
    public JsonData<Boolean> createGranter(String name, String contact, String phone, String desc) {
        String result = granterService.createGranter(name, contact, phone, desc);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/deleteGranter", method = RequestMethod.POST)
    public JsonData<Boolean> deleteGranter(long id) {
        String result = granterService.deleteGranter(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/granter", method = RequestMethod.GET)
    public JsonData findGranter(long id) {
        Granter granter = granterService.findGranter(id);
        return JsonData.success(granter);
    }

    @RequestMapping(value = "/modifyGranter", method = RequestMethod.POST)
    public JsonData<Boolean> modifyGranter(long id, String name, String contact, String phone, String desc) {
        String result = granterService.modifyGranter(id, name, contact, phone, desc);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    private DataTableSource<Granter> convertGrantersToDataTableSource(int draw, Page<Granter> granterPage) {
        DataTableSource<Granter> dts = new DataTableSource<Granter>();

        dts.setDraw(draw);
        dts.setRecordsTotal(granterPage.getTotalRecord());
        dts.setRecordsFiltered(granterPage.getTotalRecord());
        dts.setData(granterPage.getData());

        return dts;
    }
}
