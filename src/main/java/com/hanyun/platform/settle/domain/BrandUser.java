package com.hanyun.platform.settle.domain;

/**
 * Created by jack on 2017/5/8.
 */
public class BrandUser {

    private String brandUserId;

    private String brandId;

    private String username;

    private String userType;


    public String getBrandUserId() {
        return brandUserId;
    }

    public void setBrandUserId(String brandUserId) {
        this.brandUserId = brandUserId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
