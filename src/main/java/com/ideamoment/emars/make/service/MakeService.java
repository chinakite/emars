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

    List<MakeContract> findMakeContractByProductId(long productId);

    String saveMakeContract(MakeContract makeContract);

    List<MakeContractDoc> listContractDocs(long mcProductId, String type);

    List<ProductInfo> listProducts(ProductInfo condition);

    String deleteMakeContract(Long id);

    MakeContractProduct findMcProduct(long id);

    String saveMcProductFiles(List<MakeContractDoc> makeContractDocs);

    List<MakeContractProduct> findMcProductsByProductId(long productId);

    List<ProductMakeContract> findProductMakeContracts(long productId);

    String deleteMcDoc(long id);

    String generateContractCode(String signDate, String type);

    String changeMakeContractState(long id, String state);

}
