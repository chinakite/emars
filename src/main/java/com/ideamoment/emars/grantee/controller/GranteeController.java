package com.ideamoment.emars.grantee.controller;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.grantee.service.GranteeService;
import com.ideamoment.emars.model.Grantee;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("system")
public class GranteeController {
    @Autowired
    private GranteeService granteeService;

    @RequestMapping(value = "/grantees", method = RequestMethod.GET)
    public DataTableSource<Grantee> queryGrantees(int draw, int start, int length, String key) {
        Page<Grantee> grantees = granteeService.pageGrantees(start, length);
        DataTableSource<Grantee> dts = convertGranteesToDataTableSource(draw, grantees);
        return dts;
    }

    @RequestMapping(value = "/allGrantees", method = RequestMethod.GET)
    public JsonData<List<Grantee>> allGrantees() {
        List<Grantee> result = granteeService.listGrantees();
        return JsonData.success(result);
    }

    @RequestMapping(value = "/createGrantee", method = RequestMethod.POST)
    public JsonData<Boolean> createGrantee(String name, String desc) {
        String result = granteeService.createGrantee(name, desc);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/deleteGrantee", method = RequestMethod.POST)
    public JsonData<String> deleteGrantee(long id) {
        String result = granteeService.deleteGrantee(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/grantee", method = RequestMethod.GET)
    public JsonData findGrantee(long id) {
        Grantee grantee = granteeService.findGrantee(id);
        return JsonData.success(grantee);
    }

    @RequestMapping(value = "/modifyGrantee", method = RequestMethod.POST)
    public JsonData<Boolean> modifyGrantee(long id, String name, String desc) {
        String result = granteeService.modifyGrantee(id, name, desc);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    private DataTableSource<Grantee> convertGranteesToDataTableSource(int draw, Page<Grantee> granteePage) {
        DataTableSource<Grantee> dts = new DataTableSource<Grantee>();

        dts.setDraw(draw);
        dts.setRecordsTotal(granteePage.getTotalRecord());
        dts.setRecordsFiltered(granteePage.getTotalRecord());
        dts.setData(granteePage.getData());

        return dts;
    }
}
