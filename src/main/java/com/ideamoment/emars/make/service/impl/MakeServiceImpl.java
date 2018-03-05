package com.ideamoment.emars.make.service.impl;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.make.dao.MakeMapper;
import com.ideamoment.emars.make.service.MakeService;
import com.ideamoment.emars.model.MakeTask;
import com.ideamoment.emars.model.Product;
import com.ideamoment.emars.model.ProductQueryVo;
import com.ideamoment.emars.model.ProductResultVo;
import com.ideamoment.emars.model.enumeration.MakeTaskState;
import com.ideamoment.emars.model.enumeration.ProductType;
import com.ideamoment.emars.product.dao.ProductMapper;
import com.ideamoment.emars.utils.Page;
import com.ideamoment.emars.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yukiwang on 2018/3/5.
 */
@Service
public class MakeServiceImpl implements MakeService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private MakeMapper makeMapper;

    @Override
    @Transactional
    public Page<ProductResultVo> pageProducts(ProductQueryVo condition, int offset, int pageSize) {
        condition.setType(ProductType.TEXT);

        long count = productMapper.listProductsCount(condition);
        int currentPage = offset/pageSize + 1;

        List<ProductResultVo> products = productMapper.listProducts(condition, offset, pageSize);

        List<Long> productIds = new ArrayList<Long>();
        for(Product prod : products) {
            productIds.add(prod.getId());
        }

        //TODO 头晕 等下写
//        Map<String, Long> taskCounts = makeMapper.countTaskByProduct(productIds);
//        for(Product prod : products) {
//            if(taskCounts.get(prod.getId()) != null) {
//                prod.setTaskCount(taskCounts.get(prod.getId()).intValue());
//            }else{
//                prod.setTaskCount(0);
//            }
//        }


        Page<ProductResultVo> result = new Page<ProductResultVo>();
        result.setCurrentPage(currentPage);
        result.setData(products);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);

        return result;
    }

    @Override
    @Transactional
    public String saveMakeTask(MakeTask makeTask) {

        Product product = productMapper.findProduct(makeTask.getProductId());

        makeTask.setCreateTime(new Date());
        makeTask.setCreator(UserContext.getUserId());
        makeTask.setName(product.getName());
        makeTask.setState(MakeTaskState.NEW);

        boolean result = makeMapper.insertMakeTask(makeTask);
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
