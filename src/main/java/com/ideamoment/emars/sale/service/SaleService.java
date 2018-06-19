package com.ideamoment.emars.sale.service;

import com.ideamoment.emars.model.ProductInfo;
import com.ideamoment.emars.model.Sale;
import com.ideamoment.emars.model.SaleContractQueryVo;
import com.ideamoment.emars.utils.Page;

import java.util.List;

/**
 * Created by Chinakite on 2018/6/19.
 */
public interface SaleService {
    List<ProductInfo> listProducts();

    Page<Sale> pageSaleContracts(SaleContractQueryVo condition, int start, int length);
}
