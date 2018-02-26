package com.ideamoment.emars.product.service.impl;

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
}
