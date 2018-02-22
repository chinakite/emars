package com.ideamoment.emars.subject.dao;

import com.ideamoment.emars.model.Subject;
import java.util.List;

import com.ideamoment.emars.utils.mapper.SimpleInsertExtendedLanguageDriver;
import com.ideamoment.emars.utils.mapper.SimpleUpdateExtendedLanguageDriver;
import org.apache.ibatis.annotations.*;

/**
 * Created by yukiwang on 2018/2/16.
 */
@Mapper
public interface SubjectMapper {

    @Insert("INSERT INTO t_subject (#{subject})")
    @Lang(SimpleInsertExtendedLanguageDriver.class)
    boolean insertSubject(Subject subject);

    @Update("UPDATE t_subject (#{subject}) WHERE c_id = #{id}")
    @Lang(SimpleUpdateExtendedLanguageDriver.class)
    boolean updateSubject(Subject subject);

    @Select("SELECT * FROM t_subject WHERE c_id = #{id}")
    @Results(id = "subjectMap", value ={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "name", column = "C_NAME"),
            @Result(property = "desc", column = "C_DESC"),
            @Result(property = "order", column = "C_ORDER"),
            @Result(property = "type", column = "C_TYPE"),
            @Result(property = "ratio", column = "C_RATIO"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME")
    })
    Subject findSubject(@Param("id") long id);

    @Select({"<script>",
            "SELECT * FROM t_subject",
            "WHERE c_type = #{type} ",
            "<if test='key != null'>",
            " AND c_name like concat(concat('%',#{key}),'%')",
            "</if>",
            " ORDER BY c_order asc",
            " LIMIT #{offset}, #{size}",
            "</script>"})
    @ResultMap("subjectMap")
    List<Subject> listSubjects(@Param("type") String type, @Param("key") String key, @Param("offset")int offset, @Param("size")int size);

    @Select({"<script>",
            "SELECT COUNT(*) FROM t_subject",
            "WHERE c_type = #{type} ",
            "<if test='key != null'>",
            " AND c_name like concat(concat('%',#{key}),'%')",
            "</if>",
            "</script>"})
    long listSubjectsCount(@Param("type") String type, @Param("key") String key);

    @Select({"<script>",
                "SELECT * FROM t_subject",
                "WHERE c_type = #{type} ",
                "<if test='mask = true'>",
                " AND c_name like concat(concat('%',#{name}),'%')",
                "</if>",
                "<if test='mask = false'>",
                " AND c_name = #{name}",
                "</if>",
                " ORDER BY c_order asc",
            "</script>"})
    @ResultMap("subjectMap")
    List<Subject> querySubject(@Param("type") String type, @Param("name") String name, @Param("mask") boolean mask);

    @Select("SELECT MAX(C_ORDER) FROM t_subject WHERE c_type = #{type}")
    int queryMaxOrder(@Param("type") String type);

    @Select("SELECT * FROM t_subject WHERE c_type = #{type} AND c_name = #{name} AND c_id = #{id}")
    @ResultMap("subjectMap")
    List<Subject> querySubjectExceptSelf(@Param("type") String type, @Param("name")String name, @Param("id") long id);

    @Select("SELECT if(count(c_id) > 0, true, false) from T_PRODUCT_SUBJECT where C_SUBJECT_ID = #{id}")
    boolean checkExistsProductsOfSubject(@Param("id") long id);

    @Delete("DELETE FROM t_subject WHERE c_id = #{id}")
    boolean deleteSubject(@Param("id") long id);

//    @Delete("DELETE FROM t_subject WHERE c_id in (#{idArray})")
    @Delete({"<script>",
            "DELETE FROM t_subject WHERE c_id in ",
            "<foreach item='id' index='index' collection='ids'",
            "open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"})
    boolean batchDeleteSubjects(@Param("ids") long[] ids);

    @Select("SELECT * FROM t_subject WHERE c_type = #{type} AND c_order = #{order}")
    @ResultMap("subjectMap")
    Subject querySubjectByOrder(@Param("type") String type, @Param("order") int order);
}
