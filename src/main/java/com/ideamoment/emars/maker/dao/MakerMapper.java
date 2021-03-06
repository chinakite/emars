package com.ideamoment.emars.maker.dao;

import com.ideamoment.emars.model.Maker;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MakerMapper {
    @Select("select * from t_maker where c_id = #{id}")
    @Results(id = "makerMap", value = {
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "name", column = "c_name"),
            @Result(property = "contact", column = "c_contact"),
            @Result(property = "phone", column = "c_phone"),
            @Result(property = "desc", column = "c_desc"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME")
    })
    Maker findMaker(@Param("id") Long id);

    @Select("select * from t_maker ORDER BY C_MODIFYTIME DESC LIMIT #{offset}, #{size}")
    @ResultMap("makerMap")
    List<Maker> pageMakers(@Param("offset")int offset, @Param("size")int size);

    @Select("select * from t_maker ORDER BY C_MODIFYTIME DESC")
    @ResultMap("makerMap")
    List<Maker> listMakers();

    @Select("select count(c_id) from t_maker")
    long countMaker();

    @Insert("insert into t_maker " +
            "(`c_name`, `c_contact`, `c_phone`, `c_desc`, `c_creator`, `c_createtime`, `c_modifier`, `c_modifytime`)" +
            "values" +
            "(#{name}, #{contact}, #{phone}, #{desc}, #{creator}, #{createTime}, #{modifier}, #{modifyTime})"
    )
    @Options(useGeneratedKeys = true)
    boolean insertMaker(Maker maker);

    @Update("update t_maker set c_name = #{name}, c_contact = #{contact}, c_phone = #{phone}, c_desc = #{desc}, " +
            "c_modifier = #{modifier}, c_modifytime=#{modifyTime} " +
            "where c_id = #{id}"
    )
    boolean updateMaker(Maker maker);

    @Delete("delete from t_maker where c_id = #{id}")
    boolean deleteMaker(@Param("id") Long id);

    @Select({"<script>",
            "select * from t_maker where c_name = #{name}",
            "<if test='id != null'>",
            " AND c_id != #{id}",
            "</if>",
            "</script>"})
    @ResultMap("makerMap")
    Maker findMakerByName(@Param("name") String name, @Param("id") Long ignoreId);
}
