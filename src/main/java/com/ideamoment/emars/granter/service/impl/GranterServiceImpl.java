package com.ideamoment.emars.granter.service.impl;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.copyright.dao.CopyrightMapper;
import com.ideamoment.emars.granter.dao.GranterMapper;
import com.ideamoment.emars.granter.service.GranterService;
import com.ideamoment.emars.model.Granter;
import com.ideamoment.emars.utils.Page;
import com.ideamoment.emars.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class GranterServiceImpl implements GranterService {
    @Autowired
    private GranterMapper granterMapper;

    @Autowired
    private CopyrightMapper copyrightMapper;

    @Override
    @Transactional
    public Page<Granter> pageGranters(int offset, int pageSize) {
        long count = granterMapper.countGranter();
        int currentPage = offset/pageSize + 1;

        List<Granter> granters = granterMapper.pageGranters(offset, pageSize);

        Page<Granter> result = new Page<Granter>();
        result.setCurrentPage(currentPage);
        result.setData(granters);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);

        return result;
    }

    @Override
    @Transactional
    public List<Granter> listGranters() {
        return granterMapper.listGranters();
    }

    @Override
    @Transactional
    public String createGranter(String name, String contact, String phone, String desc) {
        Granter existsGranter = granterMapper.findGranterByName(name, null);

        if(existsGranter != null) {
            return ErrorCode.GRANTER_EXISTS;
        }
        Granter granter = new Granter();
        granter.setName(name);
        granter.setContact(contact);
        granter.setPhone(phone);
        granter.setDesc(desc);
        granter.setCreateTime(new Date());
        granter.setCreator(UserContext.getUserId());

        boolean result = granterMapper.insertGranter(granter);
        return resultString(result);
    }

    @Override
    @Transactional
    public String deleteGranter(long id) {
        long r = copyrightMapper.countCopyrightsByGranter(id);
        if(r > 0) {
            return ErrorCode.GRANTER_CANNOT_DELETE;
        }
        boolean result = granterMapper.deleteGranter(id);
        return resultString(result);
    }

    @Override
    @Transactional
    public Granter findGranter(long id) {
        return granterMapper.findGranter(id);
    }

    @Override
    @Transactional
    public String modifyGranter(long id, String name, String contact, String phone, String desc) {
        Granter granter = granterMapper.findGranter(id);

        if(granter == null) {
            return ErrorCode.GRANTER_NOT_EXISTS;
        }
        Granter existsGranter = granterMapper.findGranterByName(name, id);
        if(existsGranter != null) {
            return ErrorCode.GRANTER_EXISTS;
        }

        granter.setName(name);
        granter.setContact(contact);
        granter.setPhone(phone);
        granter.setModifyTime(new Date());
        granter.setModifier(UserContext.getUserId());
        granter.setDesc(desc);

        boolean result = granterMapper.updateGranter(granter);
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
