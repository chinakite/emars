package com.ideamoment.emars.granter.dao;

import com.ideamoment.emars.model.Granter;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GranterMapper {
    @Select("select * from t_granter where c_id = #{id}")
    @Results(id = "granterMap", value = {
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "name", column = "c_name"),
            @Result(property = "contact", column = "c_contact"),
            @Result(property = "phone", column = "c_phone"),
            @Result(property = "desc", column = "c_desc")
    })
    Granter findGranter(@Param("id") Long id);

    @Select("select * from t_granter ORDER BY C_MODIFYTIME DESC LIMIT #{offset}, #{size}")
    @ResultMap("granterMap")
    List<Granter> pageGranters(@Param("offset")int offset, @Param("size")int size);

    @Select("select * from t_granter ORDER BY C_MODIFYTIME DESC")
    @ResultMap("granteeMap")
    List<Granter> listGranters();

    @Select("select count(c_id) from t_granter")
    long countGranter();

    @Insert("insert into t_granter " +
            "(`c_name`, `c_contact`, `c_phone`, `c_desc`, `c_creator`, `c_createtime`, `c_modifier`, `c_modifytime`)" +
            "values" +
            "(#{name}, #{contact}, #{phone}, #{desc}, #{creator}, #{createTime}, #{modifier}, #{modifyTime})"
    )
    @Options(useGeneratedKeys = true)
    boolean insertGranter(Granter granter);

    @Update("update t_granter set c_name = #{name}, c_contact = #{contact}, c_phone = #{phone}, c_desc = #{desc}, " +
            "c_modifier = #{modifier}, c_modifytime=#{modifyTime} " +
            "where c_id = #{id}"
    )
    boolean updateGranter(Granter granter);

    @Delete("delete from t_granter where c_id = #{id}")
    boolean deleteGranter(@Param("id") Long id);

    @Select({"<script>",
            "select * from t_granter where c_name = #{name}",
            "<if test='id != null'>",
            " AND c_id != #{id}",
            "</if>",
            "</script>"})
    @ResultMap("granterMap")
    Granter findGranterByName(@Param("name") String name, @Param("id") Long ignoreId);

}
