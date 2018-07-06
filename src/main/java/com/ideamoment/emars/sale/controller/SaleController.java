package com.ideamoment.emars.sale.controller;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.model.ProductInfo;
import com.ideamoment.emars.model.Sale;
import com.ideamoment.emars.model.SaleContractFile;
import com.ideamoment.emars.model.SaleContractQueryVo;
import com.ideamoment.emars.sale.service.SaleService;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chinakite on 2018/6/19.
 */
@RestController
@RequestMapping("sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductInfo> products() {
        List<ProductInfo> products = saleService.listProducts();
        return products;
    }

    @RequestMapping(value = "/saleContracts", method = RequestMethod.GET)
    public DataTableSource<Sale> saleContracts(
            int draw,
            int start,
            int length,
            String productName,
            String code,
            String targetType
    ) {
        SaleContractQueryVo condition = new SaleContractQueryVo();
        condition.setProductName(productName);
        condition.setCode(code);
        condition.setTargetType(targetType);
        Page<Sale> saleContractPage = saleService.pageSaleContracts(condition, start, length);
        DataTableSource<Sale> dts = new DataTableSource().convertToDataTableSource(draw, start, length, saleContractPage.getData(), saleContractPage.getTotalPage());
        return dts;
    }

    @RequestMapping(value = "generateContractCode", method = RequestMethod.GET)
    public JsonData generateContractCode(String signDate, String type) {
        String code = saleService.generateContractCode(signDate, type);
        if(code == null) {
            return JsonData.error(ErrorCode.UNKNOWN_ERROR, "生成合同号时发生错误");
        }else{
            return JsonData.success(code);
        }
    }

    @RequestMapping(value = "/saleContract", method = RequestMethod.POST)
    public JsonData saveSaleContract(
            @RequestBody Sale saleContract
    ) {
        String result = saleService.saveSaleContract(saleContract);

        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/saleContract", method = RequestMethod.GET)
    public JsonData findSaleContract(long id) {
        Sale saleContract = saleService.findSaleContract(id);
        return JsonData.success(saleContract);
    }

    @RequestMapping(value = "changeSaleContractState", method = RequestMethod.POST)
    public JsonData<String> changeSaleContractState(long id, String state) {
        String result = saleService.changeSaleContractState(id, state);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/saveSaleProductFiles", method = RequestMethod.POST)
    public JsonData<String> saveSaleProductFiles(@RequestBody ArrayList<SaleContractFile> saleContractDocs) {
        String result = saleService.saveSaleProductFiles(saleContractDocs);

        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value="/getSaleDocs", method = RequestMethod.GET)
    public JsonData<List<SaleContractFile>> getMcDocs(long saleProductId, String type) {
        List<SaleContractFile> docs = saleService.listContractDocs(saleProductId, type);
        return JsonData.success(docs);
    }

    @RequestMapping(value = "/deleteDoc", method = RequestMethod.POST)
    public JsonData<String> deleteDoc(long id) {
        String result = saleService.deleteSaleDoc(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }
}
