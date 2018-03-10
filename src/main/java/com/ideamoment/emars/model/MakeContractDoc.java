package com.ideamoment.emars.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ideamoment.emars.model.enumeration.ContractDocType;

import java.util.Date;

/**
 * Created by yukiwang on 2018/3/11.
 */
public class MakeContractDoc extends BaseEntity {

    private long contractId;
    private long creatorId;
    private Date createTime;
    private String version;
    private String fileUrl;
    private String type = ContractDocType.CONTRACT_DOC;


    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeText() {
        if(this.type.equals(ContractDocType.CONTRACT_DOC)) {
            return ContractDocType.CONTRACT_DOC_TEXT;
        }else{
            return ContractDocType.CONTRACT_ADDON_TEXT;
        }
    }
}
