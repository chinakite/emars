package com.ideamoment.emars.customer.dao;

import com.ideamoment.emars.model.Customer;
import com.ideamoment.emars.model.Platform;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Chinakite on 2018/6/13.
 */
@Mapper
public interface CustomerMapper {
    @Select("select * from t_customer where c_id = #{id}")
    @Results(id = "customerMap", value = {
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "name", column = "c_name"),
            @Result(property = "contact", column = "c_contact"),
            @Result(property = "phone", column = "c_phone"),
            @Result(property = "desc", column = "c_desc"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME")
    })
    Customer findCustomer(@Param("id") Long id);

    @Select("select * from t_customer ORDER BY C_MODIFYTIME DESC LIMIT #{offset}, #{size}")
    @ResultMap("customerMap")
    List<Customer> pageCustomers(@Param("offset")int offset, @Param("size")int size);

    @Select("select * from t_customer ORDER BY C_MODIFYTIME DESC")
    @ResultMap("customerMap")
    List<Customer> listCustomers();

    @Select("select count(c_id) from t_customer")
    long countCustomer();

    @Insert("insert into t_customer " +
            "(`c_name`, `c_contact`, `c_phone`, `c_desc`, `c_creator`, `c_createtime`, `c_modifier`, `c_modifytime`)" +
            "values" +
            "(#{name}, #{contact}, #{phone}, #{desc}, #{creator}, #{createTime}, #{modifier}, #{modifyTime})"
    )
    @Options(useGeneratedKeys = true)
    boolean insertCustomer(Customer customer);

    @Update("update t_customer set c_name = #{name}, c_contact = #{contact}, c_phone = #{phone}, c_desc = #{desc}, " +
            "c_modifier = #{modifier}, c_modifytime=#{modifyTime} " +
            "where c_id = #{id}"
    )
    boolean updateCustomer(Customer customer);

    @Delete("delete from t_customer where c_id = #{id}")
    boolean deleteCustomer(@Param("id") Long id);

    @Select({"<script>",
            "select * from t_customer where c_name = #{name}",
            "<if test='id != null'>",
            " AND c_id != #{id}",
            "</if>",
            "</script>"})
    @ResultMap("customerMap")
    Customer findCustomerByName(@Param("name") String name, @Param("id") Long ignoreId);

    @Insert("insert into t_platform (c_customer_id, c_name, c_desc, c_creator, c_createtime)values(#{customerId}, #{name}, #{desc}, #{creator}, #{createTime})")
    boolean insertPlatform(Platform platform);

    @Select({
            "<script>",
            "select * from t_platform where c_customer_id=#{customerId} and c_name = #{name}",
            "<if test='ignoreId != null'>",
            " AND c_id != #{ignoreId}",
            "</if>",
            "</script>"
    })
    @Results(id = "platformMap", value ={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "customerId", column = "C_CUSTOMER_ID"),
            @Result(property = "name", column = "C_NAME"),
            @Result(property = "desc", column = "C_DESC"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME")
    })
    Platform findPlatformByName(@Param("customerId")Long customerId, @Param("name")String name, @Param("ignoreId") Long ignoreId);

    @Select("select * from t_platform where c_customer_id=#{customerId}")
    @ResultMap("platformMap")
    List<Platform> listPlatformByCustomer(@Param("customerId")Long customerId);

    @Delete("delete from t_platform where c_id = #{id}")
    boolean deletePlatform(long id);

    @Select("select * from t_platform where c_id=#{id}")
    @ResultMap("platformMap")
    Platform findPlatform(long id);

    @Update("update t_platform set c_name = #{name}, c_desc=#{desc} where c_id = #{id}")
    boolean updatePlatform(Platform platform);
}
