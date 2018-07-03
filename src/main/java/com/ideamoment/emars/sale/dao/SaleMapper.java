package com.ideamoment.emars.sale.dao;

import com.ideamoment.emars.model.*;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.Date;
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
            @Result(property = "granterId", column = "C_GRANTER_ID"),
            @Result(property = "customerId", column = "C_CUSTOMER_ID"),
            @Result(property = "totalSection", column = "C_TOTAL_SECTION"),
            @Result(property = "totalPrice", column = "C_TOTAL_PRICE"),
            @Result(property = "signDate", column = "C_SIGNDATE"),
            @Result(property = "begin", column = "C_BEGIN"),
            @Result(property = "end", column = "C_END"),
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
    @ResultMap("saleMap")
    ArrayList<Sale> pageSaleContracts(@Param("condition")SaleContractQueryVo condition, @Param("offset")int offset, @Param("pageSize")int pageSize);

    @Select({"<script>",
            "SELECT count(s.c_id) FROM t_sale_contract s, t_sale_customer_platform p ",
            "WHERE p.c_platform_id = #{platformId} " +
                    "AND p.c_sale_constract_id = s.c_id",
            "</script>"})
    long countSalesByPlatform(@Param("platformId")long id);

    @Select("select count(c_id) from t_sale_contract where c_signdate >= #{begin} and c_signdate < #{end} and C_TYPE = #{type}")
    long countContractsByTimeAndType(@Param("begin") Date begin, @Param("end") Date end, @Param("type") String type);

    @Select("SELECT * FORM t_sale_product WHERE c_id = #{id}")
    @Results(id = "saleProductMap", value ={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "saleId", column = "C_SALE_CONTRACT_ID"),
            @Result(property = "productId", column = "C_PRODUCT_ID"),
            @Result(property = "price", column = "C_PRICE"),
            @Result(property = "section", column = "C_SECTION"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME")
    })
    SaleProduct findSaleProduct(@Param("id") long id);

    @Select("SELECT * FROM t_sale_product WHERE c_sale_contract_id = #{saleId}")
    @ResultMap("saleProductMap")
    ArrayList<SaleProduct> findSaleProductsBySaleId(@Param("saleId")long saleId);

    @Select({
            "<script>",
            "SELECT * FROM T_SALE_FILE WHERE C_SALE_PRODUCT_Id = #{saleProductId}",
            "<if test='type != null'>",
            " AND C_TYPE = #{type}",
            "</if>",
            " ORDER BY C_CREATETIME DESC",
            "</script>"})
    @Results(id = "saleContractFileMap", value ={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "saleProductId", column = "C_SALE_PRODUCT_Id"),
            @Result(property = "name", column = "C_NAME"),
            @Result(property = "path", column = "C_PATH"),
            @Result(property = "desc", column = "C_DESC"),
            @Result(property = "type", column = "C_TYPE"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME")
    })
    ArrayList<SaleContractFile> listContractFiles(@Param("saleProductId") long mcProductId, @Param("type") String type);

    @Update("update t_sale_contract set c_code=#{code}, c_type=#{type}, c_granter_id=#{granterId}, " +
            "c_customer_id=#{customerId}, c_privileges=#{privileges}, " +
            "c_signdate=#{signDate}, c_operator=#{operator}, c_total_section=#{totalSection}, c_total_price=#{totalPrice}, " +
            "c_begin=#{begin}, c_end=#{end}, c_project_code=#{projectCode}, c_modifier=#{modifier}, c_modifytime=#{modifyTime} " +
            "where c_id=#{id}")
    boolean updateSaleConntract(Sale saleContract);

    @Select("select * from t_sale_product where C_SALE_CONTRACT_ID = #{id}")
    @ResultMap("saleProductMap")
    ArrayList<SaleProduct> listContractProducts(@Param("id")long id);

    @Update("update t_sale_product set c_section=#{section}, c_price=#{price}, " +
            "c_modifier=#{modifier}, c_modifytime=#{modifyTime} where c_id=#{id}")
    boolean updateSaleContractProduct(SaleProduct saleProduct);

    @Insert("insert into t_sale_product (c_sale_contract_id, c_product_id, c_section, c_price, c_creator, c_createtime)" +
            "values(#{saleId}, #{productId}, #{section}, #{price}, #{creator}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean insertSaleContractProduct(SaleProduct saleProduct);

    @Delete("delete from t_sale_product where c_id = #{id}")
    boolean deleteSaleContractProducts(long id);

    @Insert("insert into t_sale_contract (c_code, c_type, c_granter_id, c_customer_id, " +
            "c_privileges, c_signdate, c_operator, c_total_section, c_total_price, c_begin, c_end, c_project_code, " +
            "c_creator, c_createtime)values(#{code}, #{type}, #{granterId}, #{customerId}, #{privileges}, " +
            "#{signDate}, #{operator}, #{totalSection}, #{totalPrice}, #{begin}, #{end}, #{projectCode}, #{creator}, " +
            "#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean insertSaleContract(Sale saleContract);

    @Insert("insert into t_sale_product (c_sale_contract_id, c_product_id, c_section, c_price, " +
            "c_creator, c_createtime)values(#{saleId}, #{productId}, #{section}, #{price}, #{creator}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean insertSaleProduct(SaleProduct saleProduct);

    @Insert("insert into t_sale_customer_platform (c_sale_contract_id, c_customer_id, c_platform_id, c_creator, c_createtime)" +
            "values(#{saleId}, #{customerId}, #{platformId}, #{creator}, #{createTime})")
    boolean insertSaleCustomerPlatform(SaleCustomerPlatform platform);

    @Select("select sp.c_id as id, sp.c_sale_contract_id as saleId, sp.c_customer_id as customerId, p.c_id as platformId, p.c_name as platformName " +
            "from t_platform p, t_sale_customer_platform sp where sp.c_sale_contract_id = #{saleId} " +
            "and sp.c_platform_id = p.c_id")
    @Results(id = "saleCustomerPlatformMap", value ={
            @Result(property = "id", column = "id"),
            @Result(property = "saleId", column = "saleId"),
            @Result(property = "customerId", column = "customerId"),
            @Result(property = "platformId", column = "platformId"),
            @Result(property = "platformName", column = "platformName")
    })
    ArrayList<SaleCustomerPlatform> listContractPlatforms(@Param("saleId") long saleId);
}
