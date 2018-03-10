package com.ideamoment.emars.user.service;

import com.ideamoment.emars.model.User;
import com.ideamoment.emars.utils.Page;

import java.util.List;

public interface UserService {

    User login(String account, String password);

    Page<User> pageUsers(int currentPage, int pageSize, String searchKey, String searchStatus);

    String addUser(User user);

    User loadUser(Long id);

    String modifyUser(Long id, User user);

    String deleteUser(Long id);

    String enableUser(Long id);

    String disableUser(Long id);

    String batchDeleteUser(String ids);

    List<User> listExtMakers();
}
