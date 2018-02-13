package com.ideamoment.emars.user.dao;

import com.ideamoment.emars.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户DAO
 *
 * Table: T_USER
 */
@Mapper
public interface UserMapper {

    public User queryUser(String account, String ignoreId);
}
