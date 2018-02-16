package com.ideamoment.emars.subject.service.impl;

import com.ideamoment.emars.model.Subject;
import com.ideamoment.emars.model.User;
import com.ideamoment.emars.model.enumeration.ProductType;
import com.ideamoment.emars.subject.dao.SubjectMapper;
import com.ideamoment.emars.subject.service.SubjectService;
import com.ideamoment.emars.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by yukiwang on 2018/2/16.
 */
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public List<Subject> listTextSubjects(String key) {
        String type = ProductType.TEXT;
        return subjectMapper.listSubjects(type, key);
    }

    @Override
    public Subject createTextSubject(String name, String desc, String ratio) {
//        UserContext uc = UserContext.getCurrentContext();
//        User user = (User) uc.getContextAttribute(UserContext.SESSION_USER);
//
//        List<Subject> existsSubjects = subjectMapper.querySubject(ProductType.TEXT, name, false);
//
//        if(existsSubjects.size() > 0) {
//            throw duplicateException(name);
//        }else{
//            int maxOrder = subjectMapper.queryMaxOrder(ProductType.TEXT);
//            Subject subject = new Subject();
//            subject.setName(name);
//            subject.setDesc(desc);
//            subject.setRatio(ratio);
//            subject.setOrder(maxOrder + 1);
//            subject.setCreateTime(new Date());
//            subject.setCreator(user.getId());
//
//            subjectMapper.insertSubject(subject);
//
//            return subject;
//        }
        return null;
    }

    @Override
    public Subject findSubject(String id) {
        return  subjectMapper.findSubject(id);
    }

    @Override
    public int updateSubject(String id, String name, String desc, String ratio) {
//        UserContext uc = UserContext.getCurrentContext();
//        User user = (User) uc.getContextAttribute(UserContext.SESSION_USER);
//        Subject subject = subjectMapper.findSubject(id);
//
//        if(subject == null) {
//            throw dataNotFoundException(id);
//        }else{
//            List<Subject> existsSubjects = subjectMapper.querySubjectExceptSelf(ProductType.TEXT, name, id);
//
//            if(existsSubjects.size() > 0) {
//                throw duplicateException(name);
//            }else{
//                subject.setName(name);
//                subject.setDesc(desc);
//                subject.setRatio(ratio);
//                subject.setModifier(user.getId());
//                subject.setModifyTime(new Date());
//                subjectMapper.updateSubject(subject);
//                return 1;
//            }
//        }
        return 0;
    }

    @Override
    public int deleteSubject(String id) {

//        boolean exists = subjectMapper.checkExistsProductsOfSubject(id);
//        if(exists) {
//            throw new IdeaDataException(IdeaDataExceptionCode.CANT_DELETE_DUE_ASSO_DATA, String.format("Can not delete subject[%s] due having products.", id));
//        }else{
//            subjectMapper.deleteSubject(id);
//            return 1;
//        }
        return 0;
    }

    @Override
    public void batchDeleteSubjects(String[] idArray) {
        subjectMapper.batchDeleteSubjects(idArray);
    }

    @Override
    public int upSubject(String id) {
//        Subject subject = subjectMapper.findSubject(id);
//
//        if(subject == null) {
//            throw dataNotFoundException(id);
//        }else{
//            int order = subject.getOrder();
//            Subject prevSubject = subjectMapper.querySubjectByOrder(ProductType.TEXT, order-1);
//
//            prevSubject.setOrder(order);
//            subject.setOrder(order-1);
//
//            IdeaJdbc.update(prevSubject);
//            subjectMapper.updateSubject(subject);
//
//            return 2;
//        }
        return 0;
    }

    @Override
    public int downSubject(String id) {
//        Subject subject = subjectMapper.findSubject(Subject.class, id);
//
//        if(subject == null) {
//            throw dataNotFoundException(id);
//        }else{
//            int order = subject.getOrder();
//            Subject nextSubject = subjectMapper.querySubjectByOrder(ProductType.TEXT, order+1);
//
//            nextSubject.setOrder(order);
//            subject.setOrder(order+1);
//
//            IdeaJdbc.update(nextSubject);
//            subjectMapper.updateSubject(subject);
//
//            return 2;
//        }
        return 0;
    }
}
