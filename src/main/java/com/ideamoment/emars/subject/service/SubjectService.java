package com.ideamoment.emars.subject.service;

import com.ideamoment.emars.model.Subject;
import com.ideamoment.emars.utils.Page;

/**
 * Created by yukiwang on 2018/2/16.
 */
public interface SubjectService {

    Page<Subject> listTextSubjects(String key, int currentPage, int pageSize);

    Subject createTextSubject(String name, String desc, String ratio);

    Subject findSubject(String id);

    int updateSubject(String id, String name, String desc, String ratio);

    int deleteSubject(String id);

    void batchDeleteSubjects(String[] idArray);

    int upSubject(String id);

    int downSubject(String id);

}
