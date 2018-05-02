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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yukiwang on 2018/3/5.
 */
@RestController
@RequestMapping("make")
public class MakeController {

    @Autowired
    private MakeService makeService;

    @RequestMapping(value = "/makeContracts", method = RequestMethod.GET)
    public DataTableSource<MakeContract> makeContracts(
            int draw,
            int start,
            int length,
            String productName,
            String code,
            String targetType
    ) {
        MakeContractQueryVo condition = new MakeContractQueryVo();
        condition.setProductName(productName);
        condition.setCode(code);
        condition.setTargetType(targetType);
        Page<MakeContract> makeContractPage = makeService.pageMakeContracts(condition, start, length);
        DataTableSource<MakeContract> dts = new DataTableSource().convertToDataTableSource(draw, start, length, makeContractPage.getData(), makeContractPage.getTotalPage());
        return dts;
    }

    @RequestMapping(value = "/makeContract", method = RequestMethod.GET)
    public JsonData findMakeContract(long id) {
        MakeContract makeContract = makeService.findMakeContract(id);
        return JsonData.success(makeContract);
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

    @RequestMapping(value = "/deleteMakeContract", method = RequestMethod.GET)
    public JsonData deleteMakeContract(Long id) {
        String result = makeService.deleteMakeContract(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/mcProduct", method = RequestMethod.GET)
    public JsonData findMcProduct(long id) {
        MakeContractProduct makeContractProduct = makeService.findMcProduct(id);
        return JsonData.success(makeContractProduct);
    }

    @RequestMapping(value = "/saveMcProductFiles", method = RequestMethod.POST)
    public JsonData<String> saveMcProductFiles(@RequestBody ArrayList<MakeContractDoc> makeContractDocs) {
        String result = makeService.saveMcProductFiles(makeContractDocs);

        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value="/getMcDocs", method = RequestMethod.GET)
    public JsonData<List<MakeContractDoc>> getMcDocs(long mcProductId, String type) {
        List<MakeContractDoc> docs = makeService.listContractDocs(mcProductId, type);
        return JsonData.success(docs);
    }

    @RequestMapping(value = "/deleteDoc", method = RequestMethod.POST)
    public JsonData<String> deleteDoc(long id) {
        String result = makeService.deleteMcDoc(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }


}
