package com.ideamoment.emars.product.service;

import com.ideamoment.emars.model.Product;
import com.ideamoment.emars.model.ProductQueryVo;
import com.ideamoment.emars.model.ProductResultVo;
import com.ideamoment.emars.utils.Page;

/**
 * Created by yukiwang on 2018/2/23.
 */
public interface ProductService {

    Page<ProductResultVo> listProducts(ProductQueryVo condition, int offset, int pageSize);

    ProductResultVo findProduct(long id);

    String deleteProduct(long id);

    String createProduct(Product product);

    String updateProduct(Product product);

}
