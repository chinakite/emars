package com.ideamoment.emars.copyright.dao;

import com.ideamoment.emars.model.Copyright;
import com.ideamoment.emars.model.CopyrightContract;
import com.ideamoment.emars.model.CopyrightProductInfo;
import com.ideamoment.emars.model.Grantee;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Update("update t_copyright set " +
            "c_code = #{contractCode}," +
            "c_type = #{contractType}," +
            "c_granter_id = #{granterId}," +
            "c_grantee_id = #{granteeId}," +
            "c_signdate = #{signDate}," +
            "c_operator = #{operator}," +
            "c_project_code = #{projectCode}," +
            "c_modifier = #{modifier}," +
            "c_modifytime = #{modifyTime} " +
            "where c_id = #{id}"
    )
    boolean updateCopyrightContract(CopyrightContract copyright);

    @Select("SELECT * FROM t_copyright WHERE c_id = #{id}")
    @Results(id = "copyrightMap", value = {
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "contractCode", column = "C_CODE"),
            @Result(property = "contractType", column = "C_TYPE"),
            @Result(property = "granterId", column = "C_GRANTER_ID"),
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
            "</script>"})
    long countCopyrights(CopyrightContract condition);

    @Select("select C_CODE from T_COPYRIGHT_CONTRACT where C_CODE like concat(concat('%',#{code}),'%') order by C_CODE desc limit 0,1")
    String queryMaxContractCode(@Param("code") String code);

    @Delete("delete from T_COPYRIGHT_CTRT_PROD where C_CONTRACT_ID = #{contractId}")
    boolean deleteContractProduct(@Param("contractId") long contractId);

    List<Copyright> listProductContracts(@Param("productId") long productId);

    @Insert("insert into t_copyright (c_code, c_type, c_granter_id, c_grantee_id, c_signdate, c_operator, c_project_code, c_creator, c_createtime, c_modifier, c_modifytime)values(#{contractCode}, #{contractType}, #{granterId}, #{granteeId}, #{signDate}, #{operator}, #{projectCode}, #{creator}, #{createTime}, #{modifier}, #{modifyTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean insertCopyrightContract(CopyrightContract copyrightContract);

    @Select("select count(c_id) from t_copyright where c_grantee_id = #{granteeId}")
    long countCopyrightsByGrantee(@Param("granteeId") long granteeId);

    @Select("select count(c_id) from t_copyright where c_granter_id = #{granterId}")
    long countCopyrightsByGranter(@Param("granterId")long id);

    @Delete("delete from t_product_info where c_id in (select cp.c_product_id from t_copyright_product cp where cp.c_copyright_id = #{id})")
    boolean deleteCopyrightProducts(@Param("id") Long id);

    @Delete("delete from t_copyright_product where c_copyright_id = #{id}")
    boolean deleteCopyrightProductsRelations(@Param("id") Long id);

    @Delete("delete from t_copyright where c_id = #{id}")
    boolean deleteCopyright(@Param("id") Long id);

    @Select("select p.c_id as id, " +
            "p.c_name as name, " +
            "p.c_author_id as authorId, " +
            "p.c_wordcount as wordCount, " +
            "p.c_subject_id as subjectId, " +
            "p.c_publish_state as publishState, " +
            "p.c_isbn as isbn, " +
            "p.c_press as press, " +
            "cp.c_price as price, " +
            "cp.c_privileges as privilegesText, " +
            "cp.c_grant as grantType, " +
            "cp.c_copyright_type as copyrightType, " +
            "cp.c_beign as beginDate, " +
            "cp.c_end as endDate, " +
            "cp.c_settlement_type as settlementType, " +
            "cp.c_desc as descText " +
            "from t_product_info p, t_copyright_product cp " +
            "where cp.c_copyright_id = #{copyrightId} " +
            "and cp.c_product_id = p.c_id")
    @Results(id = "copyrightProductInfoMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "authorId", column = "authorId"),
            @Result(property = "wordCount", column = "wordCount"),
            @Result(property = "subjectId", column = "subjectId"),
            @Result(property = "publishState", column = "publishState"),
            @Result(property = "isbn", column = "isbn"),
            @Result(property = "press", column = "press"),
            @Result(property = "copyrightPrice", column = "price"),
            @Result(property = "privilegesText", column = "privilegesText"),
            @Result(property = "grant", column = "grantType"),
            @Result(property = "copyrightType", column = "copyrightType"),
            @Result(property = "copyrightBegin", column = "beginDate"),
            @Result(property = "copyrightEnd", column = "endDate"),
            @Result(property = "settlementType", column = "settlementType"),
            @Result(property = "desc", column = "descText")
    })
    ArrayList<CopyrightProductInfo> queryCopyrightProductInfoes(@Param("copyrightId") long id);

    @Select({"<script>",
            "select cp.c_product_id from t_copyright_product cp where cp.c_copyright_id = #{copyrightId} and cp.c_product_id not in ",
            "<foreach item=\"productId\" index=\"index\" collection=\"curProductIds\" open=\"(\" separator=\",\" close=\")\">",
            "#{productId}",
            "</foreach>",
            "</script>"
    })
    ArrayList<Long> queryCopyrightProductIdsToDelete(@Param("copyrightId") long copyrightId, @Param("curProductIds") List<Long> curProductIds);

    @Select("select cp.c_product_id from t_copyright_product cp where cp.c_copyright_id = #{copyrightId}")
    ArrayList<Long> queryCopyrightProductIds(@Param("copyrightId") long copyrightId);

    @Delete("delete from t_copyright_product where c_copyright_id = #{copyrightId} and c_product_id = #{productId}")
    boolean deleteCopyrightProductInfo(long copyrightId, @Param("productId") Long productId);
}
