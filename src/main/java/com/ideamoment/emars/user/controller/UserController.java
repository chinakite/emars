package com.ideamoment.emars.user.controller;

import com.ideamoment.emars.model.User;
import com.ideamoment.emars.user.service.UserService;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/users", method=RequestMethod.GET)
    public DataTableSource<User> queryUsers(int draw, int start, int length, String searchKey, String searchStatus) {
        Page<User> users = userService.pageUsers(start, length, searchKey, searchStatus);
        DataTableSource<User> dts = convertProductsToDataTableSource(draw, users);
        return dts;
    }

    private DataTableSource<User> convertProductsToDataTableSource(int draw, Page<User> productsPage) {
        DataTableSource<User> dts = new DataTableSource<User>();

        dts.setDraw(draw);
        dts.setRecordsTotal(productsPage.getTotalRecord());
        dts.setRecordsFiltered(productsPage.getTotalRecord());
        dts.setData(productsPage.getData());

        return dts;
    }
}
