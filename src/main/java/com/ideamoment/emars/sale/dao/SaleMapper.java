package com.ideamoment.emars.sale.dao;

import com.ideamoment.emars.model.Sale;
import com.ideamoment.emars.model.SaleContractQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chinakite on 2018/6/14.
 */
@Mapper
public interface SaleMapper {

    @Select("select count(c_id) from t_sale where c_customer_id = #{customerId}")
    long countSalesByCustomer(@Param("customerId")long id);

    @Select({"<script>",
            "SELECT COUNT(*) FROM t_sale_contract s",
            "WHERE s.c_id > 0",
            "<if test='condition.code != null'>",
            " AND s.c_code = #{condition.code}",
            "</if>",
            "<if test='condition.targetType != null'>",
            " AND s.c_target_type = #{condition.targetType}",
            "</if>",
            "</script>"})
    long countSaleContracts(SaleContractQueryVo condition);

    @Select({"<script>",
            "SELECT s.* FROM t_sale_contract s",
            "WHERE s.c_id > 0",
            "<if test='condition.code != null'>",
            " AND s.c_code = #{condition.code}",
            "</if>",
            "<if test='condition.targetType != null'>",
            " AND m.c_target_type = #{condition.targetType}",
            "</if>",
            " ORDER By s.c_createtime desc ",
            " LIMIT ?,?",
            "</script>"})
    ArrayList<Sale> pageSaleContracts(SaleContractQueryVo condition, int offset, int pageSize);
}
