package com.ideamoment.emars.copyright.dao;

import com.ideamoment.emars.model.Copyright;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by yukiwang on 2018/2/24.
 */
@Mapper
public interface CopyrightMapper {

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

}
