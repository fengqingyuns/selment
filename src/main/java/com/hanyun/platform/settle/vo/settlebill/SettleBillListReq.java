package com.hanyun.platform.settle.vo.settlebill;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyun.platform.settle.vo.base.PageRequest;

import java.util.Date;

/**
 * @author:wangximin
 * @date:2017/4/6
 */
public class SettleBillListReq extends PageRequest {


    private Date beginTime;

    private Date endTime;

    private String brandId;

    private String storeId;

    private String settleStatus;

    private String beginDate;

    private String endDate;

    private Integer entityType;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public String getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus;
    }

    public Date getBeginTime() {

        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }
}
