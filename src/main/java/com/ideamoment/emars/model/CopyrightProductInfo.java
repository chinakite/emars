package com.ideamoment.emars.model;

import com.ideamoment.emars.model.HistoriableEntity;
import com.ideamoment.emars.model.enumeration.YesOrNo;
import com.ideamoment.emars.utils.StringUtils;

import java.util.List;

public class CopyrightProductInfo extends HistoriableEntity {

    private String name;
    private String wordCount;
    private String subjectId;
    private String publishState;
    private String isbn;
    private String proportions;
    private String type;
    private String stockIn;
    private boolean privilege1;
    private boolean privilege2;
    private boolean privilege3;
    private boolean privilege4;
    private String privileges;
    private String privilegesText;
    private String grant;
    private String copyrightType;
    private String copyrightPrice;
    private String settlementType;
    private String copyrightBegin;
    private String copyrightEnd;
    private String radioTrans;
    private String desc;

    private Long copyrightId;
    private Long productId;
    private List<Author> authors;
    private String subjectName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWordCount() {
        return wordCount;
    }

    public void setWordCount(String wordCount) {
        this.wordCount = wordCount;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getPublishState() {
        return publishState;
    }

    public void setPublishState(String publishState) {
        this.publishState = publishState;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getProportions() {
        return proportions;
    }

    public void setProportions(String proportions) {
        this.proportions = proportions;
    }

    public boolean isPrivilege1() {
        return privilege1;
    }

    public void setPrivilege1(boolean privilege1) {
        this.privilege1 = privilege1;
    }

    public boolean isPrivilege2() {
        return privilege2;
    }

    public void setPrivilege2(boolean privilege2) {
        this.privilege2 = privilege2;
    }

    public boolean isPrivilege3() {
        return privilege3;
    }

    public void setPrivilege3(boolean privilege3) {
        this.privilege3 = privilege3;
    }

    public boolean isPrivilege4() {
        return privilege4;
    }

    public void setPrivilege4(boolean privilege4) {
        this.privilege4 = privilege4;
    }

    public String getGrant() {
        return grant;
    }

    public void setGrant(String grant) {
        this.grant = grant;
    }

    public String getCopyrightType() {
        return copyrightType;
    }

    public void setCopyrightType(String copyrightType) {
        this.copyrightType = copyrightType;
    }

    public String getCopyrightPrice() {
        return copyrightPrice;
    }

    public void setCopyrightPrice(String copyrightPrice) {
        this.copyrightPrice = copyrightPrice;
    }

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    public String getCopyrightBegin() {
        return copyrightBegin;
    }

    public void setCopyrightBegin(String copyrightBegin) {
        this.copyrightBegin = copyrightBegin;
    }

    public String getCopyrightEnd() {
        return copyrightEnd;
    }

    public void setCopyrightEnd(String copyrightEnd) {
        this.copyrightEnd = copyrightEnd;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public String getRadioTrans() {
        return radioTrans;
    }

    public void setRadioTrans(String radioTrans) {
        this.radioTrans = radioTrans;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDescText() {
        if(StringUtils.isNotEmpty(desc))
            return desc;
        else
            return "无";
    }

    public String getPrivileges() {
        if(StringUtils.isNotEmpty(privileges)) {
            return this.privileges;
        }
        return (privilege1 ? "1" : "0")
                + (privilege2 ? "1" : "0")
                + (privilege3 ? "1" : "0")
                + (privilege4 ? "1" : "0");
    }

    public Long getCopyrightId() {
        return copyrightId;
    }

    public void setCopyrightId(Long copyrightId) {
        this.copyrightId = copyrightId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getPrivilegesText() {
        if(StringUtils.isNotEmpty(privilegesText)) {
            return this.privilegesText;
        }
        String result = "";
        if(StringUtils.isNotEmpty(privileges)) {
            if(privileges.charAt(0) == '1') {
                result += "音频改编权";
            }
            if(privileges.charAt(1) == '1') {
                if(StringUtils.isNotEmpty(result)) {
                    result += "、";
                }
                result += "广播权";
            }
            if(privileges.charAt(2) == '1') {
                if(StringUtils.isNotEmpty(result)) {
                    result += "、";
                }
                result += "信息网络传播权";
            }
            if(privileges.charAt(3) == '1') {
                if(StringUtils.isNotEmpty(result)) {
                    result += "、";
                }
                result += "广播剧改编权";
            }
            return result;
        }else{
            if(privilege1) {
                result += "音频改编权";
            }
            if(privilege2) {
                if(StringUtils.isNotEmpty(result)) {
                    result += "、";
                }
                result += "广播权";
            }
            if(privilege3) {
                if(StringUtils.isNotEmpty(result)) {
                    result += "、";
                }
                result += "信息网络传播权";
            }
            if(privilege4) {
                if(StringUtils.isNotEmpty(result)) {
                    result += "、";
                }
                result += "广播剧改编权";
            }
            return result;
        }

    }

    public void setPrivilegesText(String privilegesText) {
        this.privilegesText = privilegesText;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStockIn() {
        return stockIn;
    }

    public void setStockIn(String stockIn) {
        this.stockIn = stockIn;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getAuthorsTextInline() {
        String result = "";
        if(authors != null) {
            for (int i = 0; i < this.authors.size(); i++) {
                Author author = authors.get(i);
                if (i > 0) {
                    result += "、";
                }
                result += author.getName();
                if (StringUtils.isNotEmpty(author.getPseudonym())) {
                    result += "(" + author.getPseudonym() + ")";
                }
            }
        }
        return result;
    }

    public String getGrantText() {
        if(YesOrNo.YES.equals(this.grant)) {
            return YesOrNo.YES_TEXT;
        }else{
            return YesOrNo.NO_TEXT;
        }
    }

    public String getSettlementTypeText() {
        if(YesOrNo.YES.equals(this.settlementType)) {
            return YesOrNo.YES_TEXT;
        }else{
            return YesOrNo.NO_TEXT;
        }
    }

    public String getCopyrightTypeText() {
        if(YesOrNo.YES.equals(this.copyrightType)) {
            return "专有授权许可";
        }else{
            return "非专有授权许可";
        }
    }

    public String getRadioTransText() {
        if(YesOrNo.YES.equals(this.radioTrans)) {
            return "可以";
        }else{
            return "不可以";
        }
    }
}
