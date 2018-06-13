package com.ideamoment.emars.sale.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Chinakite on 2018/6/14.
 */
@Mapper
public interface SaleMapper {

    @Select("select count(c_id) from t_sale where c_customer_id = #{customerId}")
    long countSalesByCustomer(@Param("customerId")long id);

}
