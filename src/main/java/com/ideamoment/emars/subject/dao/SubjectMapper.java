package com.ideamoment.emars.subject.dao;

import com.ideamoment.emars.model.Subject;
import java.util.List;
import org.apache.ibatis.annotations.*;

/**
 * Created by yukiwang on 2018/2/16.
 */
@Mapper
public interface SubjectMapper {

    //TODO 完善insert语句
    @Insert("INSERT INTO t_subject")
    void insertSubject(@Param("subject") Subject subject);

    //TODO 完善update语句
    @Update("UPDATE t_subject")
    void updateSubject(@Param("subject") Subject subject);

    @Select("SELECT * FROM t_subject WHERE id = #{id}")
    Subject findSubject(@Param("id") String id);

    @Select({"<script>",
            "SELECT * FROM t_subject",
            "WHERE c_type = #{type} ",
            "<if test='key != null'>",
            " AND c_name like %#{key}%",
            "</if>",
            " ORDER BY c_order asc",
            " LIMIT #{offset}, #{size}",
            "</script>"})
    List<Subject> listSubjects(@Param("type") String type, @Param("key") String key, @Param("offset")int offset, @Param("size")int size);

    @Select({"<script>",
            "SELECT COUNT(*) FROM t_subject",
            "WHERE c_type = #{type} ",
            "<if test='key != null'>",
            " AND c_name like %#{key}%",
            "</if>",
            "</script>"})
    long listSubjectsCount(@Param("type") String type, @Param("key") String key);

    @Select({"<script>",
                "SELECT * FROM t_subject" +
                "WHERE c_type = #{type} ",
                "<if test=\"mask = true\">" +
                        " AND c_name like %#{name}%" +
                "</if>",
                "<if test=\"mask = false\">" +
                        " AND c_name = #{name}" +
                "</if>",
                " ORDER BY c_order asc",
            "</script>"})
    List<Subject> querySubject(@Param("type") String type, @Param("name") String name, @Param("mask") boolean mask);

    @Select("SELECT MAX(C_ORDER) FROM t_subject WHERE c_type = #{type}")
    int queryMaxOrder(@Param("type") String type);

    @Select("SELECT * FROM t_subject WHERE c_type = #{type} AND c_name = #{name} AND c_id = #{id}")
    List<Subject> querySubjectExceptSelf(@Param("type") String type, @Param("name")String name, @Param("id") String id);

    @Select("SELECT if((id) > 0, true, false) from T_PRODUCT_SUBJECT where C_SUBJECT_ID = #{id} limit 1,1")
    boolean checkExistsProductsOfSubject(@Param("id") String id);

    @Delete("DELETE FROM t_subject WHERE c_id = #{idArray}")
    void deleteSubject(@Param("id") String id);

    @Delete("DELETE FROM t_subject WHERE c_id in (#{idArray})")
    void batchDeleteSubjects(@Param("idArray") String[] idArray);

    @Select("SELECT * FROM t_subject WHERE c_type = #{type} AND c_order = #{order}")
    Subject querySubjectByOrder(@Param("type") String type, @Param("order") int order);
}
