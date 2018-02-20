package com.ideamoment.emars.subject.service;

import com.ideamoment.emars.model.Subject;
import com.ideamoment.emars.utils.Page;

/**
 * Created by yukiwang on 2018/2/16.
 */
public interface SubjectService {

    Page<Subject> listTextSubjects(String key, int offset, int pageSize);

    Subject createTextSubject(String name, String desc, String ratio);

    Subject findSubject(long id);

    int updateSubject(long id, String name, String desc, String ratio);

    int deleteSubject(long id);

    void batchDeleteSubjects(String[] idArray);

    int upSubject(String id);

    int downSubject(String id);

}
