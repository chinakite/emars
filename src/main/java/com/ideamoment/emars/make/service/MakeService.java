package com.ideamoment.emars.make.service;

import com.ideamoment.emars.model.*;
import com.ideamoment.emars.utils.Page;

import java.util.List;

/**
 * Created by yukiwang on 2018/3/5.
 */
public interface MakeService {

    Page<ProductInfo> pageProducts(ProductInfo condition, int offset, int pageSize);

    Page<MakeContract> pageMakeContracts(MakeContractQueryVo condition, int offset, int pageSize);

    String saveMakeTask(MakeTask makeTask);

    MakeContract findMakeContract(long id);

    MakeContract findMakeContractByProduct(long productId);

    String saveMakeContract(MakeContract makeContract);

    List<MakeContractDoc> listContractDocs(long contractId);

    List<ProductInfo> listProducts(ProductInfo condition);

    String deleteMakeContract(Long id);
}
