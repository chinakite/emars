package com.ideamoment.emars.sale.dao;

import com.ideamoment.emars.model.SaleContract;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by yukiwang on 2018/3/19.
 */
@Mapper
public interface ContractMapper {

    @Insert("INSERT INTO t_sale_contract" +
            "(`C_CODE`,`C_OWNER`,`C_OWNER_CONTACT`,`C_OWNER_CONTACT_PHONE`,`C_OWNER_CONTACT_ADDRESS`," +
            "`C_OWNER_CONTACT_EMAIL`,`C_BUYER`,`C_BUYER_CONTACT`,`C_BUYER_CONTACT_PHONE`,`C_BUYER_CONTACT_ADDRESS`," +
            "`C_BUYER_CONTACT_EMAIL`,`C_PRIVILEGES`,`C_PRIVILEGE_TYPE`,`C_PRIVILEGE_RANGE`,`C_PRIVILEGE_DEADLINE`," +
            "`C_BANK_ACCOUNT_NAME`,`C_BANK_ACCOUNT_NO`,`C_BANK`,`C_TOTAL_PRICE`,`C_STATE`,`C_FINISH_TIME`," +
            "`C_CREATOR`,`C_CREATETIME`) " +
            "values " +
            "(#{code}, #{owner}, #{ownerContact}, #{ownerContactPhone}, #{ownerContactAddress}, " +
            "#{ownerContactEmail}, #{buyer}, #{buyerContact}, #{buyerContactPhone}, #{buyerContactAddress}, " +
            "#{buyerContactEmail}, #{privileges}, #{privilegeType}, #{privilegeRange}, #{privilegeDeadline}, " +
            "#{bankAccountName}, #{bankAccountNo}, #{bank}, #{totalPrice}, #{state}, #{finishTime}, " +
            "#{creator}, #{createTime})")
    boolean insertCopyright(SaleContract saleContract);

    @Update("UPDATE t_sale_contract SET " +
            "`C_CODE`=#{code},`C_OWNER`=#{owner},`C_OWNER_CONTACT`=#{ownerContact}," +
            "`C_OWNER_CONTACT_PHONE`=#{ownerContactPhone},`C_OWNER_CONTACT_ADDRESS`=#{ownerContactAddress}," +
            "`C_OWNER_CONTACT_EMAIL`=#{ownerContactEmail},`C_BUYER`=#{buyer},`C_BUYER_CONTACT`=#{buyerContact}," +
            "`C_BUYER_CONTACT_PHONE`=#{buyerContactPhone},`C_BUYER_CONTACT_ADDRESS`=#{buyerContactAddress}," +
            "`C_BUYER_CONTACT_EMAIL`=#{buyerContactEmail},`C_PRIVILEGES`=#{privileges}," +
            "`C_PRIVILEGE_TYPE`=#{privilegeType},`C_PRIVILEGE_RANGE`=#{privilegeRange}," +
            "`C_PRIVILEGE_DEADLINE`=#{privilegeDeadline},`C_BANK_ACCOUNT_NAME`=#{bankAccountName}," +
            "`C_BANK_ACCOUNT_NO`=#{bankAccountNo},`C_BANK`=#{bank},`C_TOTAL_PRICE`=#{totalPrice}," +
            "`C_STATE`=#{state},`C_FINISH_TIME`=#{finishTime},`C_MODIFIER`=#{modifier},`C_MODIFYTIME`=#{modifyTime} " +
            "WHERE c_id = #{id}")
    boolean updateCopyright(SaleContract saleContract);

    @Select("SELECT * FROM t_sale_contract WHERE c_id = #{id}")
    @Results(id = "saleContractMap", value = {
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
            @Result(property = "state", column = "C_STATE"),
            @Result(property = "finishTime", column = "C_FINISH_TIME"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME")
    })
    SaleContract findSaleContract(@Param("id") long id);

    @Select({"<script>",
            "SELECT * FROM t_sale_contract ",
            "WHERE c_id > 0",
            "<if test='condition.code != null'>",
            " AND c_code = #{condition.code}",
            "</if>",
            "<if test='condition.owner != null'>",
            " AND c_owner like concat(concat('%',#{condition.owner}),'%')",
            "</if>",
            "<if test='condition.state != null'>",
            " AND c_state = #{condition.state}",
            "</if>",
            " ORDER BY C_MODIFYTIME DESC ",
            " LIMIT #{offset}, #{size}",
            "</script>"})
    @ResultMap("saleContractMap")
    List<SaleContract> listContracts(@Param("condition") SaleContract condition, @Param("offset")int offset, @Param("size")int size);

    @Select({"<script>",
            "SELECT COUNT(*) FROM t_sale_contract ",
            "WHERE c_id > 0",
            "<if test='condition.code != null'>",
            " AND c_code = #{condition.code}",
            "</if>",
            "<if test='condition.owner != null'>",
            " AND c_owner like concat(concat('%',#{condition.owner}),'%')",
            "</if>",
            "<if test='condition.state != null'>",
            " AND c_state = #{condition.state}",
            "</if>",
            "</script>"})
    long listContractsCount(@Param("condition") SaleContract condition);


}
