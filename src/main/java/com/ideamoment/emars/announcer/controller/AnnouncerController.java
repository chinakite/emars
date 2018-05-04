package com.ideamoment.emars.announcer.controller;

import com.ideamoment.emars.announcer.service.AnnouncerService;
import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.model.Announcer;
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
public class AnnouncerController {
    @Autowired
    private AnnouncerService announcerService;

    @RequestMapping(value = "/announcers", method = RequestMethod.GET)
    public DataTableSource<Announcer> queryAnnouncers(int draw, int start, int length, String key) {
        Page<Announcer> announcers = announcerService.listAnnouncer(key, start, length);
        DataTableSource<Announcer> dts = convertAnnouncersToDataTableSource(draw, announcers);
        return dts;
    }

    @RequestMapping(value = "/allAnnouncers", method = RequestMethod.GET)
    public JsonData<List<Announcer>> listAllAnnouncers() {
        List<Announcer> announcers = announcerService.listAllAnnouncers();
        return JsonData.success(announcers);
    }

    @RequestMapping(value = "/announcer", method = RequestMethod.GET)
    public JsonData findAnnouncer(long id) {
        Announcer announcer = announcerService.findAnnouncer(id);
        return JsonData.success(announcer);
    }

    @RequestMapping(value = "/createAnnouncer", method = RequestMethod.POST)
    public JsonData<Boolean> createAnnouncer(String name, String desc, String pseudonym) {
        String result = announcerService.createAnnouncer(name, desc, pseudonym);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/announcer", method = RequestMethod.POST)
    public JsonData<Boolean> updateAnnouncer(long id, String name, String desc, String pseudonym) {
        String result = announcerService.updateAnnouncer(id, name, desc, pseudonym);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/deleteAnnouncer", method = RequestMethod.POST)
    public JsonData<Boolean> deleteAnnouncer(long id) {
        String result = announcerService.deleteAnnouncer(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/batchDeleteAnnouncer", method = RequestMethod.POST)
    public JsonData<Boolean> batchDeleteAnnouncer(long[] ids) {
        String result =  announcerService.batchDeleteAnnouncers(ids);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    private DataTableSource<Announcer> convertAnnouncersToDataTableSource(int draw, Page<Announcer> announcerPage) {
        DataTableSource<Announcer> dts = new DataTableSource<Announcer>();

        dts.setDraw(draw);
        dts.setRecordsTotal(announcerPage.getTotalRecord());
        dts.setRecordsFiltered(announcerPage.getTotalRecord());
        dts.setData(announcerPage.getData());

        return dts;
    }
}
