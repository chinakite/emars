package com.ideamoment.emars.model;

import com.ideamoment.emars.model.enumeration.ProductState;
import com.ideamoment.emars.model.enumeration.PublishState;

import java.math.BigDecimal;

/**
 * Created by yukiwang on 2018/2/24.
 */
public class Product extends HistoriableEntity {

    private String name;
    private long subjectId;
    private long authorId;
    private String type;
    private String publishState;
    private String publishYear;
    private String finishYear;
    private String state;
    private BigDecimal wordCount;
    private int sectionCount;
    private int sectionLength;
    private String press;
    private String website;
    private String summary;
    private boolean hasAudio;
    private boolean audioCopyright;
    private String audioDesc;
    private String isbn;
    private String logoUrl;
    private boolean reserved;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublishState() {
        return publishState;
    }

    public void setPublishState(String publishState) {
        this.publishState = publishState;
    }

    public String getPublishStateText() {
        if(PublishState.PUBLISHED.equals(this.publishState)) {
            return PublishState.PUBLISHED_TEXT;
        }else if(PublishState.NET_SIGNED.equals(this.publishState)){
            return PublishState.NET_SIGNED_TEXT;
        }else if(PublishState.NET_UN_SIGNED.equals(this.publishState)){
            return PublishState.NET_UN_SIGNED_TEXT;
        }else{
            return PublishState.UN_PUBLIC_TEXT;
        }
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public String getFinishYear() {
        return finishYear;
    }

    public void setFinishYear(String finishYear) {
        this.finishYear = finishYear;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateText() {
        if(ProductState.APPROVE_WAITING.equals(this.state)) {
            return ProductState.APPROVE_WAITING_TEXT;
        }else if(ProductState.APPROVE_REJECT.equals(this.state)){
            return ProductState.APPROVE_REJECT_TEXT;
        }else if(ProductState.EVALUATE_WAITING.equals(this.state)){
            return ProductState.EVALUATE_WAITING_TEXT;
        }else if(ProductState.EVALUATED.equals(this.state)){
            return ProductState.EVALUATED_TEXT;
        }else if(ProductState.EVALUATE_FINISH.equals(this.state)){
            return ProductState.EVALUATE_FINISH_TEXT;
        }else if(ProductState.MK_CONTRACT.equals(this.state)){
            return ProductState.MK_CONTRACT_TEXT;
        }else if(ProductState.MK.equals(this.state)){
            return ProductState.MK_TEXT;
        }else if(ProductState.MK_FINISH.equals(this.state)){
            return ProductState.MK_FINISH_TEXT;
        }else if(ProductState.MK_WAITING.equals(this.state)){
            return ProductState.MK_WAITING_TEXT;
        }else if(ProductState.SALED.equals(this.state)){
            return ProductState.SALED_TEXT;
        }else if(ProductState.CP_CONTRACT_INFLOW.equals(this.state)){
            return ProductState.CP_CONTRACT_INFLOW_TEXT;
        }else if(ProductState.CP_CONTRACT_FINISH.equals(this.state)){
            return ProductState.CP_CONTRACT_FINISH_TEXT;
        }else if(ProductState.RESERVED.equals(this.state)){
            return ProductState.RESERVED_TEXT;
        }else if(ProductState.CPFILE_APPROVE_WAITING.equals(this.state)){
            return ProductState.CPFILE_APPROVE_WAITING_TEXT;
        }
        else{
            return ProductState.DRAFT_TEXT;
        }
    }

    public BigDecimal getWordCount() {
        return wordCount;
    }

    public void setWordCount(BigDecimal wordCount) {
        this.wordCount = wordCount;
    }

    public int getSectionCount() {
        return sectionCount;
    }

    public void setSectionCount(int sectionCount) {
        this.sectionCount = sectionCount;
    }

    public int getSectionLength() {
        return sectionLength;
    }

    public void setSectionLength(int sectionLength) {
        this.sectionLength = sectionLength;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isHasAudio() {
        return hasAudio;
    }

    public void setHasAudio(boolean hasAudio) {
        this.hasAudio = hasAudio;
    }

    public boolean isAudioCopyright() {
        return audioCopyright;
    }

    public void setAudioCopyright(boolean audioCopyright) {
        this.audioCopyright = audioCopyright;
    }

    public String getAudioDesc() {
        return audioDesc;
    }

    public void setAudioDesc(String audioDesc) {
        this.audioDesc = audioDesc;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
}
