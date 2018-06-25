package com.ideamoment.emars.sale.service.impl;

import com.ideamoment.emars.model.ProductInfo;
import com.ideamoment.emars.model.Sale;
import com.ideamoment.emars.model.SaleContractQueryVo;
import com.ideamoment.emars.product.dao.ProductMapper;
import com.ideamoment.emars.sale.dao.SaleMapper;
import com.ideamoment.emars.sale.service.SaleService;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public List<ProductInfo> listProducts() {
        return productMapper.listSaleableProducts();
    }

    @Override
    @Transactional
    public Page<Sale> pageSaleContracts(SaleContractQueryVo condition, int offset, int pageSize) {
        long count = saleMapper.countSaleContracts(condition);
        int currentPage = offset/pageSize + 1;
        List<Sale> saleContracts = saleMapper.pageSaleContracts(condition, offset, pageSize);
        Page<Sale> result = new Page<Sale>();
        result.setCurrentPage(currentPage);
        result.setData(saleContracts);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);
        return result;
    }

    @Override
    @Transactional
    public long countSaleContracts(SaleContractQueryVo condition) {
        long count = saleMapper.countSaleContracts(condition);
        return count;
    }
}
