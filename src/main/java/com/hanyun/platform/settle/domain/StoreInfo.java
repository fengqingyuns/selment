package com.hanyun.platform.settle.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author wangjie@hanyun.com
 * @Date 2017/10/29 11:46
 */
public class StoreInfo {

    private String brandId;

    private String storeId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoreInfo storeInfo = (StoreInfo) o;

        if (!brandId.equals(storeInfo.brandId)) return false;
        return storeId.equals(storeInfo.storeId);
    }

    @Override
    public int hashCode() {
        int result = brandId.hashCode();
        result = 31 * result + storeId.hashCode();
        return result;
    }
}
