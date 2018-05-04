package com.ideamoment.emars.announcer.service.impl;

import com.ideamoment.emars.announcer.dao.AnnouncerMapper;
import com.ideamoment.emars.announcer.service.AnnouncerService;
import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.model.Announcer;
import com.ideamoment.emars.utils.Page;
import com.ideamoment.emars.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AnnouncerServiceImpl implements AnnouncerService {

    @Autowired
    private AnnouncerMapper announcerMapper;

    @Override
    @Transactional
    public Page<Announcer> listAnnouncer(String key, int offset, int pageSize) {
        long count = announcerMapper.listAnnouncerCount(key);
        int currentPage = offset/pageSize + 1;

        List<Announcer> announcers = announcerMapper.listAnnouncer(key, offset, pageSize);

        Page<Announcer> result = new Page<Announcer>();
        result.setCurrentPage(currentPage);
        result.setData(announcers);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);

        return result;
    }

    @Override
    @Transactional
    public Announcer findAnnouncer(long id) {
        return announcerMapper.findAnnouncer(id);
    }

    @Override
    @Transactional
    public Announcer findAnnouncerByName(String name) {
        Announcer announcer = announcerMapper.queryAnnouncer(name, null);
        return announcer;
    }

    @Override
    @Transactional
    public String createAnnouncer(String name, String desc, String pseudonym) {
        Announcer existsAnnouncer = announcerMapper.queryAnnouncer(name, null);

        if(existsAnnouncer != null) {
            return ErrorCode.ANNOUNCER_EXISTS;
        }
        Announcer announcer = new Announcer();
        announcer.setName(name);
        announcer.setPseudonym(pseudonym);
        announcer.setDesc(desc);
        announcer.setCreateTime(new Date());
        announcer.setCreator(UserContext.getUserId());

        boolean result = announcerMapper.insertAnnouncer(announcer);
        return resultString(result);
    }

    @Override
    @Transactional
    public String updateAnnouncer(long id, String name, String desc, String pseudonym) {
        Announcer announcer = announcerMapper.findAnnouncer(id);

        if(announcer == null) {
            return ErrorCode.ANNOUNCER_NOT_EXISTS;
        }
        Announcer existsAnnouncer = announcerMapper.queryAnnouncer(name, id);
        if(existsAnnouncer != null) {
            return ErrorCode.ANNOUNCER_EXISTS;
        }

        existsAnnouncer.setName(name);
        existsAnnouncer.setPseudonym(pseudonym);
        existsAnnouncer.setModifyTime(new Date());
        existsAnnouncer.setModifier(UserContext.getUserId());
        existsAnnouncer.setDesc(desc);

        boolean result = announcerMapper.updateAnnouncer(existsAnnouncer);
        return resultString(result);
    }

    @Override
    @Transactional
    public String deleteAnnouncer(long id) {
        boolean r = announcerMapper.existsProductsByAnnouncer(id);
        if(r) {
            return ErrorCode.ANNOUNCER_CANNOT_DELETE;
        }
        boolean result = announcerMapper.deleteAnnouncer(id);
        return resultString(result);
    }

    @Override
    @Transactional
    public List<Announcer> listAllAnnouncers() {
        return announcerMapper.listAllAnnouncer();
    }

    @Override
    public String batchDeleteAnnouncers(long[] idArray) {
        boolean result = announcerMapper.batchDeleteAnnouncers(idArray);
        return resultString(result);
    }

    private String resultString(boolean result) {
        if(result) {
            return SuccessCode.SUCCESS;
        }else{
            return ErrorCode.UNKNOWN_ERROR;
        }
    }
}
