package com.ideamoment.emars.model;

import java.util.ArrayList;

/**
 * Created by Chinakite on 2018/6/13.
 */
public class Sale extends HistoriableEntity {
    private String code;
    private Long granterId;
    private Long customerId;
    private String privileges;
    private Long platformId;
    private String signDate;
    private String operator;
    private String projectCode;
    private String begin;
    private String end;
    private String totalPrice;

    private String operatorName;
    private ArrayList<SaleProduct> products = new ArrayList<SaleProduct>();

    private Grantee granter;
    private Customer customer;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getGranterId() {
        return granterId;
    }

    public void setGranterId(Long granterId) {
        this.granterId = granterId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public ArrayList<SaleProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<SaleProduct> products) {
        this.products = products;
    }

    public Grantee getGranter() {
        return granter;
    }

    public void setGranter(Grantee granter) {
        this.granter = granter;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
