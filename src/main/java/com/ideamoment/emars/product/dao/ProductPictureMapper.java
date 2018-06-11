package com.ideamoment.emars.product.dao;

import com.ideamoment.emars.model.ProductPicture;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Chinakite on 2018/4/24.
 */
@Mapper
public interface ProductPictureMapper {
    @Insert("insert into t_product_picture (c_name, c_type, c_path, c_product_id, c_logo, c_creator, c_createtime)values(#{name}, #{type}, #{path}, #{productId}, #{isLogo}, #{creator}, #{createTime})")
    boolean insertPicture(ProductPicture pic);

    @Select("select * from t_product_picture where c_product_id = #{productId} order by c_createtime asc")
    @Results(id = "pictureMap", value = {
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "name", column = "c_name"),
            @Result(property = "type", column = "c_type"),
            @Result(property = "path", column = "c_path"),
            @Result(property = "productId", column = "c_product_id"),
            @Result(property = "isLogo", column = "c_logo"),
            @Result(property = "desc", column = "c_desc"),
            @Result(property = "creator", column = "c_creator"),
            @Result(property = "createTime", column = "c_createtime")
    })
    List<ProductPicture> queryProductPictures(@Param("productId") String productId);

    @Delete("delete from t_product_picture where c_id = #{id}")
    boolean deleteProductPicture(String id);
}
