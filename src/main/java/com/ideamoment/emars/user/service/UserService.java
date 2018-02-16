package com.ideamoment.emars.user.service;

import com.ideamoment.emars.model.User;
import com.ideamoment.emars.utils.Page;

public interface UserService {

    User login(String account, String password);

    Page<User> pageUsers(int currentPage, int pageSize);


}
