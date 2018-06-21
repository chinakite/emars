package com.ideamoment.emars.customer.controller;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.customer.service.CustomerService;
import com.ideamoment.emars.model.Customer;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Chinakite on 2018/6/14.
 */
@RestController
@RequestMapping("system")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public DataTableSource<Customer> queryCustomers(int draw, int start, int length, String key) {
        Page<Customer> customers = customerService.pageCustomers(start, length);
        DataTableSource<Customer> dts = convertCustomersToDataTableSource(draw, customers);
        return dts;
    }

    @RequestMapping(value = "/allCustomers", method = RequestMethod.GET)
    public JsonData<List<Customer>> allCustomers() {
        List<Customer> result = customerService.listCustomers();
        return JsonData.success(result);
    }

    @RequestMapping(value = "/createCustomer", method = RequestMethod.POST)
    public JsonData<Boolean> createCustomer(String name, String contact, String phone, String desc) {
        String result = customerService.createCustomer(name, contact, phone, desc);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/deleteCustomer", method = RequestMethod.POST)
    public JsonData<Boolean> deleteCustomer(long id) {
        String result = customerService.deleteCustomer(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public JsonData findCustomer(long id) {
        Customer customer = customerService.findCustomer(id);
        return JsonData.success(customer);
    }

    @RequestMapping(value = "/modifyCustomer", method = RequestMethod.POST)
    public JsonData<Boolean> modifyCustomer(long id, String name, String contact, String phone, String desc) {
        String result = customerService.modifyCustomer(id, name, contact, phone, desc);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value = "/createPlatform", method = RequestMethod.POST)
    public JsonData<Boolean> createPlatform(String name, String desc) {
        String result = customerService.createPlatform(name, desc);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    private DataTableSource<Customer> convertCustomersToDataTableSource(int draw, Page<Customer> customerPage) {
        DataTableSource<Customer> dts = new DataTableSource<Customer>();

        dts.setDraw(draw);
        dts.setRecordsTotal(customerPage.getTotalRecord());
        dts.setRecordsFiltered(customerPage.getTotalRecord());
        dts.setData(customerPage.getData());

        return dts;
    }
}
