package com.ideamoment.emars.user.controller;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.model.User;
import com.ideamoment.emars.user.service.UserService;
import com.ideamoment.emars.utils.DataTableSource;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value="/user", method=RequestMethod.POST)
    public JsonData<Boolean> addUser(User user) {
        String result = userService.addUser(user);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value="/user/{id}", method=RequestMethod.GET)
    public JsonData<User> loadUser(@PathVariable("id") Long id) {
        User user = userService.loadUser(id);
        if(user == null) {
            return JsonData.error(ErrorCode.USER_NOT_EXISTS, ErrorCode.ERROR_MSG.get(ErrorCode.USER_NOT_EXISTS));
        }else{
            return JsonData.success(user);
        }
    }

    @RequestMapping(value="/user/{id}", method=RequestMethod.POST)
    public JsonData modifyUser(@PathVariable("id") Long id, User user) {
        String result = userService.modifyUser(id, user);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value="/deleteUser/{id}", method=RequestMethod.POST)
    public JsonData deleteUser(@PathVariable("id") Long id) {
        String result = userService.deleteUser(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value="/batchDeleteUser", method=RequestMethod.POST)
    public JsonData batchDeleteUser(String ids) {
        String result = userService.batchDeleteUser(ids);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value="/enableUser/{id}", method=RequestMethod.POST)
    public JsonData enableUser(@PathVariable("id") Long id) {
        String result = userService.enableUser(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
    }

    @RequestMapping(value="/disableUser/{id}", method=RequestMethod.POST)
    public JsonData disableUser(@PathVariable("id") Long id) {
        String result = userService.disableUser(id);
        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(result, ErrorCode.ERROR_MSG.get(result));
        }
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
