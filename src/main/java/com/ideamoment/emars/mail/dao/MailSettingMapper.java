package com.ideamoment.emars.mail.dao;

import com.ideamoment.emars.model.MailSetting;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MailSettingMapper {
    @Select("select * from t_email_setting where c_id = #{id}")
    @Results(id="mailSettingMap", value={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "hostName", column = "c_hostname"),
            @Result(property = "port", column = "c_port"),
            @Result(property = "fromEmail", column = "c_from_email"),
            @Result(property = "fromName", column = "c_from_name"),
            @Result(property = "userName", column = "c_user_name"),
            @Result(property = "password", column = "c_password"),
            @Result(property = "ssl", column = "c_ssl"),
            @Result(property = "creator", column = "c_creator"),
            @Result(property = "createTime", column = "c_createtime"),
            @Result(property = "modifier", column = "c_modifier"),
            @Result(property = "modifyTime", column = "c_modifytime")
    })
    MailSetting findMailSetting(@Param("id") Long id);

    @Insert("insert into t_email_setting (c_hostname, c_port, c_type, c_from_email, c_from_name, c_user_name, c_password, c_ssl, c_creator, c_createtime)values(#{hostName}, #{port}, '1', #{fromEmail}, #{fromName}, #{userName}, #{password}, #{ssl}, #{creator}, #{createTime})")
    boolean insertMailSetting(MailSetting mail);

    @Update("update t_email_setting set c_hostname = #{hostName}, c_port=#{port}, c_from_email=#{fromEmail}, c_from_name=#{fromName}, c_user_name=#{userName}, c_password=#{password}, c_ssl=#{ssl}, c_modifier=#{modifier}, c_modifytime=#{modifyTIme} where c_id = 1")
    boolean updateMailSetting(MailSetting mail);
}
