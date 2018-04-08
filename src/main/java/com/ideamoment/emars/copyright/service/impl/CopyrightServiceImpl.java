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
import com.ideamoment.emars.model.enumeration.ProductState;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
            Product existedProduct = productMapper.checkProductDuplicated(productName, null);
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

            //处理作者逻辑
            String authorName = product.getAuthorName();
            String authorPseudonym = product.getAuthorPseudonym();
            Author author = null;
            if(StringUtils.isNotEmpty(authorPseudonym)) {
                author = authorMapper.findAuthorByPseudonym(authorPseudonym);
            }
            if(author != null) {
                product.setAuthorId(author.getId());
            }else{
                author = authorMapper.findAuthorByName(authorName);
                if(author != null) {
                    product.setAuthorId(author.getId());
                }else{
                    author = new Author();
                    author.setName(authorName);
                    author.setPseudonym(authorPseudonym);
                    author.setCreator(UserContext.getUserId());
                    author.setCreateTime(curDate);
                    authorMapper.insertAuthor(author);

                    product.setAuthorId(author.getId());
                }
            }
            productMapper.insertProductInfo(product);
            product.setProductId(product.getId());
        }
        copyrightMapper.insertCopyrightContract(copyrightContract);
        for(CopyrightProductInfo product : products) {
            product.setCopyrightId(copyrightContract.getId());
            copyrightContractProductMapper.insertCopyrightProduct(product);
        }
        return "success";
    }

    @Override
    public String removeCopyright(Long id) {
        boolean result = copyrightMapper.deleteCopyrightProducts(id);
        result = result && copyrightMapper.deleteCopyrightProductsRelations(id);
        result = result && copyrightMapper.deleteCopyright(id);
        return resultString(result);
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
