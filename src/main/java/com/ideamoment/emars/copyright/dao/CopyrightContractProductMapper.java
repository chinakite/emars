package com.ideamoment.emars.copyright.dao;

import com.ideamoment.emars.model.CopyrightContractProduct;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by yukiwang on 2018/3/5.
 */
@Mapper
public interface CopyrightContractProductMapper {

    @Insert("INSERT INTO t_copyright_ctrt_prod" +
            "(`C_CONTRACT_ID`,`C_PRODUCT_ID`,`C_PRICE`) " +
            "VALUES" +
            "(#{contractId}, #{productId}, #{price})")
    boolean insertCopyrightContractProduct(CopyrightContractProduct copyrightContractProduct);

}
