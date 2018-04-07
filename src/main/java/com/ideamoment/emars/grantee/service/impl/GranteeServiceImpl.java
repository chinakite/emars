package com.ideamoment.emars.grantee.service.impl;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.copyright.dao.CopyrightMapper;
import com.ideamoment.emars.grantee.dao.GranteeMapper;
import com.ideamoment.emars.grantee.service.GranteeService;
import com.ideamoment.emars.model.Grantee;
import com.ideamoment.emars.utils.Page;
import com.ideamoment.emars.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class GranteeServiceImpl implements GranteeService {

    @Autowired
    private GranteeMapper granteeMapper;

    @Autowired
    private CopyrightMapper copyrightMapper;

    @Override
    @Transactional
    public Page<Grantee> pageGrantees(int offset, int pageSize) {
        long count = granteeMapper.countGrantee();
        int currentPage = offset/pageSize + 1;

        List<Grantee> grantees = granteeMapper.pageGrantees(offset, pageSize);

        Page<Grantee> result = new Page<Grantee>();
        result.setCurrentPage(currentPage);
        result.setData(grantees);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);

        return result;
    }

    @Override
    @Transactional
    public List<Grantee> listGrantees() {
        return granteeMapper.listGrantees();
    }

    @Override
    @Transactional
    public String createGrantee(String name, String desc) {
        Grantee existsGrantee = granteeMapper.findGranteeByName(name, null);

        if(existsGrantee != null) {
            return ErrorCode.GRANTEE_EXISTS;
        }
        Grantee grantee = new Grantee();
        grantee.setName(name);
        grantee.setDesc(desc);
        grantee.setCreateTime(new Date());
        grantee.setCreator(UserContext.getUserId());

        boolean result = granteeMapper.insertGrantee(grantee);
        return resultString(result);
    }

    @Override
    @Transactional
    public String deleteGrantee(long id) {
        long r = copyrightMapper.countCopyrightsByGrantee(id);
        if(r > 0) {
            return ErrorCode.GRANTEE_CANNOT_DELETE;
        }
        boolean result = granteeMapper.deleteGrantee(id);
        return resultString(result);
    }

    @Override
    @Transactional
    public Grantee findGrantee(long id) {
        return granteeMapper.findGrantee(id);
    }

    @Override
    @Transactional
    public String modifyGrantee(long id, String name, String desc) {
        Grantee grantee = granteeMapper.findGrantee(id);

        if(grantee == null) {
            return ErrorCode.AUTHOR_NOT_EXISTS;
        }
        Grantee existsGrantee = granteeMapper.findGranteeByName(name, id);
        if(existsGrantee != null) {
            return ErrorCode.AUTHOR_EXISTS;
        }

        grantee.setName(name);
        grantee.setModifyTime(new Date());
        grantee.setModifier(UserContext.getUserId());
        grantee.setDesc(desc);

        boolean result = granteeMapper.updateGrantee(grantee);
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
