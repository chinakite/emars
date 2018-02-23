package com.ideamoment.emars.subject.controller;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
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

    @RequestMapping(value="/notExistsSubjects", method = RequestMethod.GET)
    public boolean notExistsSubjects(String name, Long id) {
        return subjectService.notExistsSubjects(name, id);
    }

    @RequestMapping(value="/textSubject", method = RequestMethod.POST)
    public JsonData<Boolean> createTextSubject(String name, String desc, String ratio) {
        String result = subjectService.createTextSubject(name, desc, ratio);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value="/subject", method = RequestMethod.POST)
    public JsonData<Boolean> updateSubject(long id, String name, String desc, String ratio) {
        String result = subjectService.updateSubject(id, name, desc, ratio);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value="/deleteSubject", method = RequestMethod.POST)
    public JsonData<Boolean> deleteSubject(long id) {
        String result = subjectService.deleteSubject(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value="/batchDeleteSubject", method = RequestMethod.POST)
    public JsonData<Boolean> batchDeleteSubject(long[] ids) {
        String result = subjectService.batchDeleteSubjects(ids);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value="/upSubject", method = RequestMethod.POST)
    public JsonData<Boolean> upSubject(long id) {
        String result = subjectService.upSubject(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value="/downSubject", method = RequestMethod.POST)
    public JsonData<Boolean> downSubject(long id) {
        String result = subjectService.downSubject(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
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
