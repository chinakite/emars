package com.ideamoment.emars.maker.controller;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.maker.service.MakerService;
import com.ideamoment.emars.model.Maker;
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
public class MakerController {
    @Autowired
    private MakerService makerService;

    @RequestMapping(value = "/makers", method = RequestMethod.GET)
    public DataTableSource<Maker> queryMakers(int draw, int start, int length, String key) {
        Page<Maker> makers = makerService.pageMakers(start, length);
        DataTableSource<Maker> dts = convertMakersToDataTableSource(draw, makers);
        return dts;
    }

    @RequestMapping(value = "/allMakers", method = RequestMethod.GET)
    public JsonData<List<Maker>> allMakers() {
        List<Maker> result = makerService.listMakers();
        return JsonData.success(result);
    }

    @RequestMapping(value = "/createMaker", method = RequestMethod.POST)
    public JsonData<Boolean> createMaker(String name, String contact, String phone, String desc) {
        String result = makerService.createMaker(name, contact, phone, desc);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/deleteMaker", method = RequestMethod.POST)
    public JsonData<Boolean> deleteMaker(long id) {
        String result = makerService.deleteMaker(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/maker", method = RequestMethod.GET)
    public JsonData findMaker(long id) {
        Maker maker = makerService.findMaker(id);
        return JsonData.success(maker);
    }

    @RequestMapping(value = "/modifyMaker", method = RequestMethod.POST)
    public JsonData<Boolean> modifyMaker(long id, String name, String contact, String phone, String desc) {
        String result = makerService.modifyMaker(id, name, contact, phone, desc);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    private DataTableSource<Maker> convertMakersToDataTableSource(int draw, Page<Maker> makerPage) {
        DataTableSource<Maker> dts = new DataTableSource<Maker>();

        dts.setDraw(draw);
        dts.setRecordsTotal(makerPage.getTotalRecord());
        dts.setRecordsFiltered(makerPage.getTotalRecord());
        dts.setData(makerPage.getData());

        return dts;
    }
}
