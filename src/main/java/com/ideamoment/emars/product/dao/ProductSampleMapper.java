package com.ideamoment.emars.product.dao;

import com.ideamoment.emars.model.ProductSample;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by yukiwang on 2018/3/4.
 */
@Mapper
public interface ProductSampleMapper {

    @Insert("INSERT INTO t_product_sample" +
            "(`C_PRODUCT_ID`,`C_FILE_URL`,`C_CREATOR`,`C_CREATETIME`,`C_MODIFIER`,`C_MODIFYTIME`) " +
            "values (#{productId}, #{fileUrl}, #{creator}, #{createTime})")
    boolean insertProductSimpleMapper(ProductSample productSample);
}
