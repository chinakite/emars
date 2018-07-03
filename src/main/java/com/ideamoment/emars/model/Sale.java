package com.ideamoment.emars.model;

import com.ideamoment.emars.model.enumeration.SaleType;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Chinakite on 2018/6/13.
 */
public class Sale extends HistoriableEntity {
    private String code;
    private String type;
    private Long granterId;
    private Long customerId;
    private String privileges;
    private ArrayList<Long> platformIds;
    private String signDate;
    private String operator;
    private String projectCode;
    private String begin;
    private String end;
    private BigDecimal totalPrice;
    private int totalSection;

    private String operatorName;
    private ArrayList<SaleProduct> products = new ArrayList<SaleProduct>();

    private Grantee granter;
    private Customer customer;

    private String productIds;
    private ArrayList<SaleCustomerPlatform> platforms = new ArrayList<SaleCustomerPlatform>();

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

    public ArrayList<Long> getPlatformIds() {
        return platformIds;
    }

    public void setPlatformIds(ArrayList<Long> platformIds) {
        this.platformIds = platformIds;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
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

    public int getTotalSection() {
        return totalSection;
    }

    public void setTotalSection(int totalSection) {
        this.totalSection = totalSection;
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<SaleCustomerPlatform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(ArrayList<SaleCustomerPlatform> platforms) {
        this.platforms = platforms;
    }

    public String getTypeText() {
        if(SaleType.FM.equals(type)) {
            return SaleType.FM_TEXT;
        }else{
            return SaleType.WWW_TEXT;
        }
    }
}
