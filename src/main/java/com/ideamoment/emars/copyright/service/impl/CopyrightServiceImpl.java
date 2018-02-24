package com.ideamoment.emars.copyright.service.impl;

import com.ideamoment.emars.copyright.dao.CopyrightMapper;
import com.ideamoment.emars.copyright.service.CopyrightService;
import com.ideamoment.emars.model.Copyright;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yukiwang on 2018/2/24.
 */
@Service
public class CopyrightServiceImpl implements CopyrightService {

    @Autowired
    private CopyrightMapper copyrightMapper;

    @Override
    @Transactional
    public Page<Copyright> listCopyrights(Copyright condition, int offset, int pageSize) {
        long count = copyrightMapper.listCopyrightsCount(condition);
        int currentPage = offset/pageSize + 1;

        List<Copyright> authors = copyrightMapper.listCopyrights(condition, offset, pageSize);

        Page<Copyright> result = new Page<Copyright>();
        result.setCurrentPage(currentPage);
        result.setData(authors);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);

        return result;
    }
}
