package com.ideamoment.emars.make.dao;

import com.ideamoment.emars.model.MakeContract;
import com.ideamoment.emars.model.MakeContractDoc;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by yukiwang on 2018/3/10.
 */
@Mapper
public interface MakeContractMapper {

    @Insert("INSERT INTO t_make_contract " +
            "(`C_CODE`,`C_PRODUCT_ID`,`C_MAKER_ID`,`C_TARGET_TYPE`,`C_OWNER`,`C_OWNER_CONTACT`,`C_OWNER_CONTACT_PHONE`," +
            "`C_OWNER_CONTACT_ADDRESS`,`C_OWNER_CONTACT_EMAIL`,`C_WORKER`,`C_WORKER_CONTACT`,`C_WORKER_CONTACT_PHONE`," +
            "`C_WORKER_CONTACT_ADDRESS`,`C_WORKER_CONTACT_EMAIL`,`C_TOTAL_SECTION`,`C_PRICE`,`C_TOTAL_PRICE`," +
            "`C_PENALTY`,`C_SHOWER_ID`,`C_BANK_ACCOUNT_NAME`,`C_BANK_ACCOUNT_NO`,`C_BANK`,`C_STATE`,`C_CREATOR`" +
            ",`C_CREATETIME`) " +
            "VALUES (" +
            "#{code}, #{productId}, #{makerId}, #{targetType}, #{owner}, #{ownerContact}, #{ownerContactPhone}, " +
            "#{ownerContactAddress}, #{ownerContactEmail}, #{worker}, #{workerContact}, #{workerContactPhone}, " +
            "#{workerContactAddress}, #{workerContactEmail}, #{totalSection}, #{price}, #{totalPrice}, #{penalty}, " +
            "#{showerId}, #{bankAccountName}, #{bankAccountNo}, #{bank}, #{state}, #{creator}, #{createTime})")
    boolean insertMakeContract(MakeContract makeContract);

    @Update("UPDATE t_make_contract SET " +
            "`C_CODE`=#{code},`C_PRODUCT_ID`=#{productId},`C_MAKER_ID`=#{makerId},`C_TARGET_TYPE`=#{targetType}," +
            "`C_OWNER`=#{owner},`C_OWNER_CONTACT`=#{ownerContact},`C_OWNER_CONTACT_PHONE`=#{ownerContactPhone}," +
            "`C_OWNER_CONTACT_ADDRESS`=#{ownerContactAddress},`C_OWNER_CONTACT_EMAIL`=#{ownerContactEmail}," +
            "`C_WORKER`=#{worker},`C_WORKER_CONTACT`=#{workerContact},`C_WORKER_CONTACT_PHONE`=#{workerContactPhone}," +
            "`C_WORKER_CONTACT_ADDRESS`=#{workerContactAddress},`C_WORKER_CONTACT_EMAIL`=#{workerContactEmail}," +
            "`C_TOTAL_SECTION`=#{totalSection},`C_PRICE`=#{price},`C_TOTAL_PRICE`=#{totalPrice},`C_PENALTY`=#{penalty}," +
            "`C_SHOWER_ID`=#{showerId},`C_BANK_ACCOUNT_NAME`=#{bankAccountName},`C_BANK_ACCOUNT_NO`=#{bankAccountNo}," +
            "`C_BANK`=#{bank},`C_STATE`=#{state},`C_MODIFIER`=#{modifier},`C_MODIFYTIME`=#{modifyTime} " +
            "WHERE c_id = #{id}")
    boolean updateMakeConntract(MakeContract makeContract);

    @Select("SELECT * FROM t_make_contract WHERE c_id = #{id}")
    @Results(id = "makeContractMap", value ={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "code", column = "C_CODE"),
            @Result(property = "productId", column = "C_PRODUCT_ID"),
            @Result(property = "makerId", column = "C_MAKER_ID"),
            @Result(property = "targetType", column = "C_TARGET_TYPE"),
            @Result(property = "owner", column = "C_OWNER"),
            @Result(property = "ownerContact", column = "C_OWNER_CONTACT"),
            @Result(property = "ownerContactPhone", column = "C_OWNER_CONTACT_PHONE"),
            @Result(property = "ownerContactAddress", column = "C_OWNER_CONTACT_ADDRESS"),
            @Result(property = "ownerContactEmail", column = "C_OWNER_CONTACT_EMAIL"),
            @Result(property = "worker", column = "C_WORKER"),
            @Result(property = "workerContact", column = "C_WORKER_CONTACT"),
            @Result(property = "workerContactPhone", column = "C_WORKER_CONTACT_PHONE"),
            @Result(property = "workerContactAddress", column = "C_WORKER_CONTACT_ADDRESS"),
            @Result(property = "workerContactEmail", column = "C_WORKER_CONTACT_EMAIL"),
            @Result(property = "totalSection", column = "C_TOTAL_SECTION"),
            @Result(property = "price", column = "C_PRICE"),
            @Result(property = "totalPrice", column = "C_TOTAL_PRICE"),
            @Result(property = "penalty", column = "C_PENALTY"),
            @Result(property = "showerId", column = "C_SHOWER_ID"),
            @Result(property = "bankAccountName", column = "C_BANK_ACCOUNT_NAME"),
            @Result(property = "bankAccountNo", column = "C_BANK_ACCOUNT_NO"),
            @Result(property = "bank", column = "C_BANK"),
            @Result(property = "state", column = "C_STATE"),
            @Result(property = "creator", column = "C_CREATOR"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "modifier", column = "C_MODIFIER"),
            @Result(property = "modifyTime", column = "C_MODIFYTIME")
    })
    MakeContract findMakeContract(@Param("id") long id);

    @Select("select C_CODE from T_MAKE_CONTRACT where C_CODE like concat(concat('%',#{code}),'%') order by C_CODE desc limit 0,1")
    String queryMaxContractCode(@Param("code") String code);

    @Select("SELECT * FROM t_make_contract WHERE c_product_id = #{productId}")
    @ResultMap("makeContractMap")
    MakeContract findMakeContractByProduct(@Param("productId") long productId);

    @Insert("INSERT INTO T_MAKE_CTRT_DOC " +
            "(`C_CONTRACT_ID`,`C_TYPE`,`C_CREATOR_ID`,`C_CREATETIME`,`C_VERSION`,`C_FILE_URL`) " +
            "VALUES " +
            "(#{contractId}, #{type}, #{creatorId}, #{createTime}, #{version}, #{fileUrl})")
    boolean insertMakeContractDoc(MakeContractDoc makeContractDoc);

    @Select("SELECT * FROM T_MAKE_CTRT_DOC WHERE C_CONTRACT_ID = #{contractId} ORDER BY C_CREATETIME DESC")
    @Results(id = "makeContractDocMap", value ={
            @Result(property = "id", column = "c_id", id = true),
            @Result(property = "contractId", column = "C_CONTRACT_ID"),
            @Result(property = "type", column = "C_TYPE"),
            @Result(property = "creatorId", column = "C_CREATOR_ID"),
            @Result(property = "createTime", column = "C_CREATETIME"),
            @Result(property = "version", column = "C_VERSION"),
            @Result(property = "fileUrl", column = "C_FILE_URL")
    })
    List<MakeContractDoc> listContractDocs(@Param("contractId") long contractId);
}
