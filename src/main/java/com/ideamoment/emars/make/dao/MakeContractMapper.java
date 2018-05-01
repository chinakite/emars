package com.ideamoment.emars.make.dao;

import com.ideamoment.emars.model.MakeContract;
import com.ideamoment.emars.model.MakeContractDoc;
import com.ideamoment.emars.model.MakeContractProduct;
import com.ideamoment.emars.model.MakeContractQueryVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by yukiwang on 2018/3/10.
 */
@Mapper
public interface MakeContractMapper {

    @Insert("INSERT INTO t_make_contract " +
            "(`C_CODE`,`C_TARGET_TYPE`,`C_OWNER`,`C_TOTAL_SECTION`,`C_TOTAL_PRICE`," +
            "`C_CREATOR`,`C_CREATETIME`) " +
            "VALUES (" +
            "#{code}, #{targetType}, #{owner}, #{totalSection}, #{totalPrice}, " +
            "#{creator}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean insertMakeContract(MakeContract makeContract);

    @Update("UPDATE t_make_contract SET " +
            "`C_CODE`=#{code},`C_TARGET_TYPE`=#{targetType},`C_OWNER`=#{owner}," +
            "`C_TOTAL_SECTION`=#{totalSection},`C_TOTAL_PRICE`=#{totalPrice}," +
            "`C_MODIFIER`=#{modifier},`C_MODIFYTIME`=#{modifyTime} " +
            "WHERE c_id = #{id}")
    boolean updateMakeConntract(MakeContract makeContract);

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

    @Select("SELECT * FROM t_make_contract WHERE c_product_id = #{productId}")
    @ResultMap("makeContractMap")
    MakeContract findMakeContractByProduct(@Param("productId") long productId);

    @Insert("INSERT INTO T_MAKE_CTRT_DOC " +
            "(`C_CONTRACT_ID`,`C_TYPE`,`C_CREATOR_ID`,`C_CREATETIME`,`C_VERSION`,`C_FILE_URL`) " +
            "VALUES " +
            "(#{contractId}, #{type}, #{creatorId}, #{createTime}, #{version}, #{fileUrl})")
    boolean insertMakeContractDoc(MakeContractDoc makeContractDoc);

    @Select("SELECT * FROM T_MAKE_CTRT_DOC WHERE C_CONTRACT_ID = #{contractId} ORDER BY C_CREATETIME DESC")
    @Results(id = "makeContractDocMap", value ={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "contractId", column = "C_CONTRACT_ID"),
            @Result(property = "type", column = "C_TYPE"),
            @Result(property = "creatorId", column = "C_CREATOR_ID"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "version", column = "C_VERSION"),
            @Result(property = "fileUrl", column = "C_FILE_URL")
    })
    List<MakeContractDoc> listContractDocs(@Param("contractId") long contractId);

    @Insert("INSERT INTO T_MAKE_CONTRACT_PRODUCT " +
            "(`C_MAKE_CONTRACT_ID`,`C_PRODUCT_ID`,`C_PRICE`,`C_SECTION`,`C_CREATOR`,`C_CREATETIME`) " +
            "VALUES " +
            "(#{makeContractId}, #{productId}, #{price}, #{section}, #{creator}, #{createTime})")
    boolean insertMakeContractProduct(MakeContractProduct makeContractProduct);

    @Delete("DELETE FROM t_make_contract WHERE c_id = #{id}")
    boolean deleteMakeContract(@Param("id") long id);

    @Delete("DELETE FROM t_make_contract_product WHERE c_make_contract_id = #{mcId}")
    boolean deleteMakeContractProducts(@Param("mcId") long mcId);

    @Delete("DELETE FROM t_make_ctrt_doc WHERE c_contract_id = #{mcId}")
    boolean deleteMakeContractDocs(@Param("mcId") long mcId);
}
