package com.ideamoment.emars.copyright.dao;

import com.ideamoment.emars.model.Copyright;
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

    @Select("SELECT * FROM t_copyright_contract WHERE c_id = #{id}")
    @Results(id = "copyrightMap", value = {
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "code", column = "C_CODE"),
            @Result(property = "owner", column = "C_OWNER"),
            @Result(property = "ownerContact", column = "C_OWNER_CONTACT"),
            @Result(property = "ownerContactPhone", column = "C_OWNER_CONTACT_PHONE"),
            @Result(property = "ownerContactAddress", column = "C_OWNER_CONTACT_ADDRESS"),
            @Result(property = "ownerContactEmail", column = "C_OWNER_CONTACT_EMAIL"),
            @Result(property = "buyer", column = "C_BUYER"),
            @Result(property = "buyerContact", column = "C_BUYER_CONTACT"),
            @Result(property = "buyerContactPhone", column = "C_BUYER_CONTACT_PHONE"),
            @Result(property = "buyerContactAddress", column = "C_BUYER_CONTACT_ADDRESS"),
            @Result(property = "buyerContactEmail", column = "C_BUYER_CONTACT_EMAIL"),
            @Result(property = "privileges", column = "C_PRIVILEGES"),
            @Result(property = "privilegeType", column = "C_PRIVILEGE_TYPE"),
            @Result(property = "privilegeRange", column = "C_PRIVILEGE_RANGE"),
            @Result(property = "privilegeDeadline", column = "C_PRIVILEGE_DEADLINE"),
            @Result(property = "bankAccountName", column = "C_BANK_ACCOUNT_NAME"),
            @Result(property = "bankAccountNo", column = "C_BANK_ACCOUNT_NO"),
            @Result(property = "bank", column = "C_BANK"),
            @Result(property = "totalPrice", column = "C_TOTAL_PRICE"),
            @Result(property = "auditState", column = "C_AUDIT_STATE"),
            @Result(property = "finishTime", column = "C_FINISH_TIME"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME"),
            @Result(property = "authorName", column = "AUTHORNAME"),
            @Result(property = "subjectName", column = "SUBJECTNAME")
    })
    Copyright findCopyright(@Param("id") long id);

    @Select({"<script>",
            "SELECT * FROM t_copyright_contract",
            "WHERE c_id > 0",
            "<if test='condition.code != null'>",
            " AND c_code = #{condition.code}",
            "</if>",
            "<if test='condition.owner != null'>",
            " AND c_owner like concat(concat('%',#{condition.owner}),'%')",
            "</if>",
            "<if test='condition.auditState != null'>",
            " AND c_audit_state = #{condition.auditState}",
            "</if>",
            " ORDER BY C_MODIFYTIME DESC ",
            " LIMIT #{offset}, #{size}",
            "</script>"})
    @ResultMap("copyrightMap")
    List<Copyright> listCopyrights(@Param("condition") Copyright condition, @Param("offset") int offset, @Param("size") int size);

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

    @Select("select C_CODE from T_COPYRIGHT_CONTRACT where C_CODE like concat(concat('%',#{code}),'%') order by C_CODE desc limit 0,1")
    String queryMaxContractCode(@Param("code") String code);

    @Delete("delete from T_COPYRIGHT_CTRT_PROD where C_CONTRACT_ID = #{contractId}")
    boolean deleteContractProduct(@Param("contractId") long contractId);

}
