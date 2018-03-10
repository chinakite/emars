package com.ideamoment.emars.model;

/**
 * 系统用户实体
 *
 */
public class User extends HistoriableEntity {
    /**
     * 姓名
     */
    private String name;

    /**
     * 账号
     */
    private String account;

    /**
     * Email
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 性别
     */
    private String gender;

    /**
     * 敬称, 用于邮件等场合
     */
    private String honorific;

    /**
     * 头像
     */
    private String logoUrl;

    /**
     * 状态:禁用\启用
     */
    private String status;

    /**
     * 角色
     */
    private String role;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHonorific() {
        return honorific;
    }

    public void setHonorific(String honorific) {
        this.honorific = honorific;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
