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
    void insertAuthor(Author author);

    @Update("UPDATE t_author (#{author}) WHERE c_id = #{id}")
    @Lang(SimpleUpdateExtendedLanguageDriver.class)
    void updateAuthor(Author author);

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
    Author queryAuthor(@Param("name") String name, @Param("ignoreId") long ignoreId);

    @Delete("DELETE FROM t_author WHERE c_id = #{id}")
    void deleteAuthor(@Param("id") long id);

    @Select("SELECT if((id) > 0, true, false) from T_PRODUCT where C_AUTHOR_ID = #{id} limit 1,1")
    boolean existsProductsByAuthor(long id);
}
