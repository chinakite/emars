package com.ideamoment.emars.product.dao;

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

}
