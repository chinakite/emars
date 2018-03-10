package com.ideamoment.emars.make.service;

import com.ideamoment.emars.model.*;
import com.ideamoment.emars.utils.Page;

import java.util.List;

/**
 * Created by yukiwang on 2018/3/5.
 */
public interface MakeService {

    Page<ProductResultVo> pageProducts(ProductQueryVo condition, int offset, int pageSize);

    String saveMakeTask(MakeTask makeTask);

    MakeContract findMakeContract(long id);

    MakeContract findMakeContractByProduct(long productId);

    String saveMakeContract(MakeContract makeContract, String type);

    List<MakeContractDoc> listContractDocs(long contractId);

}
