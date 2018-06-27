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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    @Override
    @Transactional
    public String generateContractCode(String signDate, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(signDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            Calendar begin = Calendar.getInstance();
            begin.set(c.get(Calendar.YEAR), 0 ,1);

            Calendar end = Calendar.getInstance();
            end.set(c.get(Calendar.YEAR) + 1, 0 ,1);

            long count = saleMapper.countContractsByTimeAndType(begin.getTime(), end.getTime(), type);
            count++;
            String countStr = String.format("%03d", count);
            if(type.equals("1")) {
                type = "xd";
            }else{
                type = "xw";
            }
            String code = c.get(Calendar.YEAR) + "-" + type + "-" + countStr;
            return code;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
