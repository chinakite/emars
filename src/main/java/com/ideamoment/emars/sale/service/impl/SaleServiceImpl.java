package com.ideamoment.emars.sale.service.impl;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.customer.dao.CustomerMapper;
import com.ideamoment.emars.model.*;
import com.ideamoment.emars.product.dao.ProductMapper;
import com.ideamoment.emars.sale.dao.SaleMapper;
import com.ideamoment.emars.sale.service.SaleService;
import com.ideamoment.emars.utils.Page;
import com.ideamoment.emars.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Chinakite on 2018/6/19.
 */
@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CustomerMapper customerMapper;

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

        for(Sale sale : saleContracts) {
            Long customerId = sale.getCustomerId();
            Customer customer = customerMapper.findCustomer(customerId);
            sale.setCustomer(customer);
            ArrayList<SaleCustomerPlatform> platforms = saleMapper.listContractPlatforms(sale.getId());
            sale.setPlatforms(platforms);
        }

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

    @Override
    @Transactional
    public String saveSaleContract(Sale saleContract) {
        boolean ret;
        long userId = UserContext.getUserId();
        Date curDate = new Date();

        if(saleContract.getId() > 0){
            saleContract.setModifier(userId);
            saleContract.setModifyTime(curDate);
            Sale oldSaleContract = findSaleContract(saleContract.getId());
            //TODO if not existed
            ret = saleMapper.updateSaleConntract(saleContract);
            ArrayList<SaleProduct> products = saleContract.getProducts();

            ArrayList<SaleProduct> existedSaleContractProds = saleMapper.listContractProducts(saleContract.getId());
            HashSet<Long> existedSaleProdIds = new HashSet<Long>();
            for(SaleProduct saleProd : existedSaleContractProds ) {
                existedSaleProdIds.add(saleProd.getProductId());
            }

            HashSet<Long> newSaleProdIds = new HashSet<Long>();
            for(SaleProduct saleProduct : products) {
                newSaleProdIds.add(saleProduct.getProductId());
                if(existedSaleProdIds.contains(saleProduct.getProductId())) {
                    saleProduct.setModifier(userId);
                    saleProduct.setModifyTime(curDate);
                    saleMapper.updateSaleContractProduct(saleProduct);
                }else {
                    saleMapper.insertSaleContractProduct(saleProduct);
                }
            }

            for(SaleProduct saleProd : existedSaleContractProds ) {
                if(!newSaleProdIds.contains(saleProd.getProductId())) {
                    saleMapper.deleteSaleContractProducts(saleProd.getId());
                }
            }
        }else {
            int totalSection = saleContract.getTotalSection();
            int sectionSum = 0;
            BigDecimal totalPrice = saleContract.getTotalPrice();
            BigDecimal priceSum = new BigDecimal(0);

            ArrayList<SaleProduct> products = saleContract.getProducts();
            for(SaleProduct saleProduct : products) {
                sectionSum += saleProduct.getSection();
                priceSum = priceSum.add(saleProduct.getPrice());
            }
            if(sectionSum != totalSection) {
                return ErrorCode.SALECONTRACT_SECTION_ERROR;
            }
            if(priceSum.compareTo(totalPrice) != 0) {
                return ErrorCode.SALECONTRACT_PIRCE_ERROR;
            }

            saleContract.setCreator(userId);
            saleContract.setCreateTime(curDate);
            ret = saleMapper.insertSaleContract(saleContract);

            for(SaleCustomerPlatform platform : saleContract.getPlatforms()) {
                platform.setSaleId(saleContract.getId());
                platform.setCreator(userId);
                platform.setCreateTime(curDate);
                saleMapper.insertSaleCustomerPlatform(platform);
            }

            for(SaleProduct saleProduct : products) {
                saleProduct.setSaleId(saleContract.getId());
                saleProduct.setCreator(userId);
                saleProduct.setCreateTime(curDate);
                saleMapper.insertSaleProduct(saleProduct);
            }
        }

        return resultString(ret);
    }

    @Override
    @Transactional
    public Sale findSaleContract(long id) {
        Sale saleContract = saleMapper.findSaleContract(id);
        ArrayList<SaleProduct> saleProducts = saleMapper.findSaleProductsBySaleId(id);
        StringBuilder saleProductIds = new StringBuilder();
        int i = 0;
        for(SaleProduct saleProduct : saleProducts) {
            ProductInfo productInfo = productMapper.findProduct(saleProduct.getProductId());
            saleProduct.setProduct(productInfo);

            ArrayList<SaleContractFile> saleContractFiles = saleMapper.listContractFiles(saleProduct.getId(), null);
            saleProduct.setSaleContractFiles(saleContractFiles);
            i += 1;
            if(i != 1) {
                saleProductIds.append(",");
            }
            saleProductIds.append(String.valueOf(saleProduct.getId()));
        }
        saleContract.setProducts(saleProducts);
        saleContract.setProductIds(saleProductIds.toString());
        return saleContract;
    }

    private String resultString(boolean result) {
        if(result) {
            return SuccessCode.SUCCESS;
        }else{
            return ErrorCode.UNKNOWN_ERROR;
        }
    }
}
