package com.ideamoment.emars.user.service.impl;

import com.ideamoment.emars.model.User;
import com.ideamoment.emars.user.dao.UserMapper;
import com.ideamoment.emars.user.service.UserService;
import com.ideamoment.emars.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public User login(String account, String password) {
        return null;
    }

    @Override
    @Transactional
    public Page<User> pageUsers(int currentPage, int pageSize) {
        long count = userMapper.countUser();
        int offset = (currentPage - 1) * pageSize;
        List<User> users = userMapper.pageUsers(offset, pageSize);

        Page<User> result = new Page<User>();
        result.setCurrentPage(currentPage);
        result.setData(users);
        result.setPageSize(pageSize);
        result.setTotalRecord(count);

        return result;
    }
}
