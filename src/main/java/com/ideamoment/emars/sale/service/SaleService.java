package com.ideamoment.emars.sale.service;

import com.ideamoment.emars.model.*;
import com.ideamoment.emars.utils.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chinakite on 2018/6/19.
 */
public interface SaleService {
    List<ProductInfo> listProducts();

    Page<Sale> pageSaleContracts(SaleContractQueryVo condition, int start, int length);

    long countSaleContracts(SaleContractQueryVo condition);

    String generateContractCode(String signDate, String type);

    String saveSaleContract(Sale saleContract);

    Sale findSaleContract(long id);

    String changeSaleContractState(long id, String state);

    String deleteSaleDoc(long id);

    List<SaleContractFile> listContractDocs(long saleProductId, String type);

    String saveSaleProductFiles(ArrayList<SaleContractFile> saleContractDocs);

    List<ProductSaleContract> queryProductSale(long productId);
}
