package com.ideamoment.emars.customer.service.impl;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.customer.dao.CustomerMapper;
import com.ideamoment.emars.customer.service.CustomerService;
import com.ideamoment.emars.model.Customer;
import com.ideamoment.emars.model.Platform;
import com.ideamoment.emars.sale.dao.SaleMapper;
import com.ideamoment.emars.utils.Page;
import com.ideamoment.emars.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Chinakite on 2018/6/13.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private SaleMapper saleMapper;

    @Override
    @Transactional
    public Page<Customer> pageCustomers(int offset, int pageSize) {
        long count = customerMapper.countCustomer();
        int currentPage = offset/pageSize + 1;

        List<Customer> customers = customerMapper.pageCustomers(offset, pageSize);

        Page<Customer> result = new Page<Customer>();
        result.setCurrentPage(currentPage);
        result.setData(customers);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);

        return result;
    }

    @Override
    @Transactional
    public List<Customer> listCustomers() {
        return customerMapper.listCustomers();
    }

    @Override
    @Transactional
    public String createCustomer(String name, String contact, String phone, String desc) {
        Customer existsCustomer = customerMapper.findCustomerByName(name, null);

        if(existsCustomer != null) {
            return ErrorCode.CUSTOMER_EXISTS;
        }
        Customer customer = new Customer();
        customer.setName(name);
        customer.setContact(contact);
        customer.setPhone(phone);
        customer.setDesc(desc);
        customer.setCreateTime(new Date());
        customer.setCreator(UserContext.getUserId());

        boolean result = customerMapper.insertCustomer(customer);
        return resultString(result);
    }

    @Override
    @Transactional
    public String deleteCustomer(long id) {
        long r = saleMapper.countSalesByCustomer(id);
        if(r > 0) {
            return ErrorCode.CUSTOMER_CANNOT_DELETE;
        }
        boolean result = customerMapper.deleteCustomer(id);
        return resultString(result);
    }

    @Override
    @Transactional
    public Customer findCustomer(long id) {
        return customerMapper.findCustomer(id);
    }

    @Override
    @Transactional
    public String modifyCustomer(long id, String name, String contact, String phone, String desc) {
        Customer customer = customerMapper.findCustomer(id);

        if(customer == null) {
            return ErrorCode.CUSTOMER_NOT_EXISTS;
        }
        Customer existsCustomer = customerMapper.findCustomerByName(name, id);
        if(existsCustomer != null) {
            return ErrorCode.CUSTOMER_EXISTS;
        }

        customer.setName(name);
        customer.setContact(contact);
        customer.setPhone(phone);
        customer.setModifyTime(new Date());
        customer.setModifier(UserContext.getUserId());
        customer.setDesc(desc);

        boolean result = customerMapper.updateCustomer(customer);
        return resultString(result);
    }

    @Override
    @Transactional
    public String createPlatform(String name, String desc, long customerId) {
        Platform existsPlatform = customerMapper.findPlatformByName(customerId, name, null);

        if(existsPlatform != null) {
            return ErrorCode.CUSTOMER_EXISTS;
        }
        Platform platform = new Platform();
        platform.setCustomerId(customerId);
        platform.setName(name);
        platform.setDesc(desc);
        platform.setCreateTime(new Date());
        platform.setCreator(UserContext.getUserId());

        boolean result = customerMapper.insertPlatform(platform);
        return resultString(result);
    }

    @Override
    @Transactional
    public List<Platform> listCustomerPlatforms(Long customerId) {
        return customerMapper.listPlatformByCustomer(customerId);
    }

    @Override
    @Transactional
    public String deletePlatform(long id) {
        long r = saleMapper.countSalesByPlatform(id);
        if(r > 0) {
            return ErrorCode.PLATFORM_CANNOT_DELETE;
        }
        boolean result = customerMapper.deletePlatform(id);
        return resultString(result);
    }

    @Override
    @Transactional
    public Platform findPlatform(long id) {
        return customerMapper.findPlatform(id);
    }

    @Override
    @Transactional
    public String modifyPlatform(long id, String name, String desc, long customerId) {
        Platform platform = customerMapper.findPlatform(id);

        if(platform == null) {
            return ErrorCode.PLATFORM_NOT_EXISTS;
        }
        Platform existsPlatform = customerMapper.findPlatformByName(customerId, name, id);
        if(existsPlatform != null) {
            return ErrorCode.PLATFORM_EXISTS;
        }

        platform.setName(name);
        platform.setModifyTime(new Date());
        platform.setModifier(UserContext.getUserId());
        platform.setDesc(desc);

        boolean result = customerMapper.updatePlatform(platform);
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
