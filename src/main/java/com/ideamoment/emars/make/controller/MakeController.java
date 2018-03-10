package com.ideamoment.emars.make.controller;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.make.service.MakeService;
import com.ideamoment.emars.model.MakeContract;
import com.ideamoment.emars.model.MakeTask;
import com.ideamoment.emars.model.ProductQueryVo;
import com.ideamoment.emars.model.ProductResultVo;
import com.ideamoment.emars.product.service.ProductService;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.Page;
import com.ideamoment.emars.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * Created by yukiwang on 2018/3/5.
 */
@RestController
@RequestMapping("make")
public class MakeController {

    @Autowired
    private MakeService makeService;

    @RequestMapping(value = "/dtProducts", method = RequestMethod.GET)
    public DataTableSource<ProductResultVo> dtProducts(
            int draw,
            int start,
            int length,
            String productName,
            String authorName,
            String isbn,
            Long subjectId,
            String publishState,
            String state
    ) {
        ProductQueryVo condition = new ProductQueryVo();
        condition.setProductName(productName);
        condition.setAuthorName(authorName);
        condition.setIsbn(isbn);
        condition.setSubjectId(subjectId);
        condition.setPublishState(publishState);
        condition.setState(state);
        Page<ProductResultVo> products = makeService.pageProducts(condition, start, length);
        DataTableSource<ProductResultVo> dts = convertProductsToDataTableSource(draw, products);
        return dts;
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
            Long id,
            long productId,
            long makerId,
            String targetType,
            String totalPrice,
            String totalSection,
            String price,
            String penalty,
            String owner,
            String ownerContact,
            String ownerContactPhone,
            String ownerContactAddress,
            String ownerContactEmail,
            String worker,
            String workerContact,
            String workerContactPhone,
            String workerContactAddress,
            String workerContactEmail,
            String bankAccountName,
            String bank,
            String bankAccountNo,
            String type
    ) {
        MakeContract mc = null;
        if(id == null) {
            mc = new MakeContract();
        }else{
            mc = makeService.findCopyContract(id);
        }

        mc.setProductId(productId);
        mc.setMakerId(makerId);
        mc.setTargetType(targetType);

        mc.setTotalPrice(new BigDecimal(totalPrice));
        mc.setPrice(new BigDecimal(price));
        mc.setPenalty(new BigDecimal(penalty));
        mc.setTotalSection(Integer.valueOf(totalSection));

        mc.setOwner(owner);
        mc.setOwnerContact(ownerContact);
        mc.setOwnerContactAddress(ownerContactAddress);
        mc.setOwnerContactEmail(ownerContactEmail);
        mc.setOwnerContactPhone(ownerContactPhone);

        mc.setWorker(worker);
        mc.setWorkerContact(workerContact);
        mc.setWorkerContactAddress(workerContactAddress);
        mc.setWorkerContactEmail(workerContactEmail);
        mc.setWorkerContactPhone(workerContactPhone);

        mc.setBank(bank);
        mc.setBankAccountName(bankAccountName);
        mc.setBankAccountNo(bankAccountNo);

        String result = makeService.saveMakeContract(mc, type);

        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    private DataTableSource<ProductResultVo> convertProductsToDataTableSource(int draw, Page<ProductResultVo> productsPage) {
        DataTableSource<ProductResultVo> dts = new DataTableSource<ProductResultVo>();

        dts.setDraw(draw);
        dts.setRecordsTotal(productsPage.getTotalRecord());
        dts.setRecordsFiltered(productsPage.getTotalRecord());
        dts.setData(productsPage.getData());

        return dts;
    }

}
