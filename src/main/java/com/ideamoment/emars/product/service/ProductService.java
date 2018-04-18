package com.ideamoment.emars.product.service;

import com.ideamoment.emars.model.*;
import com.ideamoment.emars.utils.Page;

import java.util.List;

/**
 * Created by yukiwang on 2018/2/23.
 */
public interface ProductService {

    Page<ProductInfo> listProducts(ProductInfo condition, int offset, int pageSize);

    ProductInfo findProduct(long id);

    String deleteProduct(long id);

    String createProduct(ProductInfo product);

    String updateProduct(ProductInfo product);

    String saveProduct(ProductInfo product, String submit, boolean withoutEva, String type);

    List<ProductCopyrightFile> listProductCopyrightFiles(long productId);

}
