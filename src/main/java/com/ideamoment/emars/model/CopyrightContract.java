package com.ideamoment.emars.model;

import com.ideamoment.emars.model.enumeration.CopyrightType;

import java.util.ArrayList;

public class CopyrightContract extends HistoriableEntity {
    private String contractCode;
    private String contractType;
    private String granter;
    private Long granterId;
    private String grantee;
    private Long granteeId;
    private String signDate;
    private String operator;
    private String projectCode;

    private String operatorName;

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

    public Long getGranteeId() {
        return granteeId;
    }

    public void setGranteeId(Long granteeId) {
        this.granteeId = granteeId;
    }

    public Long getGranterId() {
        return granterId;
    }

    public void setGranterId(Long granterId) {
        this.granterId = granterId;
    }

    public String getContractTypeText() {
        if(CopyrightType.COPYRIGHT_WZ.equals(this.contractType)) {
            return CopyrightType.COPYRIGHT_WZ_TEXT;
        }else if(CopyrightType.COPYRIGHT_YP.equals(this.contractType)){
            return CopyrightType.COPYRIGHT_YP_TEXT;
        }else if(CopyrightType.COPYRIGHT_SP.equals(this.contractType)){
            return CopyrightType.COPYRIGHT_SP_TEXT;
        }else if(CopyrightType.COPYRIGHT_WZGB.equals(this.contractType)){
            return CopyrightType.COPYRIGHT_WZGB_TEXT;
        }
        return "";
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}
