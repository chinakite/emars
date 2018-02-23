package com.ideamoment.emars.subject.service;

import com.ideamoment.emars.model.Subject;
import com.ideamoment.emars.utils.Page;

/**
 * Created by yukiwang on 2018/2/16.
 */
public interface SubjectService {

    Page<Subject> listTextSubjects(String key, int offset, int pageSize);

    String createTextSubject(String name, String desc, String ratio);

    boolean notExistsSubjects(String name, Long id);

    Subject findSubject(long id);

    String updateSubject(long id, String name, String desc, String ratio);

    String deleteSubject(long id);

    String batchDeleteSubjects(long[] idArray);

    String upSubject(long id);

    String downSubject(long id);

}
