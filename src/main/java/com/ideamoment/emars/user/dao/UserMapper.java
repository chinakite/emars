package com.ideamoment.emars.user.dao;

import com.ideamoment.emars.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户DAO
 *
 * Table: T_USER
 */
@Mapper
public interface UserMapper {

    User queryUser(String account, String ignoreId);

    @Select("select * from t_user where c_id > 1 limit #{offset}, #{size}")
    List<User> pageUsers(int offset, int size);

    @Select("select count(c_id) from t_user where c_id > 1")
    long countUser();
}
