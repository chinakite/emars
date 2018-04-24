package com.ideamoment.emars.model;

import com.ideamoment.emars.model.enumeration.PublishState;
import com.ideamoment.emars.model.enumeration.StockInType;

public class ProductInfo extends HistoriableEntity {

    private String name;
    private String authorName;
    private String authorPseudonym;
    private String wordCount;
    private Long subjectId;
    private String publishState;
    private String isbn;
    private String press;
    private String type;
    private String stockIn;
    private String desc;

    private long authorId;
    private String subjectName;

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

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
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

    public String getStockInText() {
        if(StockInType.STOCK_IN.equals(this.stockIn)) {
            return StockInType.STOCK_IN_TEXT;
        }else{
            return StockInType.NOT_STOCK_IN_TEXT;
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
}
