package com.ideamoment.emars.user.service;

import com.ideamoment.emars.model.User;

public interface UserService {

    User login(String account, String password);

}
