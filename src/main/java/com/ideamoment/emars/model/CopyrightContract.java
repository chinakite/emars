package com.ideamoment.emars.model;

import com.ideamoment.emars.copyright.CopyrightProductInfo;

import java.util.ArrayList;

public class CopyrightContract extends HistoriableEntity {
    private String contractCode;
    private String contractType;
    private String granter;
    private String grantee;
    private String signDate;
    private String operator;
    private String projectCode;

    private ArrayList<CopyrightProductInfo> products = new ArrayList<CopyrightProductInfo>();

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getGranter() {
        return granter;
    }

    public void setGranter(String granter) {
        this.granter = granter;
    }

    public String getGrantee() {
        return grantee;
    }

    public void setGrantee(String grantee) {
        this.grantee = grantee;
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

    public ArrayList<CopyrightProductInfo> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<CopyrightProductInfo> products) {
        this.products = products;
    }
}
