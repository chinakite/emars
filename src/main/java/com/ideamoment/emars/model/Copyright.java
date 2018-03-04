package com.ideamoment.emars.model;

import com.ideamoment.emars.model.enumeration.CopyrightContractState;
import com.ideamoment.emars.model.enumeration.PrivilegeRange;
import com.ideamoment.emars.model.enumeration.PrivilegeType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yukiwang on 2018/2/25.
 */
public class Copyright extends HistoriableEntity {

    private String code;
    private String owner;
    private String ownerContact;
    private String ownerContactPhone;
    private String ownerContactAddress;
    private String ownerContactEmail;
    private String buyer;
    private String buyerContact;
    private String buyerContactPhone;
    private String buyerContactAddress;
    private String buyerContactEmail;
    private String privileges;
    private String privilegeType;
    private String privilegeRange;
    private String privilegeDeadline;
    private String bankAccountName;
    private String bankAccountNo;
    private String bank;
    private BigDecimal totalPrice;
    private String auditState;
    private Date finishTime;

    private List<Product> products;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerContact() {
        return ownerContact;
    }

    public void setOwnerContact(String ownerContact) {
        this.ownerContact = ownerContact;
    }

    public String getOwnerContactPhone() {
        return ownerContactPhone;
    }

    public void setOwnerContactPhone(String ownerContactPhone) {
        this.ownerContactPhone = ownerContactPhone;
    }

    public String getOwnerContactAddress() {
        return ownerContactAddress;
    }

    public void setOwnerContactAddress(String ownerContactAddress) {
        this.ownerContactAddress = ownerContactAddress;
    }

    public String getOwnerContactEmail() {
        return ownerContactEmail;
    }

    public void setOwnerContactEmail(String ownerContactEmail) {
        this.ownerContactEmail = ownerContactEmail;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getBuyerContact() {
        return buyerContact;
    }

    public void setBuyerContact(String buyerContact) {
        this.buyerContact = buyerContact;
    }

    public String getBuyerContactPhone() {
        return buyerContactPhone;
    }

    public void setBuyerContactPhone(String buyerContactPhone) {
        this.buyerContactPhone = buyerContactPhone;
    }

    public String getBuyerContactAddress() {
        return buyerContactAddress;
    }

    public void setBuyerContactAddress(String buyerContactAddress) {
        this.buyerContactAddress = buyerContactAddress;
    }

    public String getBuyerContactEmail() {
        return buyerContactEmail;
    }

    public void setBuyerContactEmail(String buyerContactEmail) {
        this.buyerContactEmail = buyerContactEmail;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public String getPrivilegesText() {
        String privilegesText = privileges.replaceAll("01", "音频改编权");
        privilegesText = privilegesText.replaceAll("02", "广播权");
        privilegesText = privilegesText.replaceAll("03", "网络信息传播权");
        privilegesText = privilegesText.replaceAll("04", "转授权");
        return privilegesText;
    }

    public String getPrivilegeType() {
        return privilegeType;
    }

    public void setPrivilegeType(String privilegeType) {
        this.privilegeType = privilegeType;
    }

    public String getPrivilegeTypeText() {
        if(this.privilegeType.equals(PrivilegeType.EXCLUSIVE)) {
            return PrivilegeType.EXCLUSIVE_TEXT;
        }else if(this.privilegeType.equals(PrivilegeType.UNEXCLUSIVE)) {
            return PrivilegeType.UNEXCLUSIVE_TEXT;
        }else{
            return "";
        }
    }

    public String getPrivilegeRange() {
        return privilegeRange;
    }

    public void setPrivilegeRange(String privilegeRange) {
        this.privilegeRange = privilegeRange;
    }

    public String getPrivilegeRangeText() {
        if(this.privilegeRange.equals(PrivilegeRange.GLOBAL)) {
            return PrivilegeRange.GLOBAL_TEXT;
        }else if(this.privilegeRange.equals(PrivilegeRange.WHOLE_CHINA)) {
            return PrivilegeRange.WHOLE_CHINA_TEXT;
        }else if(this.privilegeRange.equals(PrivilegeRange.CRP)) {
            return PrivilegeRange.CRP_TEXT;
        }else{
            return "";
        }
    }

    public String getPrivilegeDeadline() {
        return privilegeDeadline;
    }

    public void setPrivilegeDeadline(String privilegeDeadline) {
        this.privilegeDeadline = privilegeDeadline;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAuditState() {
        return auditState;
    }

    public String getAuditStateText() {
        if(this.auditState.equals(CopyrightContractState.AUDIT_FINISH)) {
            return CopyrightContractState.AUDIT_FINISH_TEXT;
        }else if(this.auditState.equals(CopyrightContractState.CEO_AUDIT)) {
            return CopyrightContractState.CEO_AUDIT_TEXT;
        }else if(this.auditState.equals(CopyrightContractState.DIRECTOR_AUDIT)) {
            return CopyrightContractState.DIRECTOR_AUDIT_TEXT;
        }else if(this.auditState.equals(CopyrightContractState.DRAFT)) {
            return CopyrightContractState.DRAFT_TEXT;
        }else if(this.auditState.equals(CopyrightContractState.FINISH)) {
            return CopyrightContractState.FINISH_TEXT;
        }else if(this.auditState.equals(CopyrightContractState.LAWYER_AUDIT)) {
            return CopyrightContractState.LAWYER_AUDIT_TEXT;
        }else if(this.auditState.equals(CopyrightContractState.LAWYER_CONFIRM)) {
            return CopyrightContractState.LAWYER_CONFIRM_TEXT;
        }else if(this.auditState.equals(CopyrightContractState.MANAGER_AUDIT)) {
            return CopyrightContractState.MANAGER_AUDIT_TEXT;
        }else if(this.auditState.equals(CopyrightContractState.REJECTED)) {
            return CopyrightContractState.REJECTED_TEXT;
        }else if(this.auditState.equals(CopyrightContractState.FINISH_CONFIRM)) {
            return CopyrightContractState.FINISH_CONFIRM_TEXT;
        }else{
            return CopyrightContractState.DRAFT_TEXT;
        }
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
