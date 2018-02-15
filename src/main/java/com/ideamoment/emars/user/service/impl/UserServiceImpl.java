package com.ideamoment.emars.user.service.impl;

import com.ideamoment.emars.model.User;
import com.ideamoment.emars.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Override
    @Transactional
    public User login(String account, String password) {
        return null;
    }
}
