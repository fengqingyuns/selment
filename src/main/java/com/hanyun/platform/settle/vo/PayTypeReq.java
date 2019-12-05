package com.hanyun.platform.settle.vo;

import java.util.Date;

/**
 * @author wangjie@hanyun.com
 * @Date 2018/3/23 9:58
 */
public class PayTypeReq {

    private String brandId;
    private String storeId;
    private Integer operateType;
    private String typeCategory;
    private Integer diffStatus;
    private Date tradeDateStart;
    private Date tradeDateEnd;

    public Date getTradeDateStart() {
        return tradeDateStart;
    }

    public void setTradeDateStart(Date tradeDateStart) {
        this.tradeDateStart = tradeDateStart;
    }

    public Date getTradeDateEnd() {
        return tradeDateEnd;
    }

    public void setTradeDateEnd(Date tradeDateEnd) {
        this.tradeDateEnd = tradeDateEnd;
    }

    public Integer getDiffStatus() {
        return diffStatus;
    }

    public void setDiffStatus(Integer diffStatus) {
        this.diffStatus = diffStatus;
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

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getTypeCategory() {
        return typeCategory;
    }

    public void setTypeCategory(String typeCategory) {
        this.typeCategory = typeCategory;
    }
}
