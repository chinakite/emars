package com.ideamoment.emars.copyright.dao;

import com.ideamoment.emars.copyright.CopyrightProductInfo;
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

    @Insert("INSERT INTO t_copyright_product" +
            "(`c_copyright_id`,`c_product_id`,`c_price`,`c_price`,`c_privileges`,`c_grant`,`c_copyright_type`,`c_settlement_type`,`c_beign`,`c_end`,`c_desc`,`c_creator`,`c_createtime`,`c_modifier`,`c_modifytime`) " +
            "VALUES" +
            "(#{contractId}, #{productId}, #{price})")
    boolean insertCopyrightProduct(CopyrightProductInfo copyrightProductInfo);
}
