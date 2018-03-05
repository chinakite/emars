package com.ideamoment.emars.make.dao;

import com.ideamoment.emars.model.MakeTask;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * Created by yukiwang on 2018/3/5.
 */
@Mapper
public interface MakeMapper {

    @Insert("INSERT INTO t_make_task " +
            "(`C_NAME`,`C_PRODUCT_ID`,`C_MAKER_ID`,`C_TIME_SECTION`,`C_TOTAL_SECTION`,`C_SHOW_TYPE`,`C_MAKE_TIME`," +
            "`C_SHOW_EXPECTION`,`C_CONTRACT_ID`,`C_STATE`,`C_DESC`,`C_EXT_FINISH`,`C_CREATOR`,`C_CREATETIME`) " +
            "values (" +
            "#{name}, #{productId}, #{makerId}, #{timeSection}, #{totalSection}, #{showType}, #{makeTime}, " +
            "#{showExpection}, #{contractId}, #{state}, #{desc}, #{extFinish}, #{creator}, #{createTime})")
    boolean insertMakeTask(MakeTask makeTask);

    @Update("UPDATE t_make_task SET " +
            "`C_NAME`=#{name},`C_PRODUCT_ID`=#{productId},`C_MAKER_ID`=#{makerId},`C_TIME_SECTION`=#{timeSection}," +
            "`C_TOTAL_SECTION`=#{totalSection},`C_SHOW_TYPE`=#{showType},`C_MAKE_TIME`=#{makeTime}," +
            "`C_SHOW_EXPECTION`=#{showExpection},`C_CONTRACT_ID`=#{contractId},`C_STATE`=#{state},`C_DESC`=#{desc}," +
            "`C_EXT_FINISH`=#{extFinish},`C_MODIFIER`=#{modifier},`C_MODIFYTIME`=#{modifyTime} WHERE c_id = #{id}")
    boolean updateMakeTask(MakeTask makeTask);

}
