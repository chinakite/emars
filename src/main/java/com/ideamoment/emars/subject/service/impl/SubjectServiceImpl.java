package com.ideamoment.emars.subject.service.impl;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.model.Subject;
import com.ideamoment.emars.model.User;
import com.ideamoment.emars.model.enumeration.ProductType;
import com.ideamoment.emars.subject.dao.SubjectMapper;
import com.ideamoment.emars.subject.service.SubjectService;
import com.ideamoment.emars.utils.Page;
import com.ideamoment.emars.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by yukiwang on 2018/2/16.
 */
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    @Transactional
    public Page<Subject> listTextSubjects(String key, int offset, int pageSize) {
        String type = ProductType.TEXT;

        long count = subjectMapper.listSubjectsCount(type, key);
        int currentPage = offset/pageSize + 1;

        List<Subject> subjects = subjectMapper.listSubjects(type, key, offset, pageSize);

        Page<Subject> result = new Page<Subject>();
        result.setCurrentPage(currentPage);
        result.setData(subjects);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);

        return result;
    }

    @Override
    @Transactional
    public String createTextSubject(String name, String desc, String ratio) {
        List<Subject> existsSubjects = subjectMapper.querySubject(ProductType.TEXT, name, false);

        if(existsSubjects.size() > 0) {
            return ErrorCode.SUBJECT_EXISTS;
        }
        int maxOrder = subjectMapper.queryMaxOrder(ProductType.TEXT);
        Subject subject = new Subject();
        subject.setName(name);
        subject.setDesc(desc);
        subject.setRatio(ratio);
        subject.setOrder(maxOrder + 1);
        subject.setCreateTime(new Date());
        subject.setCreator(UserContext.getUserId());

        boolean result = subjectMapper.insertSubject(subject);

        if(result) {
            return SuccessCode.SUCCESS;
        }else{
            return ErrorCode.UNKNOWN_ERROR;
        }
    }

    @Override
    @Transactional
    public Subject findSubject(long id) {
        return  subjectMapper.findSubject(id);
    }

    @Override
    @Transactional
    public String updateSubject(long id, String name, String desc, String ratio) {
        Subject subject = subjectMapper.findSubject(id);

        if(subject == null) {
            return ErrorCode.SUBJECT_NOT_EXISTS;
        }
        List<Subject> existsSubjects = subjectMapper.querySubjectExceptSelf(ProductType.TEXT, name, id);

        if(existsSubjects.size() > 0) {
            return ErrorCode.SUBJECT_EXISTS;
        }else{
            subject.setName(name);
            subject.setDesc(desc);
            subject.setRatio(ratio);
            subject.setModifier(UserContext.getUserId());
            subject.setModifyTime(new Date());
            boolean result = subjectMapper.updateSubject(subject);
            if(result) {
                return SuccessCode.SUCCESS;
            }else{
                return ErrorCode.UNKNOWN_ERROR;
            }
        }

    }

    @Override
    @Transactional
    public String deleteSubject(long id) {

        boolean exists = subjectMapper.checkExistsProductsOfSubject(id);
        if(exists) {
            return ErrorCode.SUBJECT_NOT_EXISTS;
        }else{
            boolean result = subjectMapper.deleteSubject(id);
            if(result) {
                return SuccessCode.SUCCESS;
            }else{
                return ErrorCode.UNKNOWN_ERROR;
            }
        }
    }

    @Override
    @Transactional
    public String batchDeleteSubjects(long[] idArray) {
        boolean result = subjectMapper.batchDeleteSubjects(idArray);
        if(result) {
            return SuccessCode.SUCCESS;
        }else{
            return ErrorCode.UNKNOWN_ERROR;
        }
    }

    @Override
    @Transactional
    public String upSubject(long id) {
        Subject subject = subjectMapper.findSubject(id);

        if(subject == null) {
            return ErrorCode.SUBJECT_NOT_EXISTS;
        }
        int order = subject.getOrder();
        Subject prevSubject = subjectMapper.querySubjectByOrder(ProductType.TEXT, order - 1);

        prevSubject.setOrder(order);
        prevSubject.setModifier(UserContext.getUserId());
        prevSubject.setModifyTime(new Date());
        subject.setOrder(order - 1);
        subject.setModifier(UserContext.getUserId());
        subject.setModifyTime(new Date());

        boolean result1 = subjectMapper.updateSubject(prevSubject);
        boolean result2 = subjectMapper.updateSubject(subject);
        if(result1 && result2) {
            return SuccessCode.SUCCESS;
        }else{
            return ErrorCode.UNKNOWN_ERROR;
        }

    }

    @Override
    @Transactional
    public String downSubject(long id) {
        Subject subject = subjectMapper.findSubject(id);

        if(subject == null) {
            return ErrorCode.SUBJECT_NOT_EXISTS;
        }
            int order = subject.getOrder();
        Subject nextSubject = subjectMapper.querySubjectByOrder(ProductType.TEXT, order+1);

        nextSubject.setOrder(order);
        nextSubject.setModifier(UserContext.getUserId());
        nextSubject.setModifyTime(new Date());
        subject.setOrder(order + 1);
        subject.setModifier(UserContext.getUserId());
        subject.setModifyTime(new Date());

        boolean result1 = subjectMapper.updateSubject(nextSubject);
        boolean result2 = subjectMapper.updateSubject(subject);
        if(result1 && result2) {
            return SuccessCode.SUCCESS;
        }else{
            return ErrorCode.UNKNOWN_ERROR;
        }

    }
}
