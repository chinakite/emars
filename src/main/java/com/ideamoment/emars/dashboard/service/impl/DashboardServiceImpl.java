package com.ideamoment.emars.dashboard.service.impl;

import com.ideamoment.emars.copyright.dao.CopyrightMapper;
import com.ideamoment.emars.dashboard.service.DashboardService;
import com.ideamoment.emars.make.dao.MakeContractMapper;
import com.ideamoment.emars.model.CopyrightContract;
import com.ideamoment.emars.model.MakeContract;
import com.ideamoment.emars.model.MakeContractQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yukiwang on 2018/6/19.
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private CopyrightMapper copyrightMapper;

    @Autowired
    private MakeContractMapper makeContractMapper;

    @Override
    public List<Map> newestContract() {
        CopyrightContract condition1 = new CopyrightContract();
        List<CopyrightContract> copyrightContractList = copyrightMapper.listCopyrights(condition1, 0, 5);

        MakeContractQueryVo condition2 = new MakeContractQueryVo();
        List<MakeContract> makeContractList = makeContractMapper.listMakeContracts(condition2, 0, 5);

        //TODO 营销合同

        List<Map> ret = new ArrayList<>();

        for(CopyrightContract copyrightContract : copyrightContractList) {
            for(MakeContract makeContract : makeContractList) {
            }
        }


        return null;
    }
}
