package com.ideamoment.emars.author.dao;

import com.ideamoment.emars.model.Author;
import com.ideamoment.emars.utils.mapper.SimpleInsertExtendedLanguageDriver;
import com.ideamoment.emars.utils.mapper.SimpleUpdateExtendedLanguageDriver;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by yukiwang on 2018/2/22.
 */
@Mapper
public interface AuthorMapper {

    @Insert("INSERT INTO t_author (#{author})")
    @Lang(SimpleInsertExtendedLanguageDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean insertAuthor(Author author);

    @Update("UPDATE t_author (#{author}) WHERE c_id = #{id}")
    @Lang(SimpleUpdateExtendedLanguageDriver.class)
    boolean updateAuthor(Author author);

    @Select("SELECT * FROM t_author WHERE c_id = #{id}")
    @Results(id = "authorMap", value ={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "name", column = "C_NAME"),
            @Result(property = "idCard", column = "C_IDCARD"),
            @Result(property = "pseudonym", column = "C_PSEUDONYM"),
            @Result(property = "desc", column = "C_DESC"),
            @Result(property = "famous", column = "C_FAMOUS"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME")
    })
    Author findAuthor(@Param("id") long id);

    @Select({"<script>",
            "SELECT * FROM t_author",
            "<if test='key != null'>",
            " WHERE (c_name like concat(concat('%',#{key}),'%') OR c_pseudonym like concat(concat('%',#{key}),'%'))",
            "</if>",
            " ORDER BY C_MODIFYTIME DESC",
            " LIMIT #{offset}, #{size}",
            "</script>"})
    @ResultMap("authorMap")
    List<Author> listAuthors(@Param("key") String key, @Param("offset")int offset, @Param("size")int size);

    @Select({"<script>",
            "SELECT COUNT(*) FROM t_author",
            "<if test='key != null'>",
            " WHERE (c_name like concat(concat('%',#{key}),'%') OR c_pseudonym like concat(concat('%',#{key}),'%'))",
            "</if>",
            "</script>"})
    long listAuthorsCount(@Param("key") String key);

    @Select({"<script>",
            "SELECT * FROM t_author",
            "WHERE c_name = #{name}",
            "<if test='ignoreId != null'>",
            " AND c_id != #{ignoreId}",
            "</if>",
            "</script>"})
    @ResultMap("authorMap")
    Author queryAuthor(@Param("name") String name, @Param("ignoreId") Long ignoreId);

    @Delete("DELETE FROM t_author WHERE c_id = #{id}")
    boolean deleteAuthor(@Param("id") long id);

    @Delete({"<script>",
            "DELETE FROM t_author WHERE c_id in ",
            "<foreach item='id' index='index' collection='ids'",
            "open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"})
    boolean batchDeleteAuthors(@Param("ids") long[] ids);

    @Select("SELECT if(count(c_id) > 0, true, false) from T_PRODUCT where C_AUTHOR_ID = #{id}")
    boolean existsProductsByAuthor(long id);

    @Select("select * from t_author where c_pseudonym = #{pseudonym}")
    @ResultMap("authorMap")
    Author findAuthorByPseudonym(@Param("pseudonym") String pseudonym);

    @Select("select * from t_author where c_name = #{name}")
    @ResultMap("authorMap")
    Author findAuthorByName(@Param("name") String name);
}
