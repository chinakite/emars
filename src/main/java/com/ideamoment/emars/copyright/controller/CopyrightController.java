package com.ideamoment.emars.copyright.controller;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.copyright.service.CopyrightService;
import com.ideamoment.emars.model.Copyright;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * Created by yukiwang on 2018/2/24.
 */
@RestController
@RequestMapping("copyright")
public class CopyrightController {

    @Autowired
    private CopyrightService copyrightService;

    @RequestMapping(value = "/copyrights", method = RequestMethod.GET)
    public DataTableSource<Copyright> queryCopyrights(
            int draw,
            int start,
            int length,
            String code,
            String owner,
            String auditState
    ) {
        Copyright condition = new Copyright();
        condition.setCode(code);
        condition.setOwner(owner);
        condition.setAuditState(auditState);
        Page<Copyright> copyrights = copyrightService.listCopyrights(condition, start, length);
        DataTableSource<Copyright> dts = convertProductsToDataTableSource(draw, copyrights);
        return dts;
    }

    @RequestMapping(value = "/copyright", method = RequestMethod.GET)
    public JsonData findCopyright(long id) {
        Copyright copyright = copyrightService.findCopyright(id);
        return JsonData.success(copyright);
    }

    @RequestMapping(value = "/createCopyrightContract", method = RequestMethod.POST)
    public JsonData<String> createCopyrightContract(
            Long contractId,
            String totalPrice,
            long[] contractProductIds,
            String prices,
            String owner,
            String ownerContact,
            String ownerContactPhone,
            String ownerContactAddress,
            String ownerContactEmail,
            String buyer,
            String buyerContact,
            String buyerContactPhone,
            String buyerContactAddress,
            String buyerContactEmail,
            String privileges,
            String privilegeType,
            String privilegeRange,
            String privilegeDeadline,
            String bankAccountName,
            String bank,
            String bankAccountNo,
            int submit,
            String type
    ) {
        Copyright cc = null;
        if(contractId == null) {
            cc = new Copyright();
        }else{
            cc = copyrightService.findCopyright(contractId);

        }

        cc.setTotalPrice(new BigDecimal(totalPrice));
        cc.setOwner(owner);
        cc.setOwnerContact(ownerContact);
        cc.setOwnerContactAddress(ownerContactAddress);
        cc.setOwnerContactEmail(ownerContactEmail);
        cc.setOwnerContactPhone(ownerContactPhone);

        cc.setBuyer(buyer);
        cc.setBuyerContact(buyerContact);
        cc.setBuyerContactAddress(buyerContactAddress);
        cc.setBuyerContactEmail(buyerContactEmail);
        cc.setBuyerContactPhone(buyerContactPhone);

        cc.setPrivileges(privileges);
        cc.setPrivilegeType(privilegeType);
        cc.setPrivilegeRange(privilegeRange);
        cc.setPrivilegeDeadline(privilegeDeadline);

        cc.setBank(bank);
        cc.setBankAccountName(bankAccountName);
        cc.setBankAccountNo(bankAccountNo);

        long[] productIdArr = contractProductIds;
        String[] priceArr = prices.split(",");

        String result = copyrightService.saveCopyrightContract(cc, productIdArr, priceArr, submit, type);

        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }

    }


    private DataTableSource<Copyright> convertProductsToDataTableSource(int draw, Page<Copyright> productsPage) {
        DataTableSource<Copyright> dts = new DataTableSource<Copyright>();

        dts.setDraw(draw);
        dts.setRecordsTotal(productsPage.getTotalRecord());
        dts.setRecordsFiltered(productsPage.getTotalRecord());
        dts.setData(productsPage.getData());

        return dts;
    }
}
