package com.hanyun.platform.settle.vo;

import java.util.Date;

/**
 * Created by jack on 2018/3/5.
 */
public class CommissionSettleEntityReq {

    private String brandId;

    private String storeId;

    private Integer entityType;

    private Integer availStatus;

    private Integer separateSettle;

    private Integer commissionSettlementSwitch;

    public Integer getSeparateSettle() {
        return separateSettle;
    }

    public void setSeparateSettle(Integer separateSettle) {
        this.separateSettle = separateSettle;
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

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public Integer getAvailStatus() {
        return availStatus;
    }

    public void setAvailStatus(Integer availStatus) {
        this.availStatus = availStatus;
    }

    public Integer getCommissionSettlementSwitch() {
        return commissionSettlementSwitch;
    }

    public void setCommissionSettlementSwitch(Integer commissionSettlementSwitch) {
        this.commissionSettlementSwitch = commissionSettlementSwitch;
    }
}
