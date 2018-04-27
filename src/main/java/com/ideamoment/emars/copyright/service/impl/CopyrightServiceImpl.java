package com.ideamoment.emars.copyright.service.impl;

import com.ideamoment.emars.author.dao.AuthorMapper;
import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.model.CopyrightProductInfo;
import com.ideamoment.emars.copyright.dao.CopyrightContractProductMapper;
import com.ideamoment.emars.copyright.dao.CopyrightMapper;
import com.ideamoment.emars.copyright.service.CopyrightService;
import com.ideamoment.emars.model.*;
import com.ideamoment.emars.model.enumeration.CopyrightContractState;
import com.ideamoment.emars.model.enumeration.CopyrightFileType;
import com.ideamoment.emars.model.enumeration.ProductState;
import com.ideamoment.emars.model.enumeration.StockInType;
import com.ideamoment.emars.product.dao.ProductMapper;
import com.ideamoment.emars.utils.Page;
import com.ideamoment.emars.utils.StringUtils;
import com.ideamoment.emars.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yukiwang on 2018/2/24.
 */
@Service
public class CopyrightServiceImpl implements CopyrightService {

    @Autowired
    private CopyrightMapper copyrightMapper;

    @Autowired
    private CopyrightContractProductMapper copyrightContractProductMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private AuthorMapper authorMapper;

    @Override
    @Transactional
    public Page<CopyrightContract> listCopyrights(CopyrightContract condition, int offset, int pageSize) {
        long count = copyrightMapper.countCopyrights(condition);
        int currentPage = offset/pageSize + 1;

        List<CopyrightContract> authors = copyrightMapper.listCopyrights(condition, offset, pageSize);

        Page<CopyrightContract> result = new Page<CopyrightContract>();
        result.setCurrentPage(currentPage);
        result.setData(authors);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);

