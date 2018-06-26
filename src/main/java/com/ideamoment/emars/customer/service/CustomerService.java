package com.ideamoment.emars.customer.service;

import com.ideamoment.emars.model.Customer;
import com.ideamoment.emars.model.Platform;
import com.ideamoment.emars.utils.Page;

import java.util.List;

/**
 * Created by Chinakite on 2018/6/13.
 */
public interface CustomerService {
    Page<Customer> pageCustomers(int offset, int pageSize);

    List<Customer> listCustomers();

    String createCustomer(String name, String contact, String phone, String desc);

    String deleteCustomer(long id);

    Customer findCustomer(long id);

    String modifyCustomer(long id, String name, String contact, String phone, String desc);

    String createPlatform(String name, String desc, long customerId);

    List<Platform> listCustomerPlatforms(Long customerId);

    String deletePlatform(long id);

    Platform findPlatform(long id);

    String modifyPlatform(long id, String name, String desc, long customerId);
}
