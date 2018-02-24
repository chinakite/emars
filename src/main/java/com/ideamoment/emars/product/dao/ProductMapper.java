package com.ideamoment.emars.product.dao;

import com.ideamoment.emars.model.ProductQueryVo;
import com.ideamoment.emars.model.ProductResultVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by yukiwang on 2018/2/23.
 */
@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM t_product WHERE c_id = #{id}")
    @Results(id = "productMap", value = {
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "name", column = "C_NAME"),
            @Result(property = "subjectId", column = "C_SUBJECT_ID"),
            @Result(property = "authorId", column = "C_AUTHOR_ID"),
            @Result(property = "type", column = "C_TYPE"),
            @Result(property = "publishState", column = "C_PUBLISH_STATE"),
            @Result(property = "publishYear", column = "C_PUBLISH_YEAR"),
            @Result(property = "finishYear", column = "C_FINISH_YEAR"),
            @Result(property = "state", column = "C_STATE"),
            @Result(property = "wordCount", column = "C_WORD_COUNT"),
            @Result(property = "sectionCount", column = "C_SECTION_COUNT"),
            @Result(property = "sectionLength", column = "C_SECTION_LENGTH"),
            @Result(property = "press", column = "C_PRESS"),
            @Result(property = "website", column = "C_WEBSITE"),
            @Result(property = "summary", column = "C_SUMMARY"),
            @Result(property = "hasAudio", column = "C_HAS_AUDIO"),
            @Result(property = "audioCopyright", column = "C_AUDIO_COPYRIGHT"),
            @Result(property = "audioDesc", column = "C_AUDIO_DESC"),
            @Result(property = "isbn", column = "C_ISBN"),
            @Result(property = "logoUrl", column = "C_LOGO_URL"),
            @Result(property = "reserved", column = "C_RESERVED"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME"),
            @Result(property = "authorName", column = "AUTHORNAME"),
            @Result(property = "subjectName", column = "SUBJECTNAME"),

    })
    ProductResultVo findProduct(@Param("id") long id);

    @Select({"<script>",
            "SELECT p.*, a.c_name AS AUTHORNAME, s.c_name AS SUBJECTNAME FROM t_product p LEFT JOIN t_author a ON p.c_author_id = a.c_id LEFT JOIN t_subject s ON p.c_subject_id = s.c_id",
            "WHERE p.c_id > 0",
            "<if test='condition.productName != null'>",
            " AND p.C_NAME like concat(concat('%',#{condition.productName}),'%')",
            "</if>",
            "<if test='condition.authorName != null'>",
            " AND a.C_NAME like concat(concat('%',#{condition.authorName}),'%')",
            "</if>",
            "<if test='condition.isbn != null'>",
            " AND p.C_ISBN = #{condition.isbn}",
            "</if>",
            "<if test='condition.subjectId != null'>",
            " AND p.C_SUBJECT_ID = #{condition.subjectId}",
            "</if>",
            "<if test='condition.publishState != null'>",
            " AND p.C_PUBLISH_STATE like concat(concat('%',#{condition.publishState}),'%')",
            "</if>",
            "<if test='condition.state != null'>",
            " AND p.C_STATE like concat(concat('%',#{condition.state}),'%')",
            "</if>",
            " ORDER BY p.C_MODIFYTIME DESC ",
            " LIMIT #{offset}, #{size}",
            "</script>"})
    @ResultMap("productMap")
    List<ProductResultVo> listProducts(@Param("condition") ProductQueryVo condition, @Param("offset") int offset, @Param("size") int size);

    @Select({"<script>",
            "SELECT COUNT(*) FROM t_product p LEFT JOIN t_author a ON p.c_author_id = a.c_id LEFT JOIN t_subject s ON p.c_subject_id = s.c_id",
            "WHERE p.c_id > 0",
            "<if test='productName != null'>",
            " AND p.C_NAME like concat(concat('%',#{productName}),'%')",
            "</if>",
            "<if test='authorName != null'>",
            " AND a.C_NAME like concat(concat('%',#{authorName}),'%')",
            "</if>",
            "<if test='isbn != null'>",
            " AND p.C_ISBN = #{isbn}",
            "</if>",
            "<if test='subjectId != null'>",
            " AND p.C_SUBJECT_ID = #{subjectId}",
            "</if>",
            "<if test='publishState != null'>",
            " AND p.C_PUBLISH_STATE like concat(concat('%',#{publishState}),'%')",
            "</if>",
            "<if test='state != null'>",
            " AND p.C_STATE like concat(concat('%',#{state}),'%')",
            "</if>",
            "</script>"})
    long listProductsCount(ProductQueryVo condition);

}
