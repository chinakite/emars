package com.ideamoment.emars.dashboard.service.impl;

import com.ideamoment.emars.copyright.dao.CopyrightMapper;
import com.ideamoment.emars.dashboard.model.Contract;
import com.ideamoment.emars.dashboard.service.DashboardService;
import com.ideamoment.emars.grantee.dao.GranteeMapper;
import com.ideamoment.emars.make.dao.MakeContractMapper;
import com.ideamoment.emars.model.*;
import com.ideamoment.emars.sale.dao.SaleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by yukiwang on 2018/6/19.
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private CopyrightMapper copyrightMapper;

    @Autowired
    private MakeContractMapper makeContractMapper;

    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private GranteeMapper granteeMapper;

    @Override
    public List<Contract> newestContract() {
        CopyrightContract condition1 = new CopyrightContract();
        List<CopyrightContract> copyrightContractList = copyrightMapper.listCopyrights(condition1, 0, 5);

        MakeContractQueryVo condition2 = new MakeContractQueryVo();
        List<MakeContract> makeContractList = makeContractMapper.listMakeContracts(condition2, 0, 5);

        SaleContractQueryVo condition3 = new SaleContractQueryVo();
        List<Sale> saleList = saleMapper.pageSaleContracts(condition3, 0, 5);
        for(Sale sale : saleList) {
            Grantee grantee = granteeMapper.findGrantee(sale.getGranterId());
            sale.setGranter(grantee);
        }

        List<Contract> contracts = new ArrayList<>();

        contracts = dashboardContractList(contracts, copyrightContractList);
        contracts = dashboardContractList(contracts, makeContractList);
        contracts = dashboardContractList(contracts, saleList);
        Collections.sort(contracts, new Comparator<Contract>() {
            @Override
            public int compare(Contract o1, Contract o2) {
                return o2.getCreateTime().compareTo(o1.getCreateTime());
            }
        });
        return contracts;
    }

    private List<Contract> dashboardContractList(List<Contract> contracts, List<?> objList) {
        for(Object obj : objList) {
            Contract contract = new Contract();
            String objClassName = obj.getClass().getName();
            if (objClassName.equals(CopyrightContract.class.getName())) {
                CopyrightContract objContract = (CopyrightContract) obj;
                contract.setType("1");
                contract.setId(objContract.getId());
                contract.setCode(objContract.getContractCode());
                contract.setCreateTime(objContract.getCreateTime());
                contract.setTitle(objContract.getGranter());
            } else if (objClassName.equals(MakeContract.class.getName())) {
                MakeContract objContract = (MakeContract) obj;
                contract.setType("2");
                contract.setId(objContract.getId());
                contract.setCode(objContract.getCode());
                contract.setCreateTime(objContract.getCreateTime());
                contract.setTitle(objContract.getMaker());
            } else if (objClassName.equals(Sale.class.getName())) {
                Sale objContract = (Sale) obj;
                contract.setType("3");
                contract.setId(objContract.getId());
                contract.setCode(objContract.getCode());
                contract.setCreateTime(objContract.getCreateTime());
                contract.setTitle(objContract.getGranter().getName());
            }
            contracts.add(contract);
        }

        return contracts;
    }
}
