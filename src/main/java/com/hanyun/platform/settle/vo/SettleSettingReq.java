package com.hanyun.platform.settle.vo;

import com.hanyun.platform.settle.vo.base.PageRequest;

/**
 * Created by jack on 2017/5/13.
 */
public class SettleSettingReq extends PageRequest {

    private String entityId;
    private String  brandId;
    private String storeId;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
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
