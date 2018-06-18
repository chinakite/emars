package com.ideamoment.emars.sale.service.impl;

import com.ideamoment.emars.model.ProductInfo;
import com.ideamoment.emars.product.dao.ProductMapper;
import com.ideamoment.emars.sale.dao.SaleMapper;
import com.ideamoment.emars.sale.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Chinakite on 2018/6/19.
 */
@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductInfo> listProducts() {
        return productMapper.listSaleableProducts();
    }
}
