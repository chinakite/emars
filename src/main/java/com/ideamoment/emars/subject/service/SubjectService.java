package com.ideamoment.emars.subject.service;

import com.ideamoment.emars.model.Subject;

import java.util.List;

/**
 * Created by yukiwang on 2018/2/16.
 */
public interface SubjectService {

    List<Subject> listTextSubjects(String key);

    Subject createTextSubject(String name, String desc, String ratio);

    Subject findSubject(String id);

    int updateSubject(String id, String name, String desc, String ratio);

    int deleteSubject(String id);

    void batchDeleteSubjects(String[] idArray);

    int upSubject(String id);

    int downSubject(String id);

}
