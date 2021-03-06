package com.ideamoment.emars.product.dao;

import com.ideamoment.emars.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by yukiwang on 2018/2/23.
 */
@Mapper
public interface ProductMapper {

    @Insert("INSERT INTO t_product " +
            "(`C_NAME`,`C_SUBJECT_ID`,`C_TYPE`,`C_PUBLISH_STATE`,`C_PUBLISH_YEAR`,`C_FINISH_YEAR`," +
            "`C_STATE`,`C_WORD_COUNT`,`C_SECTION_COUNT`,`C_SECTION_LENGTH`,`C_WEBSITE`,`C_SUMMARY`," +
            "`C_HAS_AUDIO`,`C_AUDIO_COPYRIGHT`,`C_AUDIO_DESC`,`C_ISBN`,`C_LOGO_URL`,`C_RESERVED`,`C_CREATOR`," +
            "`C_CREATETIME`) " +
            "VALUES " +
            "(#{name}, #{subjectId}, #{type}, #{publishState}, #{publishYear}, #{finishYear}, " +
            "#{state}, #{wordCount}, #{sectionCount}, #{sectionLength}, #{website}, #{summary}, " +
            "#{hasAudio}, #{audioCopyright}, #{audioDesc}, #{isbn}, #{logoUrl}, #{reserved}, #{creator}, " +
            "#{createTime})")
    boolean insertProduct(ProductInfo product);

    @Update("update t_product_info set " +
            "c_name=#{name}, c_wordcount=#{wordCount}, " +
            "c_subject_id=#{subjectId}, c_publish_state=#{publishState}, c_isbn=#{isbn}, " +
            "c_type=#{type}, c_stockin=#{stockIn}, c_desc=#{desc}, c_modifier=#{modifier}, c_modifytime=#{modifyTime}," +
            "c_section=#{section}, c_sort=#{sort} " +
            "where c_id = #{id}"
    )
    boolean updateProduct(ProductInfo product);

    @Update("UPDATE t_product SET C_PRODUCTION_STATE = #{state} WHERE c_id = #{id}")
    boolean updateProductState(@Param("id") long id, @Param("state") String state);

    @Select("SELECT p.*, s.c_name AS subjectName, c.c_code as copyrightCode FROM t_product_info p LEFT JOIN t_subject s ON p.c_subject_id = s.c_id LEFT JOIN t_copyright_product cp " +
            "    ON p.c_id = cp.`c_product_id`" +
            "  LEFT JOIN t_copyright c " +
            "    ON c.c_id = cp.`c_copyright_id` WHERE p.c_id = #{id}")
    @Results(id = "productMap", value = {
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "name", column = "C_NAME"),
            @Result(property = "subjectId", column = "C_SUBJECT_ID"),
            @Result(property = "type", column = "C_TYPE"),
            @Result(property = "publishState", column = "C_PUBLISH_STATE"),
            @Result(property = "wordCount", column = "C_WORDCOUNT"),
            @Result(property = "section", column = "C_SECTION"),
            @Result(property = "isbn", column = "C_ISBN"),
            @Result(property = "stockIn", column = "C_STOCKIN"),
            @Result(property = "desc", column = "C_DESC"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME"),
            @Result(property = "subjectName", column = "SUBJECTNAME"),
            @Result(property = "productionState", column = "C_PRODUCTION_STATE"),
            @Result(property = "sort", column = "C_SORT"),
            @Result(property = "copyrightCode", column = "copyrightCode")
    })
    ProductInfo findProduct(@Param("id") long id);

    @Select({"<script>",
            "SELECT p.*, s.c_name AS subjectName, c.c_code as copyrightCode FROM t_product_info p ",
//            "LEFT JOIN t_author a ON p.c_author_id = a.c_id ",
            "LEFT JOIN t_subject s ON p.c_subject_id = s.c_id ",
            "LEFT JOIN t_copyright_product cp ON p.c_id = cp.`c_product_id` ",
            "LEFT JOIN t_copyright c ON c.c_id = cp.`c_copyright_id` ",
            "WHERE p.c_id > 0",
            "<if test='condition.name != null'>",
            " AND p.C_NAME like concat(concat('%',#{condition.name}),'%')",
            "</if>",
            "<if test='condition.isbn != null'>",
            " AND p.C_ISBN = #{condition.isbn}",
            "</if>",
            "<if test='condition.subjectId != null'>",
            " AND p.C_SUBJECT_ID = #{condition.subjectId}",
            "</if>",
            "<if test='condition.stockIn != null'>",
            " AND p.C_STOCKIN = #{condition.stockIn}",
            "</if>",
            " ORDER BY c.C_CODE DESC, p.C_CREATETIME DESC ",
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
            "<if test='stockIn != null'>",
            " AND p.C_STOCKIN = #{stockIn}",
            "</if>",
            "</script>"})
    long listProductsCount(ProductInfo condition);

