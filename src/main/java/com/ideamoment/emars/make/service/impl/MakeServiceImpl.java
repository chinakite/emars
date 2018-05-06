package com.ideamoment.emars.make.service.impl;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.make.dao.MakeContractMapper;
import com.ideamoment.emars.make.dao.MakeTaskMapper;
import com.ideamoment.emars.make.service.MakeService;
import com.ideamoment.emars.model.*;
import com.ideamoment.emars.model.enumeration.MakeTaskState;
import com.ideamoment.emars.product.dao.ProductMapper;
import com.ideamoment.emars.utils.Page;
import com.ideamoment.emars.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yukiwang on 2018/3/5.
 */
@Service
public class MakeServiceImpl implements MakeService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private MakeTaskMapper makeTaskMapper;

    @Autowired
    private MakeContractMapper makeContractMapper;

    @Override
    @Transactional
    public Page<ProductInfo> pageProducts(ProductInfo condition, int offset, int pageSize) {
        long count = productMapper.listProductsCount(condition);
        int currentPage = offset/pageSize + 1;

        List<ProductInfo> products = productMapper.listProducts(condition, offset, pageSize);

        List<Long> productIds = new ArrayList<Long>();
        for(ProductInfo prod : products) {
            productIds.add(prod.getId());
        }

        Page<ProductInfo> result = new Page<ProductInfo>();
        result.setCurrentPage(currentPage);
        result.setData(products);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);

        return result;
    }

    @Override
    public Page<MakeContract> pageMakeContracts(MakeContractQueryVo condition, int offset, int pageSize) {
        long count = makeContractMapper.listMakeContractsCount(condition);
        int currentPage = offset/pageSize + 1;
        List<MakeContract> makeContracts = makeContractMapper.listMakeContracts(condition, offset, pageSize);
        Page<MakeContract> result = new Page<MakeContract>();
        result.setCurrentPage(currentPage);
        result.setData(makeContracts);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);
        return result;
    }

    @Override
    @Transactional
    public String saveMakeTask(MakeTask makeTask) {

        ProductInfo product = productMapper.findProduct(makeTask.getProductId());

        makeTask.setCreateTime(new Date());
        makeTask.setCreator(UserContext.getUserId());
        makeTask.setName(product.getName());
        makeTask.setState(MakeTaskState.NEW);

        boolean result = makeTaskMapper.insertMakeTask(makeTask);
        return resultString(result);
    }

    @Override
    @Transactional
    public MakeContract findMakeContract(long id) {
        MakeContract makeContract = makeContractMapper.findMakeContract(id);
        ArrayList<MakeContractProduct> makeContractProducts = makeContractMapper.findMcProductsByMcId(id);
        StringBuilder mcProductIds = new StringBuilder();
        int i = 0;
        for(MakeContractProduct makeContractProduct : makeContractProducts) {
            ArrayList<MakeContractDoc> makeContractDocs = makeContractMapper.listContractDocs(makeContractProduct.getId(), null);
            makeContractProduct.setMakeContractDocs(makeContractDocs);
            i += 1;
            if(i != 1) {
                mcProductIds.append(",");
            }
            mcProductIds.append(String.valueOf(makeContractProduct.getId()));
        }
        makeContract.setMcProducts(makeContractProducts);
        makeContract.setMcProductIds(mcProductIds.toString());
        return makeContract;
    }

    @Override
    @Transactional
    public List<MakeContract> findMakeContractByProductId(long productId) {
        List<MakeContract> makeContract = makeContractMapper.findMakeContractByProductId(productId);
        return makeContract;
    }

    @Override
    @Transactional
    public String saveMakeContract(MakeContract makeContract) {
        boolean ret;
        long userId = UserContext.getUserId();
        Date curDate = new Date();

        if(makeContract.getId() > 0){
            makeContract.setModifier(userId);
            makeContract.setModifyTime(curDate);
            MakeContract oldMackContract = findMakeContract(makeContract.getId());
            ret = makeContractMapper.updateMakeConntract(makeContract);
            ArrayList<MakeContractProduct> products = makeContract.getMcProducts();
            for(MakeContractProduct mcProduct : products) {
                mcProduct.setMakeContractId(makeContract.getId());
                mcProduct.setCreator(userId);
                mcProduct.setCreateTime(curDate);
                makeContractMapper.updateMakeContractProduct(mcProduct);
            }

            //TODO 数组对比

        }else {
            int totalSection = makeContract.getTotalSection();
            int sectionSum = 0;
            BigDecimal totalPrice = makeContract.getTotalPrice();
            BigDecimal priceSum = new BigDecimal(0);

            ArrayList<MakeContractProduct> products = makeContract.getMcProducts();
            for(MakeContractProduct mcProduct : products) {
                sectionSum += mcProduct.getSection();
                priceSum = priceSum.add(mcProduct.getPrice());
            }
            if(sectionSum != totalSection) {
                return ErrorCode.MAKECONTRACT_SECTION_ERROR;
            }
            if(priceSum.compareTo(totalPrice) != 0) {
                return ErrorCode.MAKECONTRACT_PIRCE_ERROR;
            }

            String code = createCode(makeContract);
            makeContract.setCode(code);
            makeContract.setCreator(userId);
            makeContract.setCreateTime(curDate);
            ret = makeContractMapper.insertMakeContract(makeContract);
//            ArrayList<MakeContractProduct> products = makeContract.getMcProducts();

            for(MakeContractProduct mcProduct : products) {
                mcProduct.setMakeContractId(makeContract.getId());
                mcProduct.setCreator(userId);
                mcProduct.setCreateTime(curDate);
                makeContractMapper.insertMakeContractProduct(mcProduct);
            }
        }

        return resultString(ret);
    }

    @Override
    @Transactional
    public List<MakeContractDoc> listContractDocs(long mcProductId, String type) {
        return makeContractMapper.listContractDocs(mcProductId, type);
    }

    @Override
    public List<ProductInfo> listProducts(ProductInfo condition) {
        List<ProductInfo> products = productMapper.listProducts(condition, 0, 1000000000);
        return products;
    }

    @Override
    @Transactional
    public String deleteMakeContract(Long id) {
        boolean ret = makeContractMapper.deleteMakeContract(id);
        if(ret) {
            List<MakeContractProduct> makeContractProducts = makeContractMapper.findMcProductsByMcId(id);
            for(MakeContractProduct makeContractProduct : makeContractProducts) {
                makeContractMapper.deleteMakeContractDocs(makeContractProduct.getId());
            }
            makeContractMapper.deleteMakeContractProducts(id);
        }
        return resultString(ret);
    }

    @Override
    @Transactional
    public MakeContractProduct findMcProduct(long id) {
        return makeContractMapper.findMcProduct(id);
    }

    @Override
    @Transactional
    public String saveMcProductFiles(List<MakeContractDoc> makeContractDocs) {
        Long userId = UserContext.getUserId();
        Date curDate = new Date();

        boolean result = true;
        for(MakeContractDoc makeContractDoc : makeContractDocs) {
            makeContractDoc.setCreator(userId);
            makeContractDoc.setCreateTime(curDate);
            result = result && makeContractMapper.insertMakeContractDoc(makeContractDoc);
        }

        return resultString(result);
    }

    @Override
    @Transactional
    public List<MakeContractProduct> findMcProductsByProductId(long productId) {
        List<MakeContractProduct> makeContractProducts = makeContractMapper.findMcProductsByProductId(productId);
        return makeContractProducts;
    }

    @Override
    @Transactional
    public List<ProductMakeContract> findProductMakeContracts(long productId) {
        List<MakeContract> makeContracts = makeContractMapper.findMakeContractByProductId(productId);
        List<ProductMakeContract> productMakeContracts = new ArrayList<>();
        for(MakeContract makeContract : makeContracts) {
            ProductMakeContract productMakeContract = new ProductMakeContract();
            productMakeContract.setTargetType(makeContract.getTargetType());
            productMakeContract.setCode(makeContract.getCode());
            productMakeContract.setOwner(makeContract.getOwner());
            productMakeContract.setMaker(makeContract.getMaker());
            productMakeContract.setTotalSection(makeContract.getTotalSection());
            productMakeContract.setTotalPrice(makeContract.getTotalPrice());

            MakeContractProduct makeContractProduct = makeContractMapper.findMcProductsByProductIdAndContractId(productId, makeContract.getId());
            productMakeContract.setSection(makeContractProduct.getSection());
            productMakeContract.setPrice(makeContractProduct.getPrice());
            productMakeContract.setWorker(makeContractProduct.getWorker());
            productMakeContract.setMcProductId(makeContractProduct.getId());

            productMakeContracts.add(productMakeContract);
        }

        return productMakeContracts;
    }

    @Override
    @Transactional
    public String deleteMcDoc(long id) {
        boolean ret = makeContractMapper.deleteMakeContractDoc(id);
        return resultString(ret);
    }

    private synchronized String createCode(MakeContract mc) {
        Date curDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String dateStr = df.format(curDate);
        String prefix = "M" + dateStr;
        String maxCode = makeContractMapper.queryMaxContractCode(prefix);

        int intCode = 1;
        if(maxCode != null) {
            String[] codeParts = maxCode.split("-");
            intCode = Integer.parseInt(codeParts[1]);
            intCode++;
        }

        DecimalFormat nf = new DecimalFormat("000");
        String strCode = nf.format(intCode);

        return prefix + "-" + strCode;
    }

    private String resultString(boolean result) {
        if(result) {
            return SuccessCode.SUCCESS;
        }else{
            return ErrorCode.UNKNOWN_ERROR;
        }
    }
}
