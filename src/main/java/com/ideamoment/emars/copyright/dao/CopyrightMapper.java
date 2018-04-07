package com.ideamoment.emars.copyright.dao;

import com.ideamoment.emars.model.Copyright;
import com.ideamoment.emars.model.CopyrightContract;
import com.ideamoment.emars.model.Grantee;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by yukiwang on 2018/2/24.
 */
@Mapper
public interface CopyrightMapper {

    @Insert("INSERT INTO t_copyright_contract" +
            "(`C_CODE`,`C_OWNER`,`C_OWNER_CONTACT`,`C_OWNER_CONTACT_PHONE`,`C_OWNER_CONTACT_ADDRESS`," +
            "`C_OWNER_CONTACT_EMAIL`,`C_BUYER`,`C_BUYER_CONTACT`,`C_BUYER_CONTACT_PHONE`,`C_BUYER_CONTACT_ADDRESS`," +
            "`C_BUYER_CONTACT_EMAIL`,`C_PRIVILEGES`,`C_PRIVILEGE_TYPE`,`C_PRIVILEGE_RANGE`,`C_PRIVILEGE_DEADLINE`," +
            "`C_BANK_ACCOUNT_NAME`,`C_BANK_ACCOUNT_NO`,`C_BANK`,`C_TOTAL_PRICE`,`C_AUDIT_STATE`,`C_FINISH_TIME`," +
            "`C_CREATOR`,`C_CREATETIME`) " +
            "values " +
            "(#{code}, #{owner}, #{ownerContact}, #{ownerContactPhone}, #{ownerContactAddress}, " +
            "#{ownerContactEmail}, #{buyer}, #{buyerContact}, #{buyerContactPhone}, #{buyerContactAddress}, " +
            "#{buyerContactEmail}, #{privileges}, #{privilegeType}, #{privilegeRange}, #{privilegeDeadline}, " +
            "#{bankAccountName}, #{bankAccountNo}, #{bank}, #{totalPrice}, #{auditState}, #{finishTime}, " +
            "#{creator}, #{createTime})")
    boolean insertCopyright(Copyright copyright);

    @Update("UPDATE t_copyright_contract SET " +
            "`C_CODE`=#{code},`C_OWNER`=#{owner},`C_OWNER_CONTACT`=#{ownerContact}," +
            "`C_OWNER_CONTACT_PHONE`=#{ownerContactPhone},`C_OWNER_CONTACT_ADDRESS`=#{ownerContactAddress}," +
            "`C_OWNER_CONTACT_EMAIL`=#{ownerContactEmail},`C_BUYER`=#{buyer},`C_BUYER_CONTACT`=#{buyerContact}," +
            "`C_BUYER_CONTACT_PHONE`=#{buyerContactPhone},`C_BUYER_CONTACT_ADDRESS`=#{buyerContactAddress}," +
            "`C_BUYER_CONTACT_EMAIL`=#{buyerContactEmail},`C_PRIVILEGES`=#{privileges}," +
            "`C_PRIVILEGE_TYPE`=#{privilegeType},`C_PRIVILEGE_RANGE`=#{privilegeRange}," +
            "`C_PRIVILEGE_DEADLINE`=#{privilegeDeadline},`C_BANK_ACCOUNT_NAME`=#{bankAccountName}," +
            "`C_BANK_ACCOUNT_NO`=#{bankAccountNo},`C_BANK`=#{bank},`C_TOTAL_PRICE`=#{totalPrice}," +
            "`C_AUDIT_STATE`=#{auditState},`C_FINISH_TIME`=#{finishTime},`C_MODIFIER`=#{modifier}," +
            "`C_MODIFYTIME`=#{modifyTime} " +
            "WHERE c_id = #{id}")
    boolean updateCopyright(Copyright copyright);

    @Select("SELECT * FROM t_copyright WHERE c_id = #{id}")
    @Results(id = "copyrightMap", value = {
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "contractCode", column = "C_CODE"),
            @Result(property = "contractType", column = "C_TYPE"),
            @Result(property = "granter", column = "C_GRANTER"),
            @Result(property = "granteeId", column = "C_GRANTEE_ID"),
            @Result(property = "signDate", column = "c_signdate"),
            @Result(property = "operator", column = "C_operator"),
            @Result(property = "projectCode", column = "c_project_code"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME"),
            @Result(property = "operatorName", column = "operatorName"),
            @Result(property = "grantee", column = "grantee")
    })
    CopyrightContract findCopyright(@Param("id") long id);

    @Select({"<script>",
            "SELECT t_copyright.*, t_user.c_name as operatorName, t_grantee.c_name as grantee FROM t_copyright, t_user, t_grantee",
            "WHERE t_copyright.c_id > 0",
            "<if test='condition.contractCode != null'>",
            " AND t_copyright.c_code = #{condition.contractCode}",
            "</if>",
            "<if test='condition.granter != null'>",
            " AND t_copyright.c_granter like concat(concat('%',#{condition.granter}),'%')",
            "</if>",
            " AND t_copyright.c_operator = t_user.c_id",
            " AND t_copyright.c_grantee_id = t_grantee.c_id",
            " ORDER BY C_MODIFYTIME DESC ",
            " LIMIT #{offset}, #{size}",
            "</script>"})
    @ResultMap("copyrightMap")
    List<CopyrightContract> listCopyrights(@Param("condition") CopyrightContract condition, @Param("offset") int offset, @Param("size") int size);

    @Select({"<script>",
            "SELECT COUNT(*) FROM t_copyright_contract",
            "WHERE c_id > 0",
            "<if test='code != null'>",
            " AND c_code = #{code}",
            "</if>",
            "<if test='owner != null'>",
            " AND c_owner like concat(concat('%',#{owner}),'%')",
            "</if>",
            "<if test='auditState != null'>",
            " AND c_audit_state = #{auditState}",
            "</if>",
            "</script>"})
    long listCopyrightsCount(Copyright condition);

    @Select({"<script>",
            "SELECT COUNT(*) FROM t_copyright",
            "WHERE c_id > 0",
            "<if test='contractCode != null'>",
            " AND c_code = #{contractCode}",
            "</if>",
            "<if test='granter != null'>",
            " AND c_granter like concat(concat('%',#{granter}),'%')",
            "</if>",
            "</script>"})
    long countCopyrights(CopyrightContract condition);

    @Select("select C_CODE from T_COPYRIGHT_CONTRACT where C_CODE like concat(concat('%',#{code}),'%') order by C_CODE desc limit 0,1")
    String queryMaxContractCode(@Param("code") String code);

    @Delete("delete from T_COPYRIGHT_CTRT_PROD where C_CONTRACT_ID = #{contractId}")
    boolean deleteContractProduct(@Param("contractId") long contractId);

    List<Copyright> listProductContracts(@Param("productId") long productId);

    @Insert("insert into t_copyright (c_code, c_type, c_granter, c_grantee_id, c_signdate, c_operator, c_project_code, c_creator, c_createtime, c_modifier, c_modifytime)values(#{contractCode}, #{contractType}, #{granter}, #{granteeId}, #{signDate}, #{operator}, #{projectCode}, #{creator}, #{createTime}, #{modifier}, #{modifyTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean insertCopyrightContract(CopyrightContract copyrightContract);

    @Select("select count(c_id) from t_copyright where c_grantee_id = #{granteeId}")
    long countCopyrightsByGrantee(@Param("granteeId") long granteeId);

    @Select("select count(c_id) from t_copyright where c_granter_id = #{granterId}")
    long countCopyrightsByGranter(@Param("granterId")long id);
}
