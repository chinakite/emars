package com.ideamoment.emars.user.dao;

import com.ideamoment.emars.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户DAO
 *
 * Table: T_USER
 */
@Mapper
public interface UserMapper {



    User queryUser(String account, String ignoreId);

//    @Select("select * from t_user where c_id > 1 limit #{offset}, #{size}")
    @Select({
            "<script>",
            "select * from t_user where c_id > 1",
            "<if test='key != null'>",
            " AND (c_name like #{key} or c_account like #{key} or c_email like #{key} or c_mobile like #{key})",
            "</if>",
            "<if test='status != null and status != -1'>",
            " AND c_status = #{status}",
            "</if>",
            "order by c_createtime desc",
            "limit #{offset}, #{size}",
            "</script>"
    })
    @Results(id = "userMap", value ={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "name", column = "c_name"),
            @Result(property = "account", column = "c_account"),
            @Result(property = "email", column = "c_email"),
            @Result(property = "password", column = "c_password"),
            @Result(property = "mobile", column = "c_mobile"),
            @Result(property = "role", column = "c_role"),
            @Result(property = "gender", column = "c_gender"),
            @Result(property = "honorific", column = "c_honorific"),
            @Result(property = "status", column = "c_status"),
            @Result(property = "creator", column = "c_role"),
            @Result(property = "createTime", column = "c_createtime"),
            @Result(property = "modifier", column = "c_modifier"),
            @Result(property = "modifyTime", column = "c_modifytime")
    })
    List<User> pageUsers(@Param("offset")int offset, @Param("size")int size, @Param("key")String searchKey, @Param("status")String searchStatus);

    @Select("select count(c_id) from t_user where c_id > 1")
    long countUser();

    @Select("select * from t_user where c_account = #{account} and c_status = #{status}")
    @ResultMap("userMap")
    User findUser(@Param("account")String account, @Param("status")String status);

    @Insert("insert into t_user (c_name, c_account, c_email, c_password, c_mobile, c_gender, c_honorific, c_status, c_creator, c_createtime)values(#{name}, #{account}, #{email}, #{password}, #{mobile}, #{gender}, #{honorific}, #{status}, #{creator}, #{createTime})")
    boolean insertUser(User user);
}
