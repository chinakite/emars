package com.ideamoment.emars.product.service;

import com.ideamoment.emars.model.*;
import com.ideamoment.emars.utils.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yukiwang on 2018/2/23.
 */
public interface ProductService {

    Page<ProductInfo> listProducts(ProductInfo condition, int offset, int pageSize);

    long listProductsCount(ProductInfo condition);

    ProductInfo findProduct(long id);

    String deleteProduct(long id);

    String createProduct(ProductInfo product);

    String updateProduct(ProductInfo product);

    String saveProduct(ProductInfo product, String submit, boolean withoutEva, String type);

    List<ProductCopyrightFile> listProductCopyrightFiles(long productId);

    String saveProductPictures(ArrayList<ProductPicture> pics);

    List<ProductPicture> loadProductPictureFiles(String productId);

    String deletePicture(String id);

    String stockIn(long id);

    Page<ProductInfo> listStockedInProducts(ProductInfo condition, int start, int length);

    String changeProductionState(long id, String productionState, Long[] announcerIds);

    String packageAllFiles(Long productId);

    String packageToSaleFiles(Long productId);

    List<Map> selectProductCountWithCopyrightType();

    List<Map> selectProductCountWhitSubject();
}