    @Select({"<script>",
            "SELECT p.* FROM t_product_info p ",
            "WHERE p.c_id > 0",
            "<if test='condition.name != null'>",
            " AND p.C_NAME like concat(concat('%',#{condition.name}),'%')",
            "</if>",
            "<if test='condition.isbn != null'>",
            " AND p.C_ISBN = #{condition.isbn}",
            "</if>",
            " AND p.c_production_state = '3'",
            " ORDER BY p.C_CREATETIME DESC ",
            "</script>"})
    @ResultMap("productMap")
    List<ProductInfo> listMakableProducts(@Param("condition") ProductInfo condition);

//    @Select({"<script>",
//            "SELECT p.*, s.c_name AS subjectName, c.c_code as copyrightCode FROM t_product_info p ",
////            "LEFT JOIN t_author a ON p.c_author_id = a.c_id ",
//            "LEFT JOIN t_subject s ON p.c_subject_id = s.c_id ",
//            "LEFT JOIN t_copyright_product cp ON p.c_id = cp.`c_product_id` ",
//            "LEFT JOIN t_copyright c ON c.c_id = cp.`c_copyright_id` ",
//            "WHERE p.c_id > 0",
//            "<if test='condition.name != null'>",
//            " AND p.C_NAME like concat(concat('%',#{condition.name}),'%')",
//            "</if>",
//            "<if test='condition.isbn != null'>",
//            " AND p.C_ISBN = #{condition.isbn}",
//            "</if>",
//            "<if test='condition.subjectId != null'>",
//            " AND p.C_SUBJECT_ID = #{condition.subjectId}",
//            "</if>",
//            "<if test='condition.stockIn != null'>",
//            " AND p.C_STOCKIN = #{condition.stockIn}",
//            "</if>",
//            " AND c.c_state = '2'",
//            " ORDER BY p.C_MODIFYTIME DESC ",
//            " LIMIT #{offset}, #{size}",
//            "</script>"})
//    @ResultMap("productMap")
//    List<ProductInfo> listMakableProducts(@Param("condition") ProductInfo condition, @Param("offset") int offset, @Param("size") int size);

    //TODO not this table
    @Select("SELECT if(count(c_id) > 0, true, false) from T_PRODUCT_COPYRIGHT_FILE where C_PRODUCT_ID = #{id}")
    boolean existsCopyrightByProduct(@Param("id") long id);

    @Delete("DELETE FROM t_product WHERE c_id = #{id}")
    boolean deleteProduct(@Param("id") long id);

    @Select({"<script>",
            "SELECT * FROM t_product_info",
            "WHERE c_name = #{name}",
            "<if test='ignoreId != null'>",
            " AND c_id != #{ignoreId}",
            "</if>",
            "</script>"})
    @ResultMap("productMap")
    ProductInfo queryProduct(@Param("name") String name, @Param("ignoreId") Long ignoreId);

    @Select({"<script>",
            "SELECT * FROM t_product_info",
            "WHERE c_name = #{name}",
            "<if test='id != null'>",
            " AND c_id != #{id}",
            "</if>",
            "</script>"})
    @ResultMap("productMap")
    ProductInfo checkProductDuplicated(@Param("name") String name, @Param("id") Long id);

    @Select({"<script>",
            "SELECT * FROM t_product_info",
            "WHERE c_isbn = #{isbn}",
            "<if test='id != null'>",
            " AND c_id != #{id}",
            "</if>",
            "</script>"})
    @ResultMap("productMap")
    ProductInfo checkIsbnDuplicated(@Param("isbn") String isbn, @Param("id") Long id);

