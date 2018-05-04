package com.ideamoment.emars.announcer.dao;

import com.ideamoment.emars.model.Announcer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AnnouncerMapper {

    @Insert("INSERT INTO t_announcer (c_name, c_pseudonym, c_desc, c_creator, c_createtime)values(#{name},#{pseudonym},#{desc},#{creator},#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean insertAnnouncer(Announcer announcer);

    @Update("UPDATE t_announcer set c_name=#{name}, c_pseudonym=#{pseudonym}, c_desc=#{desc}, c_creator=#{creator}, c_createtime=#{createTime} WHERE c_id = #{id}")
    boolean updateAnnouncer(Announcer announcer);

    @Select("SELECT * FROM t_announcer WHERE c_id = #{id}")
    @Results(id = "announcerMap", value ={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "name", column = "C_NAME"),
            @Result(property = "idCard", column = "C_IDCARD"),
            @Result(property = "pseudonym", column = "C_PSEUDONYM"),
            @Result(property = "desc", column = "C_DESC"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME")
    })
    Announcer findAnnouncer(@Param("id") long id);

    @Select({"<script>",
            "SELECT * FROM t_announcer",
            "<if test='key != null'>",
            " WHERE (c_name like concat(concat('%',#{key}),'%') OR c_pseudonym like concat(concat('%',#{key}),'%'))",
            "</if>",
            " ORDER BY C_MODIFYTIME DESC",
            " LIMIT #{offset}, #{size}",
            "</script>"})
    @ResultMap("announcerMap")
    List<Announcer> listAnnouncer(@Param("key") String key, @Param("offset")int offset, @Param("size")int size);

    @Select({"<script>",
            "SELECT COUNT(*) FROM t_announcer",
            "<if test='key != null'>",
            " WHERE (c_name like concat(concat('%',#{key}),'%') OR c_pseudonym like concat(concat('%',#{key}),'%'))",
            "</if>",
            "</script>"})
    long listAnnouncerCount(@Param("key") String key);

    @Select({"<script>",
            "SELECT * FROM t_announcer",
            "WHERE c_name = #{name}",
            "<if test='ignoreId != null'>",
            " AND c_id != #{ignoreId}",
            "</if>",
            "</script>"})
    @ResultMap("announcerMap")
    Announcer queryAnnouncer(@Param("name") String name, @Param("ignoreId") Long ignoreId);

    @Delete("DELETE FROM t_announcer WHERE c_id = #{id}")
    boolean deleteAnnouncer(@Param("id") long id);

    @Select("select * from t_announcer order by c_id")
    @ResultMap("announcerMap")
    List<Announcer> listAllAnnouncer();

    @Select("SELECT if(count(c_id) > 0, true, false) from T_PRODUCT_INFO where C_ANNOUNCER_ID = #{id}")
    boolean existsProductsByAnnouncer(long id);

    @Delete({"<script>",
            "DELETE FROM t_announcer WHERE c_id in ",
            "<foreach item='id' index='index' collection='ids'",
            "open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"})
    boolean batchDeleteAnnouncers(long[] ids);
}
