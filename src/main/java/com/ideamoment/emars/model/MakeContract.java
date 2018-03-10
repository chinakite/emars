package com.ideamoment.emars.model;

import java.math.BigDecimal;

/**
 * Created by yukiwang on 2018/3/10.
 */
public class MakeContract extends HistoriableEntity {

    private String code;
    private long productId;
    private long makerId;
    private String targetType;
    private String owner;
    private String ownerContact;
    private String ownerContactPhone;
    private String ownerContactAddress;
    private String ownerContactEmail;
    private String worker;
    private String workerContact;
    private String workerContactPhone;
    private String workerContactAddress;
    private String workerContactEmail;
    private Integer totalSection;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private BigDecimal penalty;
    private long showerId;
    private String bankAccountName;
    private String bankAccountNo;
    private String bank;
    private String state;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getMakerId() {
        return makerId;
    }

    public void setMakerId(long makerId) {
        this.makerId = makerId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
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

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getWorkerContact() {
        return workerContact;
    }

    public void setWorkerContact(String workerContact) {
        this.workerContact = workerContact;
    }

    public String getWorkerContactPhone() {
        return workerContactPhone;
    }

    public void setWorkerContactPhone(String workerContactPhone) {
        this.workerContactPhone = workerContactPhone;
    }

    public String getWorkerContactAddress() {
        return workerContactAddress;
    }

    public void setWorkerContactAddress(String workerContactAddress) {
        this.workerContactAddress = workerContactAddress;
    }

    public String getWorkerContactEmail() {
        return workerContactEmail;
    }

    public void setWorkerContactEmail(String workerContactEmail) {
        this.workerContactEmail = workerContactEmail;
    }

    public Integer getTotalSection() {
        return totalSection;
    }

    public void setTotalSection(Integer totalSection) {
        this.totalSection = totalSection;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getPenalty() {
        return penalty;
    }

    public void setPenalty(BigDecimal penalty) {
        this.penalty = penalty;
    }

    public long getShowerId() {
        return showerId;
    }

    public void setShowerId(long showerId) {
        this.showerId = showerId;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
