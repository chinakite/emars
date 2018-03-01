package com.ideamoment.emars.product.service.impl;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.model.Product;
import com.ideamoment.emars.model.ProductQueryVo;
import com.ideamoment.emars.model.ProductResultVo;
import com.ideamoment.emars.product.dao.ProductMapper;
import com.ideamoment.emars.product.service.ProductService;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yukiwang on 2018/2/23.
 */
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductMapper productMapper;

    @Override
    @Transactional
    public Page<ProductResultVo> listProducts(ProductQueryVo condition, int offset, int pageSize) {
        long count = productMapper.listProductsCount(condition);
        int currentPage = offset/pageSize + 1;

        List<ProductResultVo> authors = productMapper.listProducts(condition, offset, pageSize);

        Page<ProductResultVo> result = new Page<ProductResultVo>();
        result.setCurrentPage(currentPage);
        result.setData(authors);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);

        return result;
    }

    @Override
    @Transactional
    public ProductResultVo findProduct(long id) {
        return productMapper.findProduct(id);
    }

    @Override
    @Transactional
    public String deleteProduct(long id) {
        boolean r = productMapper.existsCopyrightByProduct(id);
        if(r) {
            return ErrorCode.PRODUCT_CANNOT_DELETE;
        }
        boolean result = productMapper.deleteProduct(id);
        return resultString(result);
    }

    @Override
    @Transactional
    public String createProduct(Product product) {
        Product existsProduct = productMapper.queryProduct(product.getName(), null);

        if(existsProduct != null) {
            return ErrorCode.PRODUCT_EXISTS;
        }
        boolean result = productMapper.insertProduct(product);
        return resultString(result);
    }

    @Override
    @Transactional
    public String updateProduct(Product product) {
        Product existsProduct = productMapper.queryProduct(product.getName(), null);

        if(existsProduct == null) {
            return ErrorCode.PRODUCT_NOT_EXISTS;
        }
        boolean result = productMapper.updateProduct(product);
        return resultString(result);
    }

    private String resultString(boolean result) {
        if(result) {
            return SuccessCode.SUCCESS;
        }else{
            return ErrorCode.UNKNOWN_ERROR;
        }
    }
}
