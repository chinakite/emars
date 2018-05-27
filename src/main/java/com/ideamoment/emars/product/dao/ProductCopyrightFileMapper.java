package com.ideamoment.emars.product.dao;

import com.ideamoment.emars.model.CopyrightFile;
import com.ideamoment.emars.model.ProductCopyrightFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by yukiwang on 2018/3/11.
 */
@Mapper
public interface ProductCopyrightFileMapper {

    @Insert("INSERT INTO t_product_copyright_file " +
            "(`C_NAME`,`C_PRODUCT_ID`,`C_CREATOR_ID`,`C_CREATETIME`,`C_FILE_URL`) " +
            "VALUES (" +
            "#{name}, #{productId}, #{creatorId}, #{createTime}, #{fileUrl})")
    boolean insertProductCopyrightFile(ProductCopyrightFile productCopyrightFile);

    @Select("SELECT * FROM T_PRODUCT_COPYRIGHT_FILE WHERE C_PRODUCT_ID = #{productId} ORDER BY C_CREATETIME DESC")
    @Results(id = "productCopyrightFileMap", value = {
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "name", column = "C_NAME"),
            @Result(property = "productId", column = "C_PRODUCT_ID"),
            @Result(property = "creatorId", column = "C_CREATOR_ID"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "fileUrl", column = "C_FILE_URL")
    })
    List<ProductCopyrightFile> listProductCopyrightFiles(@Param("productId") long productId);


    @Select("select * from t_copyright_file where c_product_id = #{productId}")
    @Results(id = "copyrightFileMap", value = {
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "name", column = "C_NAME"),
            @Result(property = "path", column = "C_PATH"),
            @Result(property = "type", column = "C_TYPE"),
            @Result(property = "desc", column = "C_DESC"),
            @Result(property = "productId", column = "C_PRODUCT_ID"),
            @Result(property = "creatorId", column = "C_CREATOR_ID"),
            @Result(property = "createTime", column = "C_CREATETIME")
    })
    List<CopyrightFile> listCopyrightFiles(@Param("productId") long productId);

    @Delete("delete from t_copyright_file where c_id = #{id}")
    boolean deleteCopyrightFile(@Param("id") long id);

    @Insert("insert into t_copyright_file (c_name, c_product_id, c_type, c_path, c_desc, c_creator, c_createtime)values(#{name}, #{productId}, #{type}, #{path}, #{desc}, #{creator}, #{createTime})")
    boolean insertCopyrightFile(CopyrightFile copyrightFile);

    @Select("select * from t_copyright_file where c_product_id = #{productId} and c_type > 1")
    @ResultMap("copyrightFileMap")
    List<CopyrightFile> listCopyrightToSaleFiles(Long productId);
}
