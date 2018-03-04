package com.ideamoment.emars.product.service.impl;

import com.ideamoment.emars.author.service.AuthorService;
import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.model.*;
import com.ideamoment.emars.model.enumeration.ProductState;
import com.ideamoment.emars.model.enumeration.ProductType;
import com.ideamoment.emars.product.dao.ProductMapper;
import com.ideamoment.emars.product.dao.ProductSampleMapper;
import com.ideamoment.emars.product.service.ProductService;
import com.ideamoment.emars.utils.Page;
import com.ideamoment.emars.utils.StringUtils;
import com.ideamoment.emars.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by yukiwang on 2018/2/23.
 */
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ProductSampleMapper productSampleMapper;

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

    @Override
    @Transactional
    public String saveProduct(Product product, String submit, boolean withoutEva, String type) {
        boolean ret;
        String name = product.getName();
        if(name == null || "".equals(name.trim())) {
            return ErrorCode.NAME_REQUIED;
        }

        String checkDuplicated = checkDuplicated(product, product.getId());
        if(checkDuplicated != null) {
            return checkDuplicated;
        }

        Date curTime = new Date();

        Author author = product.getAuthor();
        Author productAuthor = findOrinsertAuthor(author.getName(), author.getPseudonym());
        if(productAuthor == null) {
            return ErrorCode.PRODUCT_AUTHOR_ERROR;
        }
        product.setAuthorId(productAuthor.getId());

        product.setModifier(UserContext.getUserId());
        product.setModifyTime(curTime);

        product.setType(ProductType.TEXT);
        if("1".equals(submit)) {
            if(withoutEva) {
                product.setState(ProductState.NEW_WITHOUT_EVA);
            }else{
                product.setState(ProductState.DRAFT);
            }
        }else {
            if(withoutEva) {
                product.setState(ProductState.NEW_WITHOUT_EVA);
            }else{
                product.setState(ProductState.APPROVE_WAITING);
            }
        }

        if(("0").equals(type)) {
            product.setCreateTime(curTime);
            product.setCreator(UserContext.getUserId());
            ret = productMapper.insertProduct(product);
        }else{
            ret = productMapper.updateProduct(product);
        }

        List<ProductSample> samples = product.getSamples();
        if(samples != null) {
            for (ProductSample sample : samples) {
                sample.setProductId(product.getId());
                ret = productSampleMapper.insertProductSimpleMapper(sample);
            }
        }

        return resultString(ret);
    }


    private String resultString(boolean result) {
        if(result) {
            return SuccessCode.SUCCESS;
        }else{
            return ErrorCode.UNKNOWN_ERROR;
        }
    }

    private String checkDuplicated(Product product, long id) {
        Product prods = productMapper.checkProductDuplicated(product.getName(), id);
        if(prods != null) {
            return ErrorCode.PRODUCT_DUPLICATED;
        }

        if(product.getIsbn() != null && product.getIsbn().trim().length() > 0) {
            prods = productMapper.checkIsbnDuplicated(product.getIsbn(), id);
            if(prods != null) {
                return ErrorCode.ISBN_DUPLICATED;
            }
        }
        return null;
    }

    private Author findOrinsertAuthor(String name, String pseudonym) {
        if(StringUtils.isEmpty(name)) {
            return null;
        }
        Author author = authorService.findAuthorByName(name);
        if(author == null) {
            String result = authorService.createAuthor(name, null, pseudonym);
            if(SuccessCode.SUCCESS.equals(result)) {
                Author newAuthor = authorService.findAuthorByName(name);
                return newAuthor;
            }else {
                return null;
            }
        }else {
            return author;
        }
    }
}
