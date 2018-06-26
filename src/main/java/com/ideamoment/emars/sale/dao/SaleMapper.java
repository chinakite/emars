package com.ideamoment.emars.sale.dao;

import com.ideamoment.emars.model.Sale;
import com.ideamoment.emars.model.SaleContractQueryVo;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chinakite on 2018/6/14.
 */
@Mapper
public interface SaleMapper {
    @Select("select * from t_sale where c_id = #{id}")
    @Results(id = "saleMap", value ={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "code", column = "C_CODE"),
            @Result(property = "type", column = "C_TYPE"),
            @Result(property = "owner", column = "C_OWNER"),
            @Result(property = "totalSection", column = "C_TOTAL_SECTION"),
            @Result(property = "totalPrice", column = "C_TOTAL_PRICE"),
            @Result(property = "signDate", column = "C_SIGNDATE"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME"),
            @Result(property = "state", column = "c_state")
    })
    Sale findSaleContract(@Param("id") long id);

    @Select("select count(c_id) from t_sale_contract where c_customer_id = #{customerId}")
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
    long countSaleContracts(@Param("condition")SaleContractQueryVo condition);

    @Select({"<script>",
            "SELECT s.* FROM t_sale_contract s",
            "WHERE s.c_id > 0",
            "<if test='condition.code != null'>",
            " AND s.c_code = #{condition.code}",
            "</if>",
            "<if test='condition.targetType != null'>",
            " AND m.c_target_type = #{condition.type}",
            "</if>",
            " ORDER By s.c_createtime desc ",
            " LIMIT #{offset},#{pageSize}",
            "</script>"})
    ArrayList<Sale> pageSaleContracts(@Param("condition")SaleContractQueryVo condition, @Param("offset")int offset, @Param("pageSize")int pageSize);

    @Select({"<script>",
            "SELECT COUNT(*) FROM t_sale_contract s ",
            "WHERE s.c_platform_id = #{platformId}",
            "</script>"})
    long countSalesByPlatform(@Param("platformId")long id);
}