    @Insert("insert into t_product_info (`c_name`,`c_wordcount`,`c_subject_id`,`c_publish_state`,`c_isbn`,`c_type`,`c_stockin`,`c_desc`,`c_creator`,`c_createtime`,`c_modifier`,`c_modifytime`,`c_section`, `c_sort`)" +
            "values" +
            "(#{name}, #{wordCount}, #{subjectId}, #{publishState}, #{isbn}, #{type}, #{stockIn}, #{desc}, #{creator}, #{createTime}, #{modifier}, #{modifyTime}, #{section}, #{sort})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean insertProductInfo(CopyrightProductInfo productInfo);

    @Delete("delete from t_product_info where c_id = #{productId}")
    boolean deleteProductInfo(@Param("productId") long productId);

    @Update("update t_product_info set " +
            "c_name=#{name}, c_wordcount=#{wordCount}, " +
            "c_subject_id=#{subjectId}, c_publish_state=#{publishState}, c_isbn=#{isbn}, " +
            "c_type=#{type}, c_stockin=#{stockIn}, c_desc=#{desc}, c_modifier=#{modifier}, c_modifytime=#{modifyTime}," +
            "c_section=#{section}, c_sort=#{sort} " +
            "where c_id = #{id}"
    )
    boolean updateProductInfo(CopyrightProductInfo product);

    @Insert("insert into t_product_author (c_product_id, c_author_id, c_creator, c_createtime)values(#{productId}, #{authorId}, #{creator}, #{createTime})")
    boolean insertProductAuthor(ProductAuthor productAuthor);

    @Delete("delete from t_product_author where c_product_id = #{productId} and c_author_id = #{authorId}")
    boolean deleteProductAuthor(@Param("productId") Long productId, @Param("authorId") Long authorId);

    @Update("update t_product_info set c_production_state = #{productionState} where c_id = #{productId}")
    boolean changeProductionState(@Param("productId") long productId, @Param("productionState") String productionState);

    @Delete("delete from t_reservation_announcer where c_product_id = #{productId}")
    boolean deleteReservationAnnouncers(@Param("productId") long productId);

    @Insert("insert into t_reservation_announcer (c_product_id, c_announcer_id) values (#{productId}, #{announcerId})")
    boolean addReservationAnnouncer(@Param("productId") long productId, @Param("announcerId") long announcerId);

    @Results(id = "reservationAnnouncerMap", value = {
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "productId", column = "c_product_id"),
            @Result(property = "announcerId", column = "c_announcer_id")
    })
    @Select("select * from t_reservation_announcer where c_product_id = #{productId}")
    List<ReservationAnnouncer> queryAnnouncerByProductId(@Param("productId") long productId);

    @Select("select distinct(c.c_type) state, count(*) count from t_product_info p left join t_copyright_product cp on p.c_id = cp.c_product_id left join t_copyright c on cp.c_copyright_id = c.c_id group by c.c_type")
    @Results(id = "productCountMap1", value = {
            @Result(property = "state", column = "state"),
            @Result(property = "count", column = "count")
    })
    List<Map> selectProductCountWithCopyrightType();

    @Select("select distinct(s.c_name) subject_name, count(*) count from t_product_info p left join t_subject s on p.c_subject_id = s.c_id group by p.c_subject_id")
    @Results(id = "productCountMap2", value = {
            @Result(property = "label", column = "subject_name"),
            @Result(property = "data", column = "count")
    })
    List<Map> selectProductCountWhitSubject();

    @Select({"<script>",
            "SELECT p.*, s.c_name AS subjectName, c.c_code as copyrightCode FROM t_product_info p ",
//            "LEFT JOIN t_author a ON p.c_author_id = a.c_id ",
            "LEFT JOIN t_subject s ON p.c_subject_id = s.c_id ",
            "LEFT JOIN t_copyright_product cp ON p.c_id = cp.`c_product_id` ",
            "LEFT JOIN t_copyright c ON c.c_id = cp.`c_copyright_id` ",
            "WHERE p.c_id > 0",
            " AND p.C_STOCKIN = '1'",
            " ORDER BY p.C_MODIFYTIME DESC ",
            "</script>"})
    @ResultMap("productMap")
    List<ProductInfo> listSaleableProducts();
}
