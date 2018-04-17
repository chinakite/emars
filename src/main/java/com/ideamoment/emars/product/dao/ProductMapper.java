package com.ideamoment.emars.product.dao;

import com.ideamoment.emars.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by yukiwang on 2018/2/23.
 */
@Mapper
public interface ProductMapper {

    @Insert("INSERT INTO t_product " +
            "(`C_NAME`,`C_SUBJECT_ID`,`C_AUTHOR_ID`,`C_TYPE`,`C_PUBLISH_STATE`,`C_PUBLISH_YEAR`,`C_FINISH_YEAR`," +
            "`C_STATE`,`C_WORD_COUNT`,`C_SECTION_COUNT`,`C_SECTION_LENGTH`,`C_PRESS`,`C_WEBSITE`,`C_SUMMARY`," +
            "`C_HAS_AUDIO`,`C_AUDIO_COPYRIGHT`,`C_AUDIO_DESC`,`C_ISBN`,`C_LOGO_URL`,`C_RESERVED`,`C_CREATOR`," +
            "`C_CREATETIME`) " +
            "VALUES " +
            "(#{name}, #{subjectId}, #{authorId}, #{type}, #{publishState}, #{publishYear}, #{finishYear}, " +
            "#{state}, #{wordCount}, #{sectionCount}, #{sectionLength}, #{press}, #{website}, #{summary}, " +
            "#{hasAudio}, #{audioCopyright}, #{audioDesc}, #{isbn}, #{logoUrl}, #{reserved}, #{creator}, " +
            "#{createTime})")
    boolean insertProduct(Product product);

    @Update("UPDATE t_product SET " +
            "`C_NAME`=#{name},`C_SUBJECT_ID`=#{subjectId},`C_AUTHOR_ID`=#{authorId},`C_TYPE`=#{type}," +
            "`C_PUBLISH_STATE`=#{publishState},`C_PUBLISH_YEAR`=#{publishYear},`C_FINISH_YEAR`=#{finishYear}," +
            "`C_STATE`=#{state},`C_WORD_COUNT`=#{wordCount},`C_SECTION_COUNT`=#{sectionCount}," +
            "`C_SECTION_LENGTH`=#{sectionLength},`C_PRESS`=#{press},`C_WEBSITE`=#{website},`C_SUMMARY`=#{summary}," +
            "`C_HAS_AUDIO`=#{hasAudio},`C_AUDIO_COPYRIGHT`=#{audioCopyright},`C_AUDIO_DESC`=#{audioDesc}," +
            "`C_ISBN`=#{isbn},`C_LOGO_URL`=#{logoUrl},`C_RESERVED`=#{reserved},`C_MODIFIER`=#{modifier}," +
            "`C_MODIFYTIME`=#{modifyTime} WHERE c_id = #{id}")
    boolean updateProduct(Product product);

    @Update("UPDATE t_product SET c_type = #{type} WHERE c_id = #{id}")
    boolean updateProductState(@Param("id") long id, @Param("state") String state);

    @Select("SELECT * FROM t_product WHERE c_id = #{id}")
    @Results(id = "productMap", value = {
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "name", column = "C_NAME"),
            @Result(property = "subjectId", column = "C_SUBJECT_ID"),
            @Result(property = "authorId", column = "C_AUTHOR_ID"),
            @Result(property = "type", column = "C_TYPE"),
            @Result(property = "publishState", column = "C_PUBLISH_STATE"),
            @Result(property = "wordCount", column = "C_WORD_COUNT"),
            @Result(property = "press", column = "C_PRESS"),
            @Result(property = "isbn", column = "C_ISBN"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME"),
            @Result(property = "authorName", column = "AUTHORNAME"),
            @Result(property = "subjectName", column = "SUBJECTNAME"),
            @Result(property = "authorPseudonym", column = "AUTHORPSEUDONYM")
    })
    ProductResultVo findProduct(@Param("id") long id);

