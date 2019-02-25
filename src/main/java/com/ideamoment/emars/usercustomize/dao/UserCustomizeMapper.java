package com.ideamoment.emars.usercustomize.dao;

import com.ideamoment.emars.model.UserListCustomize;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户列表界面定义DAO
 *
 * Table: T_USER_LIST_CUSTOMIZE
 */
@Mapper
public interface UserCustomizeMapper {
    @Select({
            "<script>",
            "select * from t_user_list_customize where c_user_id = #{userId} and c_page = #{page} ",
            "<if test='position != null'>",
            " AND c_position = #{position}",
            "</if>",
            "order by c_sort asc",
            "</script>"
    })
    @Results(id = "userCustomizeMap", value ={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "userId", column = "c_user_id"),
            @Result(property = "page", column = "c_page"),
            @Result(property = "fieldName", column = "c_field_name"),
            @Result(property = "position", column = "c_position"),
            @Result(property = "sort", column = "c_sort"),
            @Result(property = "creator", column = "c_creator"),
            @Result(property = "createTime", column = "c_createtime")
    })
    List<UserListCustomize> queryUserListCustomize(
            @Param("userId") Long userId,
            @Param("page") String page,
            @Param("position") String position
    );

    @Select({
            "<script>",
            "select * from t_page_customize where c_page = #{page} ",
            "<if test='position != null'>",
            " AND c_position = #{position}",
            "</if>",
            "order by c_sort asc",
            "</script>"
    })
    @Results(id = "defaultCustomizeMap", value ={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "page", column = "c_page"),
            @Result(property = "fieldName", column = "c_field_name"),
            @Result(property = "position", column = "c_position"),
            @Result(property = "sort", column = "c_sort")
    })
    List<UserListCustomize> queryDefaultUserListCustomize(
            @Param("page") String page,
            @Param("position") String position
    );

    @Delete("delete from t_user_list_customize where c_user_id = #{userId} and c_page = #{page}")
    int deleteUserCustomize(@Param("userId") Long userId, @Param("page") String page);

    @Insert("insert into t_user_list_customize (c_user_id, c_page, c_position, c_field_name, c_sort, c_creator, c_createtime)" +
            "values(#{userId}, #{page}, #{position}, #{fieldName}, #{sort}, #{creator}, #{createTime})")
    int saveUserCustomize(UserListCustomize sc);
}
