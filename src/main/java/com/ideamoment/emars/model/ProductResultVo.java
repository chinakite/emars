package com.ideamoment.emars.model;

/**
 * Created by yukiwang on 2018/2/24.
 */
public class ProductResultVo extends Product {

    private String authorName;
    private String subjectName;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
