package com.ideamoment.emars.sale.service.impl;

import com.ideamoment.emars.model.SaleContract;
import com.ideamoment.emars.sale.dao.ContractMapper;
import com.ideamoment.emars.sale.service.ContractService;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yukiwang on 2018/3/19.
 */
@Service
public class ContractServiceImpl implements ContractService{

    @Autowired
    private ContractMapper contractMapper;

    @Override
    @Transactional
    public Page<SaleContract> pageContracts(SaleContract condition, int offset, int pageSize) {

        long count = contractMapper.listContractsCount(condition);
        int currentPage = offset/pageSize + 1;

        List<SaleContract> contracts = contractMapper.listContracts(condition, offset, pageSize);

        Page<SaleContract> result = new Page<SaleContract>();
        result.setCurrentPage(currentPage);
        result.setData(contracts);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);

        return result;
    }
}
