package com.ideamoment.emars.grantee.dao;

import com.ideamoment.emars.model.Grantee;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GranteeMapper {

    @Select("select * from t_grantee where c_id = #{id}")
    @Results(id = "granteeMap", value = {
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "name", column = "c_name"),
            @Result(property = "desc", column = "c_desc"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME")
    })
    Grantee findGrantee(@Param("id") Long id);

    @Select("select * from t_grantee ORDER BY C_MODIFYTIME DESC LIMIT #{offset}, #{size}")
    @ResultMap("granteeMap")
    List<Grantee> pageGrantees(@Param("offset")int offset, @Param("size")int size);

    @Select("select * from t_grantee ORDER BY C_MODIFYTIME DESC")
    @ResultMap("granteeMap")
    List<Grantee> listGrantees();

    @Select("select count(c_id) from t_grantee")
    long countGrantee();

    @Insert("insert into t_grantee " +
            "(`c_name`, `c_desc`, `c_creator`, `c_createtime`, `c_modifier`, `c_modifytime`)" +
            "values" +
            "(#{name}, #{desc}, #{creator}, #{createTime}, #{modifier}, #{modifyTime})"
    )
    @Options(useGeneratedKeys = true)
    boolean insertGrantee(Grantee grantee);

    @Update("update t_grantee set c_name = #{name}, c_desc = #{desc}, " +
            "c_modifier = #{modifier}, c_modifytime=#{modifyTime} " +
            "where c_id = #{id}"
    )
    boolean updateGrantee(Grantee grantee);

    @Delete("delete from t_grantee where c_id = #{id}")
    boolean deleteGrantee(@Param("id") Long id);

    @Select({"<script>",
            "select * from t_grantee where c_name = #{name}",
            "<if test='id != null'>",
            " AND c_id != #{id}",
            "</if>",
            "</script>"})
    @ResultMap("granteeMap")
    Grantee findGranteeByName(@Param("name") String name, @Param("id") Long ignoreId);

}
