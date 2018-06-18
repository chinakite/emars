package com.ideamoment.emars.sale.service;

import com.ideamoment.emars.model.ProductInfo;
import com.ideamoment.emars.model.SaleContract;
import com.ideamoment.emars.utils.Page;

import java.util.List;

/**
 * Created by yukiwang on 2018/3/19.
 */
public interface ContractService {

    Page<SaleContract> pageContracts(SaleContract condition, int offset, int pageSize);

}
