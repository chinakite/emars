package com.ideamoment.emars.model;

import com.ideamoment.emars.model.enumeration.ProductionState;
import com.ideamoment.emars.model.enumeration.PublishState;
import com.ideamoment.emars.model.enumeration.StockInType;
import com.ideamoment.emars.utils.StringUtils;

import java.util.List;

public class ProductInfo extends HistoriableEntity {

    private String name;
    private String authorName;
    private String authorPseudonym;
    private String wordCount;
    private Long subjectId;
    private String publishState;
    private String isbn;
    private String type;
    private String stockIn;
    private String desc;
    private String productionState;
    private Integer section;
    private Integer sort;

    private String subjectName;
    private String copyrightCode;
    private List<Author> authors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorPseudonym() {
        return authorPseudonym;
    }

    public void setAuthorPseudonym(String authorPseudonym) {
        this.authorPseudonym = authorPseudonym;
    }

    public String getWordCount() {
        return wordCount;
    }

    public void setWordCount(String wordCount) {
        this.wordCount = wordCount;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getStockInText() {
        if(StockInType.STOCK_IN.equals(this.stockIn)) {
            return StockInType.STOCK_IN_TEXT;
        }else{
            return StockInType.NOT_STOCK_IN_TEXT;
        }
    }

    public String getProductionStateText() {
        if(ProductionState.THE_OLD_PROGRAM.equals(this.productionState)) {
            return ProductionState.THE_OLD_PROGRAM_TEXT;
        }else if(ProductionState.THE_OWN.equals(this.productionState)) {
            return ProductionState.THE_OWN_TEXT;
        }else if(ProductionState.TO_BE_MADE.equals(this.productionState)) {
            return ProductionState.TO_BE_MADE_TEXT;
        }else if(ProductionState.RESERVATION.equals(this.productionState)) {
            return ProductionState.RESERVATION_TEXT;
        }else if(ProductionState.HAS_BEEN_PRODUCED.equals(this.productionState)) {
            return ProductionState.HAS_BEEN_PRODUCED_TEXT;
        }else {
            return "未知";
        }
    }

    public String getPublishStateText() {
        if(PublishState.PUBLISHED.equals(this.publishState)) {
            return PublishState.PUBLISHED_TEXT;
        }else if(PublishState.NET_SIGNED.equals(this.publishState)) {
            return PublishState.NET_SIGNED_TEXT;
        }else if(PublishState.NET_UN_SIGNED.equals(this.publishState)) {
            return PublishState.NET_UN_SIGNED_TEXT;
        }else if(PublishState.UN_PUBLIC.equals(this.publishState)) {
            return PublishState.UN_PUBLIC_TEXT;
        }else{
            return "未知";
        }
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

    public String getAuthorsTextMultiline() {
        String result = "";
        if(authors != null) {
            for (int i = 0; i < this.authors.size(); i++) {
                Author author = authors.get(i);
                if (i > 0) {
                    result += "<br/>";
                }
                result += author.getName();
                if (StringUtils.isNotEmpty(author.getPseudonym())) {
                    result += "(" + author.getPseudonym() + ")";
                }
            }
        }
        return result;
    }

    public String getProductionState() {
        return productionState;
    }

    public void setProductionState(String productionState) {
        this.productionState = productionState;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCopyrightCode() {
        return copyrightCode;
    }

    public void setCopyrightCode(String copyrightCode) {
        this.copyrightCode = copyrightCode;
    }
}
