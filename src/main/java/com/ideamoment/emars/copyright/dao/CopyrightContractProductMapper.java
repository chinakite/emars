package com.ideamoment.emars.copyright.dao;

import com.ideamoment.emars.model.CopyrightProductInfo;
import com.ideamoment.emars.model.CopyrightContractProduct;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

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
            "(`c_copyright_id`,`c_product_id`,`c_price`,`c_privileges`,`c_grant`,`c_copyright_type`,`c_settlement_type`,`c_begin`,`c_end`,`c_desc`,`c_creator`,`c_createtime`,`c_modifier`,`c_modifytime`) " +
            "VALUES" +
            "(#{copyrightId}, #{productId}, #{copyrightPrice}, #{privileges}, #{grant}, #{copyrightType}, #{settlementType}, #{copyrightBegin}, #{copyrightEnd}, #{desc}, #{creator}, #{createTime}, #{modifier}, #{modifyTime})")
    boolean insertCopyrightProduct(CopyrightProductInfo copyrightProductInfo);

    @Update("update t_copyright_product set " +
            "c_price=#{copyrightPrice}, c_privileges=#{privileges}, c_grant=#{grant}, " +
            "c_copyright_type=#{copyrightType}, c_settlement_type=#{settlementType}, c_begin=#{copyrightBegin}, " +
            "c_end=#{copyrightEnd}, c_modifier=#{modifier}, c_modifytime=#{modifyTime} " +
            "where c_copyright_id=#{copyrightId} and c_product_id=#{productId}"
    )
    boolean updateContractProductInfo(CopyrightProductInfo product);
}