        return result;
    }

    @Override
    @Transactional
    public CopyrightContract findCopyright(long id) {
        CopyrightContract copyright = copyrightMapper.findCopyright(id);
        ArrayList<CopyrightProductInfo> products = copyrightMapper.queryCopyrightProductInfoes(id);
        for(CopyrightProductInfo product : products) {
            List<Author> authors = authorMapper.queryAuthorByProduct(product.getProductId());
            product.setAuthors(authors);
        }
        copyright.setProducts(products);
        return copyright;
    }

    @Override
    @Transactional
    public String saveCopyrightContract(Copyright cc, long[] productIdArr, String[] priceArr, int submit, String type) {
        boolean ret;
        if(submit == 0) {

        }else{

        }

        long userId = UserContext.getUserId();

        Date curTime = new Date();

        if(("0").equals(type)) {
            String code = createCode(cc);
            cc.setCode(code);

            cc.setCreator(userId);
            cc.setCreateTime(curTime);
            cc.setModifier(userId);
            cc.setModifyTime(curTime);

            cc.setAuditState(CopyrightContractState.MANAGER_AUDIT);

//            if(cc.getTotalPrice().floatValue() < 5000) {
//                cc.setAuditState(CopyrightContractState.AUDIT_FINISH);
//            }else{
//                cc.setAuditState(CopyrightContractState.DIRECTOR_AUDIT);
//                make.setRoleId(RoleType.COPYRIGHT_DIRECTOR);
//            }
            ret = copyrightMapper.insertCopyright(cc);
        }else {
            cc.setModifier(userId);
            cc.setModifyTime(curTime);

            cc.setAuditState(CopyrightContractState.MANAGER_AUDIT);

//            if(cc.getTotalPrice().floatValue() < 5000) {
//                cc.setAuditState(CopyrightContractState.AUDIT_FINISH);
//            }else{
//                cc.setAuditState(CopyrightContractState.DIRECTOR_AUDIT);
//                make.setRoleId(RoleType.COPYRIGHT_DIRECTOR);
//            }
            ret = copyrightMapper.updateCopyright(cc);

        }

        long ccId = cc.getId();
        ret = copyrightMapper.deleteContractProduct(ccId);
        int i=0;
        for(long productId : productIdArr) {
            CopyrightContractProduct ccp = new CopyrightContractProduct();
            ccp.setProductId(productId);
            ccp.setContractId(ccId);
            ccp.setPrice(new BigDecimal(priceArr[i]));
            i++;
            ret = copyrightContractProductMapper.insertCopyrightContractProduct(ccp);

            ret = productMapper.updateProductState(productId, ProductState.CP_CONTRACT_INFLOW);
        }

        return resultString(ret);
    }

    @Override
    @Transactional
    public List<Copyright> listProductContracts(long productId) {
        return copyrightMapper.listProductContracts(productId);
    }

    @Override
    @Transactional
    public String createCopyrightContract(CopyrightContract copyrightContract) {
        Date curDate = Calendar.getInstance().getTime();
        long userId = UserContext.getUserId();
        copyrightContract.setCreator(userId);
        copyrightContract.setCreateTime(new Date());

        ArrayList<CopyrightProductInfo> products = copyrightContract.getProducts();
        for(CopyrightProductInfo product : products) {
            String productName = product.getName();
            String productIsbn = product.getIsbn();
            ProductInfo existedProduct = productMapper.checkProductDuplicated(productName, null);
            if(existedProduct != null) {
                continue;
            }
            existedProduct = productMapper.checkIsbnDuplicated(productIsbn, null);
            if(existedProduct != null) {
                continue;
            }
        }

        for(CopyrightProductInfo product : products) {
            product.setCreator(UserContext.getUserId());
            product.setCreateTime(curDate);
            product.setType(copyrightContract.getContractType());
            product.setStockIn(StockInType.NOT_STOCK_IN);

            productMapper.insertProductInfo(product);
            product.setProductId(product.getId());

            //处理作者逻辑
            List<Author> authors = product.getAuthors();
            for(int i=0; i<authors.size(); i++) {
                ProductAuthor productAuthor = new ProductAuthor();
                productAuthor.setProductId(product.getId());
                productAuthor.setAuthorId(authors.get(i).getId());
                productAuthor.setCreator(UserContext.getUserId());
                productAuthor.setCreateTime(curDate);
                productMapper.insertProductAuthor(productAuthor);
            }
        }
        copyrightMapper.insertCopyrightContract(copyrightContract);
        for(CopyrightProductInfo product : products) {
            product.setCopyrightId(copyrightContract.getId());
            copyrightContractProductMapper.insertCopyrightProduct(product);
        }
        return "success";
    }

    @Override
    @Transactional
    public String removeCopyright(Long id) {
        boolean result = copyrightMapper.deleteCopyrightProducts(id);
        result = result && copyrightMapper.deleteCopyrightProductsRelations(id);
        result = result && copyrightMapper.deleteCopyright(id);
        return resultString(result);
    }

    @Override
    @Transactional
    public String updateCopyrightContract(CopyrightContract copyrightContract) {
        CopyrightContract existedCopyrightContract = copyrightMapper.findCopyright(copyrightContract.getId());

        if(existedCopyrightContract == null) {
            return ErrorCode.COPYRIGHT_NOT_EXISTS;
        }

        Date curDate = new Date();
        existedCopyrightContract.setContractCode(copyrightContract.getContractCode());
        existedCopyrightContract.setContractType(copyrightContract.getContractType());
        existedCopyrightContract.setGranterId(copyrightContract.getGranterId());
        existedCopyrightContract.setGranteeId(copyrightContract.getGranteeId());
        existedCopyrightContract.setOperator(copyrightContract.getOperator());
        existedCopyrightContract.setSignDate(copyrightContract.getSignDate());
        existedCopyrightContract.setProjectCode(copyrightContract.getProjectCode());
        existedCopyrightContract.setModifier(UserContext.getUserId());
        existedCopyrightContract.setModifyTime(curDate);

        boolean result = copyrightMapper.updateCopyrightContract(existedCopyrightContract);

        ArrayList<CopyrightProductInfo> products = copyrightContract.getProducts();
        ArrayList<Long> curProductIds = new ArrayList<Long>();
        for(CopyrightProductInfo productInfo : products) {
            if(productInfo.getId() > 0) {
                curProductIds.add(productInfo.getId());
            }
        }

        ArrayList<Long> toDeleteProductIds = null;
        if(curProductIds.size() > 0) {
            toDeleteProductIds = copyrightMapper.queryCopyrightProductIdsToDelete(copyrightContract.getId(), curProductIds);
        }else{
            toDeleteProductIds = copyrightMapper.queryCopyrightProductIds(copyrightContract.getId());
        }

        if(toDeleteProductIds != null && toDeleteProductIds.size() > 0) {
            for(Long productId : toDeleteProductIds) {
                copyrightMapper.deleteCopyrightProductInfo(copyrightContract.getId(), productId);
                productMapper.deleteProductInfo(productId);
            }
        }

        for(CopyrightProductInfo product : products) {
            if(product.getId() == 0) {
                String productName = product.getName();
                String productIsbn = product.getIsbn();
                ProductInfo existedProduct = productMapper.checkProductDuplicated(productName, null);
                if(existedProduct != null) {
                    continue;
                }
                existedProduct = productMapper.checkIsbnDuplicated(productIsbn, null);
                if(existedProduct != null) {
                    continue;
                }

                product.setCreator(UserContext.getUserId());
                product.setCreateTime(curDate);

                productMapper.insertProductInfo(product);
                product.setProductId(product.getId());

                //处理作者逻辑
                List<Author> authors = product.getAuthors();
                for(int i=0; i<authors.size(); i++) {
                    ProductAuthor productAuthor = new ProductAuthor();
                    productAuthor.setProductId(product.getId());
                    productAuthor.setAuthorId(authors.get(i).getId());
                    productAuthor.setCreator(UserContext.getUserId());
                    productAuthor.setCreateTime(curDate);
                    productMapper.insertProductAuthor(productAuthor);
                }

                product.setCopyrightId(copyrightContract.getId());
                copyrightContractProductMapper.insertCopyrightProduct(product);
            }else{
                String productName = product.getName();
                String productIsbn = product.getIsbn();
                ProductInfo existedProduct = productMapper.checkProductDuplicated(productName, product.getId());
                if(existedProduct != null) {
                    continue;
                }
                existedProduct = productMapper.checkIsbnDuplicated(productIsbn, product.getId());
                if(existedProduct != null) {
                    continue;
                }

                product.setCreator(UserContext.getUserId());
                product.setCreateTime(curDate);

                //处理作者逻辑
                List<Author> existedAuthors = authorMapper.queryAuthorByProduct(product.getId());
                HashSet<Long> existedAuthorIdCache = new HashSet<Long>();
                for(Author existedAuthor : existedAuthors) {
                    existedAuthorIdCache.add(existedAuthor.getId());
                }
                HashSet<Long> newAuthorIdCache = new HashSet<Long>();
                for(Author newAuthor : product.getAuthors()) {
                    newAuthorIdCache.add(newAuthor.getId());
                }
                for(Author existedAuthor : existedAuthors) {
                    if(!newAuthorIdCache.contains(existedAuthor.getId())) {
                        productMapper.deleteProductAuthor(product.getId(), existedAuthor.getId());
                    }
                }
                for(Author newAuthor : product.getAuthors()) {
                    if(!existedAuthorIdCache.contains(newAuthor.getId())) {
                        ProductAuthor prodAuthor = new ProductAuthor();
                        prodAuthor.setProductId(product.getId());
                        prodAuthor.setAuthorId(newAuthor.getId());
                        prodAuthor.setCreator(UserContext.getUserId());
                        prodAuthor.setCreateTime(curDate);
                        productMapper.insertProductAuthor(prodAuthor);
                    }
                }

                productMapper.updateProductInfo(product);
                copyrightContractProductMapper.updateContractProductInfo(product);
            }
        }

        return resultString(result);
    }

    @Override
    @Transactional
    public String saveCopyrightFiles(List<CopyrightFile> copyrightFiles) {
        Long userId = UserContext.getUserId();
        Date curDate = new Date();

        boolean result = true;
        for(CopyrightFile copyrightFile : copyrightFiles) {
            copyrightFile.setCreator(userId);
            copyrightFile.setCreateTime(curDate);
            result = result && copyrightMapper.insertCopyrightFile(copyrightFile);
        }

        return resultString(result);
    }

    @Override
    @Transactional
    public List<CopyrightFile> loadCopyrightContractFiles(String productId) {
        return copyrightMapper.queryCopyrightFiles(productId, CopyrightFileType.CONTRACT);
    }

    @Override
    @Transactional
    public String deleteCopyrightFile(String id) {
        boolean result = copyrightMapper.deleteCopyrightFile(id);
        return resultString(result);
    }

    @Override
    @Transactional
    public CopyrightFile findCopyrightFile(Long fileId) {
        CopyrightFile file = copyrightMapper.findCopyrightFile(fileId);
        if(file == null) {
            return file;
        }else{
            return file;
        }
    }

    @Override
    @Transactional
    public List<CopyrightFile> loadCopyrightPageFiles(String productId) {
        return copyrightMapper.queryCopyrightFiles(productId, CopyrightFileType.COPYRIGHT_PAGE);
    }

    @Override
    @Transactional
    public List<CopyrightFile> loadAuthorIdCardFiles(String productId) {
        return copyrightMapper.queryCopyrightFiles(productId, CopyrightFileType.AUTHOR_ID_CARD);
    }

    @Override
    @Transactional
    public List<CopyrightFile> loadGrantPaperFiles(String productId) {
        return copyrightMapper.queryCopyrightFiles(productId, CopyrightFileType.GRANT_PAPER);
    }

    @Override
    @Transactional
    public List<CopyrightFile> loadPublishContractFiles(String productId) {
        return copyrightMapper.queryCopyrightFiles(productId, CopyrightFileType.PUBLISH_CONTRACT);
    }

    private synchronized String createCode(Copyright cc) {
        Date curDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String dateStr = df.format(curDate);
        String prefix = "C" + dateStr;
        String maxCode = copyrightMapper.queryMaxContractCode(prefix);

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
