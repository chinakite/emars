package com.ideamoment.emars.make.dao;

import com.ideamoment.emars.model.*;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yukiwang on 2018/3/10.
 */
@Mapper
public interface MakeContractMapper {

    @Insert("INSERT INTO t_make_contract " +
            "(`C_CODE`,`C_TARGET_TYPE`,`C_OWNER`,`C_MAKER_ID`,`C_TOTAL_SECTION`,`C_TOTAL_PRICE`," +
            "`C_CREATOR`,`C_CREATETIME`) " +
            "VALUES (" +
            "#{code}, #{targetType}, #{owner}, #{makerId}, #{totalSection}, #{totalPrice}, " +
            "#{creator}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean insertMakeContract(MakeContract makeContract);

    @Update("UPDATE t_make_contract SET " +
            "`C_TARGET_TYPE`=#{targetType},`C_OWNER`=#{owner},`C_MAKER_ID`=#{makerId}," +
            "`C_TOTAL_SECTION`=#{totalSection},`C_TOTAL_PRICE`=#{totalPrice}," +
            "`C_MODIFIER`=#{modifier},`C_MODIFYTIME`=#{modifyTime} " +
            "WHERE c_id = #{id}")
    boolean updateMakeConntract(MakeContract makeContract);

    @Update("UPDATE t_make_contract_product SET " +
            "`C_SECTION`=#{section},`C_PRICE`=#{price},`C_ANNOUNCER_ID`=#{announcerId}," +
            "`C_MODIFIER`=#{modifier},`C_MODIFYTIME`=#{modifyTime} " +
            "WHERE c_id = #{id}")
    boolean updateMakeContractProduct(MakeContractProduct makeContractProduct);

    @Select("SELECT * FROM t_make_contract WHERE c_id = #{id}")
    @Results(id = "makeContractMap", value ={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "code", column = "C_CODE"),
            @Result(property = "makerId", column = "C_MAKER_ID"),
            @Result(property = "targetType", column = "C_TARGET_TYPE"),
            @Result(property = "owner", column = "C_OWNER"),
            @Result(property = "totalSection", column = "C_TOTAL_SECTION"),
            @Result(property = "totalPrice", column = "C_TOTAL_PRICE"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME")
    })
    MakeContract findMakeContract(@Param("id") long id);

    @Select({"<script>",
            "SELECT m.* FROM t_make_contract m",
            "WHERE m.c_id > 0",
            "<if test='condition.code != null'>",
            " AND m.c_code = #{condition.code}",
            "</if>",
//            "<if test='condition.productName != null'>",
//            " AND p.C_NAME like concat(concat('%',#{condition.productName}),'%')",
//            "</if>",
            "<if test='condition.targetType != null'>",
            " AND m.c_target_type = #{condition.targetType}",
            "</if>",
            " ORDER BY m.C_MODIFYTIME DESC ",
            " LIMIT #{offset}, #{size}",
            "</script>"})
    @ResultMap("makeContractMap")
    List<MakeContract> listMakeContracts(@Param("condition") MakeContractQueryVo condition, @Param("offset") int offset, @Param("size") int size);

    @Select({"<script>",
//            "SELECT m.* FROM t_make_contract m LEFT JOIN t_make_contract_product mcp ON m.c_id = mcp.c_make_contract_id LEFT JOIN t_product_info p ON mcp.c_product_id = p.c_id",
            "SELECT COUNT(*) FROM t_make_contract m",
            "WHERE m.c_id > 0",
            "<if test='condition.code != null'>",
            " AND m.c_code = #{condition.code}",
            "</if>",
//            "<if test='condition.productName != null'>",
//            " AND p.C_NAME like concat(concat('%',#{condition.productName}),'%')",
//            "</if>",
            "<if test='condition.targetType != null'>",
            " AND m.c_target_type = #{condition.targetType}",
            "</if>",
            "</script>"})
    long listMakeContractsCount(@Param("condition") MakeContractQueryVo condition);

    @Select("select C_CODE from T_MAKE_CONTRACT where C_CODE like concat(concat('%',#{code}),'%') order by C_CODE desc limit 0,1")
    String queryMaxContractCode(@Param("code") String code);

    @Select("SELECT * FROM t_make_contract m JOIN t_make_contract_product p ON m.c_id = p.c_make_contract_id WHERE p.c_product_id = #{productId}")
    @ResultMap("makeContractMap")
    List<MakeContract> findMakeContractByProductId(@Param("productId") long productId);

    @Insert("INSERT INTO T_MAKE_CTRT_DOC " +
            "(`C_MAKE_CONTRACT_PRODUCT_Id`,`C_NAME`,`C_PATH`,`C_DESC`,`C_TYPE`,`C_CREATOR`,`C_CREATETIME`) " +
            "VALUES " +
            "(#{mcProductId}, #{name}, #{path}, #{desc}, #{type}, #{creator}, #{createTime})")
    boolean insertMakeContractDoc(MakeContractDoc makeContractDoc);

    @Select({
            "<script>",
            "SELECT * FROM T_MAKE_CTRT_DOC WHERE C_MAKE_CONTRACT_PRODUCT_Id = #{mcProductId}",
            "<if test='type != null'>",
            " AND C_TYPE = #{type}",
            "</if>",
            " ORDER BY C_CREATETIME DESC",
            "</script>"})
    @Results(id = "makeContractDocMap", value ={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "mcProductId", column = "C_MAKE_CONTRACT_PRODUCT_Id"),
            @Result(property = "name", column = "C_NAME"),
            @Result(property = "path", column = "C_PATH"),
            @Result(property = "desc", column = "C_DESC"),
            @Result(property = "type", column = "C_TYPE"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME")
    })
    ArrayList<MakeContractDoc> listContractDocs(@Param("mcProductId") long mcProductId, @Param("type") String type);

    @Select("select * from t_product_announcer where c_mc_id = #{makeContractId} and c_product_id=#{productId}")
    @Results(id="productAnnouncerMap", value={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "productId", column = "C_PRODUCT_ID"),
            @Result(property = "makeContractId", column = "C_MC_ID"),
            @Result(property = "announcerId", column = "C_ANNOUNCER_ID"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME")
    })
    ArrayList<ProductAnnouncer> listContractProductAnnouncers(@Param("makeContractId") long makeContractId, @Param("productId") long productId);

    @Insert("INSERT INTO T_MAKE_CONTRACT_PRODUCT " +
            "(`C_MAKE_CONTRACT_ID`,`C_PRODUCT_ID`,`C_PRICE`,`C_SECTION`,`C_CREATOR`,`C_CREATETIME`) " +
            "VALUES " +
            "(#{makeContractId}, #{productId}, #{price}, #{section}, #{creator}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean insertMakeContractProduct(MakeContractProduct makeContractProduct);

    @Delete("DELETE FROM t_make_contract WHERE c_id = #{id}")
    boolean deleteMakeContract(@Param("id") long id);

    @Delete("DELETE FROM t_make_contract_product WHERE c_make_contract_id = #{mcId}")
    boolean deleteMakeContractProducts(@Param("mcId") long mcId);

    @Delete("DELETE FROM t_make_ctrt_doc WHERE C_MAKE_CONTRACT_PRODUCT_ID = #{mcProductId}")
    boolean deleteMakeContractDocs(@Param("mcProductId") long mcProductId);

    @Select("SELECT * FORM t_make_contract_product WHERE c_id = #{id}")
    @Results(id = "mcProductMap", value ={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "makeContractId", column = "C_MAKE_CONTRACT_ID"),
            @Result(property = "productId", column = "C_PRODUCT_ID"),
            @Result(property = "price", column = "C_PRICE"),
            @Result(property = "section", column = "C_SECTION")
    })
    MakeContractProduct findMcProduct(@Param("id") long id);

    @Select("SELECT * FROM t_make_contract_product WHERE c_make_contract_id = #{makeContractId}")
    @ResultMap("mcProductMap")
    ArrayList<MakeContractProduct> findMcProductsByMcId(@Param("makeContractId") long makeContractId);

    @Select("SELECT * FROM t_make_contract_product WHERE c_product_id = #{productId}")
    @ResultMap("mcProductMap")
    List<MakeContractProduct> findMcProductsByProductId(@Param("productId") long productId);

    @Select("SELECT * FROM t_make_contract_product WHERE c_product_id = #{productId} AND c_make_contract_id = ${contractId}")
    @ResultMap("mcProductMap")
    MakeContractProduct findMcProductsByProductIdAndContractId(@Param("productId") long productId, @Param("contractId") long contractId);

    @Delete("DELETE FROM t_make_ctrt_doc WHERE C_ID = #{id}")
    boolean deleteMakeContractDoc(@Param("id") long id);

    @Select("select count(c_id) from t_make_contract where c_maker_id = #{makerId}")
    long countMakeContractByMaker(@Param("makerId")long id);

    @Insert("insert into t_product_announcer (c_product_id, c_announcer_id, c_mc_id, c_creator, c_createtime)values(#{productId}, #{announcerId}, #{makeContractId}, #{creator}, #{createTime})")
    boolean insertProductAnnouncer(ProductAnnouncer productAnnouncer);

    @Insert("delete from t_product_announcer where c_mc_id = #{makeContractId} and c_product_id = #{productId}")
    boolean deleteContractProductAnnouncers(@Param("makeContractId") Long makeContractId, @Param("productId") Long productId);

    @Select("select * from t_make_contract_product where C_MAKE_CONTRACT_ID = #{id}")
    @ResultMap("mcProductMap")
    ArrayList<MakeContractProduct> listContractProducts(@Param("id") long id);
}