    @Select({"<script>",
            "SELECT p.*, a.c_name AS authorName, a.c_pseudonym AS authorPseudonym, s.c_name AS subjectName FROM t_product_info p LEFT JOIN t_author a ON p.c_author_id = a.c_id LEFT JOIN t_subject s ON p.c_subject_id = s.c_id",
            "WHERE p.c_id > 0",
            "<if test='condition.name != null'>",
            " AND p.C_NAME like concat(concat('%',#{condition.productName}),'%')",
            "</if>",
            "<if test='condition.isbn != null'>",
            " AND p.C_ISBN = #{condition.isbn}",
            "</if>",
            "<if test='condition.subjectId != null'>",
            " AND p.C_SUBJECT_ID = #{condition.subjectId}",
            "</if>",
            " ORDER BY p.C_MODIFYTIME DESC ",
            " LIMIT #{offset}, #{size}",
            "</script>"})
    @ResultMap("productMap")
    List<ProductInfo> listProducts(@Param("condition") ProductInfo condition, @Param("offset") int offset, @Param("size") int size);

    @Select({"<script>",
            "SELECT COUNT(*) FROM t_product_info p ",
            "WHERE p.c_id > 0",
            "<if test='name != null'>",
            " AND p.C_NAME like concat(concat('%',#{name}),'%')",
            "</if>",
            "<if test='isbn != null'>",
            " AND p.C_ISBN = #{isbn}",
            "</if>",
            "<if test='subjectId != null'>",
            " AND p.C_SUBJECT_ID = #{subjectId}",
            "</if>",
            "</script>"})
    long listProductsCount(ProductInfo condition);

    //TODO not this table
    @Select("SELECT if(count(c_id) > 0, true, false) from T_PRODUCT_COPYRIGHT_FILE where C_PRODUCT_ID = #{id}")
    boolean existsCopyrightByProduct(@Param("id") long id);

    @Delete("DELETE FROM t_product WHERE c_id = #{id}")
    boolean deleteProduct(@Param("id") long id);

    @Select({"<script>",
            "SELECT * FROM t_product",
            "WHERE c_name = #{name}",
            "<if test='ignoreId != null'>",
            " AND c_id != #{ignoreId}",
            "</if>",
            "</script>"})
    @ResultMap("productMap")
    Product queryProduct(@Param("name") String name, @Param("ignoreId") Long ignoreId);

    @Select({"<script>",
            "SELECT * FROM t_product",
            "WHERE c_name = #{name}",
            "<if test='id != null'>",
            " AND c_id != #{id}",
            "</if>",
            "</script>"})
    @ResultMap("productMap")
    Product checkProductDuplicated(@Param("name") String name, @Param("id") Long id);

    @Select({"<script>",
            "SELECT * FROM t_product",
            "WHERE c_isbn = #{isbn}",
            "<if test='id != null'>",
            " AND c_id != #{id}",
            "</if>",
            "</script>"})
    @ResultMap("productMap")
    Product checkIsbnDuplicated(@Param("isbn") String isbn, @Param("id") Long id);

    @Insert("insert into t_product_info (`c_name`,`c_author_id`,`c_wordcount`,`c_subject_id`,`c_publish_state`,`c_isbn`,`c_press`,`c_desc`,`c_creator`,`c_createtime`,`c_modifier`,`c_modifytime`)" +
            "values" +
            "(#{name}, #{authorId}, #{wordCount}, #{subjectId}, #{publishState}, #{isbn}, #{press}, #{desc}, #{creator}, #{createTime}, #{modifier}, #{modifyTime})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean insertProductInfo(CopyrightProductInfo productInfo);

    @Delete("delete from t_product_info where c_id = #{productId}")
    boolean deleteProductInfo(@Param("productId") long productId);

    @Update("update t_product_info set " +
            "c_name=#{name}, c_author_id=#{authorId}, c_wordcount=#{wordCount}, " +
            "c_subject_id=#{subjectId}, c_publish_state=#{publishState}, c_isbn=#{isbn}, " +
            "c_press=#{press}, c_desc=#{desc}, c_modifier=#{modifier}, c_modifytime=#{modifyTime} " +
            "where c_id = #{id}"
    )
    boolean updateProductInfo(CopyrightProductInfo product);
}
