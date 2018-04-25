package com.ideamoment.emars.make.controller;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.make.service.MakeService;
import com.ideamoment.emars.model.*;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yukiwang on 2018/3/5.
 */
@RestController
@RequestMapping("make")
public class MakeController {

    @Autowired
    private MakeService makeService;

    @RequestMapping(value = "/dtProducts", method = RequestMethod.GET)
    public DataTableSource<ProductInfo> dtProducts(
            int draw,
            int start,
            int length,
            String productName,
            String authorName,
            String isbn,
            Long subjectId,
            String publishState
    ) {
        ProductInfo condition = new ProductInfo();
        condition.setName(productName);
        condition.setAuthorName(authorName);
        condition.setIsbn(isbn);
        condition.setSubjectId(subjectId);
        condition.setPublishState(publishState);
        Page<ProductInfo> products = makeService.pageProducts(condition, start, length);
        DataTableSource<ProductInfo> dts = convertProductsToDataTableSource(draw, products);
        return dts;
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductInfo> products(
    ) {
        ProductInfo condition = new ProductInfo();
        List<ProductInfo> products = makeService.listProducts(condition);
        return products;
    }

    @RequestMapping(value = "/makeTask", method = RequestMethod.POST)
    public JsonData<Boolean> makeTask(
            long productId,
            long makerId,
            long contractId,
            Integer timePerSection,
            String showType,
            String showExpection,
            Integer makeTime,
            String desc
    ) {
        MakeTask makeTask = new MakeTask();
        makeTask.setProductId(productId);
        makeTask.setMakerId(makerId);
        makeTask.setContractId(contractId);
        makeTask.setShowType(showType);
        makeTask.setShowExpection(showExpection);
        makeTask.setMakeTime(makeTime);
        makeTask.setDesc(desc);

        String result = makeService.saveMakeTask(makeTask);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/makeContract", method = RequestMethod.POST)
    public JsonData saveMakeContract(
            @RequestBody MakeContract makeContract
    ) {
        String result = makeService.saveMakeContract(makeContract);

        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    private DataTableSource<ProductInfo> convertProductsToDataTableSource(int draw, Page<ProductInfo> productsPage) {
        DataTableSource<ProductInfo> dts = new DataTableSource<ProductInfo>();

        dts.setDraw(draw);
        dts.setRecordsTotal(productsPage.getTotalRecord());
        dts.setRecordsFiltered(productsPage.getTotalRecord());
        dts.setData(productsPage.getData());

        return dts;
    }

}
