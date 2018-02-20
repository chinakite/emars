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

    private DataTableSource<Subject> convertProductsToDataTableSource(int draw, Page<Subject> productsPage) {
        DataTableSource<Subject> dts = new DataTableSource<Subject>();

        dts.setDraw(draw);
        dts.setRecordsTotal(productsPage.getTotalRecord());
        dts.setRecordsFiltered(productsPage.getTotalRecord());
        dts.setData(productsPage.getData());

        return dts;
    }
}
