package com.ideamoment.emars.subject.controller;

import com.ideamoment.emars.model.Subject;
import com.ideamoment.emars.subject.service.SubjectService;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yukiwang on 2018/2/20.
 */
@RestController
@RequestMapping("system")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "/subjects", method = RequestMethod.GET)
    public DataTableSource<Subject> querySubjects(int draw, int start, int length, String key) {
        Page<Subject> subjects = subjectService.listTextSubjects(key, start, length);
        DataTableSource<Subject> dts = convertProductsToDataTableSource(draw, subjects);
        return dts;
    }

    @RequestMapping(value="/subject", method = RequestMethod.GET)
    public JsonData findSubject(long id) {
        Subject subject = subjectService.findSubject(id);
        return JsonData.success(subject);
    }

    @RequestMapping(value="/textSubject", method = RequestMethod.POST)
    public JsonData createTextSubject(String name, String desc, String ratio) {
        Subject subject = subjectService.createTextSubject(name, desc, ratio);
        return JsonData.SUCCESS;
    }

    @RequestMapping(value="/subject", method = RequestMethod.POST)
    public JsonData updateSubject(long id, String name, String desc, String ratio) {
        int r = subjectService.updateSubject(id, name, desc, ratio);
        return JsonData.SUCCESS;
    }

    @RequestMapping(value="/subject", method = RequestMethod.DELETE)
    public JsonData deleteSubject(long id) {
        int r = subjectService.deleteSubject(id);
        return JsonData.SUCCESS;
    }

    @RequestMapping(value="/batchDeleteSubject", method = RequestMethod.DELETE)
    public JsonData batchDeleteSubject(String ids) {
        String[] idArray = ids.split(",");

        subjectService.batchDeleteSubjects(idArray);

        return JsonData.SUCCESS;
    }

    @RequestMapping(value="/upSubject", method = RequestMethod.POST)
    public JsonData upSubject(String id) {
        int r = subjectService.upSubject(id);
        return JsonData.SUCCESS;
    }

    @RequestMapping(value="/downSubject", method = RequestMethod.POST)
    public JsonData downSubject(String id) {
        int r = subjectService.downSubject(id);
        return JsonData.SUCCESS;
    }

    private DataTableSource<Subject> convertProductsToDataTableSource(int draw, Page<Subject> productsPage) {
        DataTableSource<Subject> dts = new DataTableSource<Subject>();

        dts.setDraw(draw);
        dts.setRecordsTotal(productsPage.getTotalRecord());
        dts.setRecordsFiltered(productsPage.getTotalRecord());
        dts.setData(productsPage.getData());

        return dts;
    }
}
