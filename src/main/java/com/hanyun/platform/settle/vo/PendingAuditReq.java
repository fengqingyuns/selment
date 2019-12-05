package com.hanyun.platform.settle.vo;

import com.hanyun.platform.settle.vo.base.PageRequest;
import com.hanyun.platform.settle.vo.base.PageResData;

/**
 * Created by jack on 2017/5/24.
 */
public class PendingAuditReq extends PageRequest {
    private String userId;
    private String userName;
    private String brandId;
    private String storeId;
    private String roleId;
    private String roleName;


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }


}
