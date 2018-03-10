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

    @Select("select * from t_user where c_id = #{id}")
    @ResultMap("userMap")
    User findUserById(@Param("id") Long id);

    @Select("select * from t_user where c_account = #{account}")
    @ResultMap("userMap")
    User queryUser(@Param("account") String account);

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
            @Result(property = "creator", column = "c_creator"),
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

    @Update("update t_user set c_name=#{name}, c_account=#{account}, c_password=#{password}, c_email=#{email}, c_mobile=#{mobile}, c_status=#{status}, c_gender=#{gender}, c_honorific=#{honorific}, c_modifier=#{modifier}, c_modifytime=#{modifyTime} where c_id = #{id}")
    boolean updateUser(User dbUser);

    @Delete("delete from t_user where c_id = #{id}")
    boolean deleteUser(@Param("id") Long id);

    @Delete({
            "<script>",
            "delete from t_user where c_id in ",
            "<foreach item='id' index='index' collection='ids'",
            "open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    boolean batchDeleteUser(@Param("ids") Long[] ids);

    @Select("SELECT * FROM T_USER WHERE C_ROLE like '%52%'")
    @ResultMap("userMap")
    List<User> listExtMakers();
}
