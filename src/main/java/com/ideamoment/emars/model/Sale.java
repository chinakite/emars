package com.ideamoment.emars.model;

import com.ideamoment.emars.model.enumeration.SaleStateType;
import com.ideamoment.emars.model.enumeration.SaleType;
import com.ideamoment.emars.utils.StringUtils;

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
    private String state;
    private BigDecimal totalPrice;
    private int totalSection;

    private String operatorName;
    private ArrayList<SaleProduct> products = new ArrayList<SaleProduct>();

    private Grantee granter;
    private Customer customer;
    private String customerName;
    private String granterName;

    private String saleProductIds;
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

    public String getSaleProductIds() {
        return saleProductIds;
    }

    public void setSaleProductIds(String saleProductIds) {
        this.saleProductIds = saleProductIds;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTypeText() {
        if(SaleType.FM.equals(type)) {
            return SaleType.FM_TEXT;
        }else{
            return SaleType.WWW_TEXT;
        }
    }

    public String getStateText() {
        if(SaleStateType.FINISH.equals(state)) {
            return SaleStateType.FINISH_TEXT;
        }else if(SaleStateType.INVALID.equals(state)) {
            return SaleStateType.INVALID_TEXT;
        }else{
            return "";
        }
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getGranterName() {
        return granterName;
    }

    public void setGranterName(String granterName) {
        this.granterName = granterName;
    }

    public String getPlatformsInline() {
        if(platforms != null && platforms.size() > 0) {
            int c=0;
            String result = "";
            for(SaleCustomerPlatform platform : platforms) {
                if(c>0) {
                    result += "、";
                }
                result += platform.getPlatformName();
            }
            return result;
        }else{
            return "";
        }
    }

    public String getPrivilegesText() {
        if(StringUtils.isEmpty(privileges)) {
            return "";
        }else{
            String result = "";
            if(privileges.charAt(0) == '1') {
                result += "广播权";
            }
            if(result.length() > 0) {
                result += "、";
            }
            if(privileges.charAt(1) == '1') {
                result += "网络信息传播权";
            }
            return result;
        }
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }
}
