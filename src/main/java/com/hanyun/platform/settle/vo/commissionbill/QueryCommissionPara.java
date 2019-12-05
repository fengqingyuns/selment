package com.hanyun.platform.settle.vo.commissionbill;

import com.hanyun.platform.settle.vo.base.PageRequest;

import java.util.Date;

/**
 * @author wangjie@hanyun.com
 * @Date 2018/3/1 15:48
 */
public class QueryCommissionPara extends PageRequest {

    private String brandId;
    private String storeId;
    private Integer entityType;
    private String tradeDateStart;
    private String tradeDateEnd;
    private Date beginTime;
    private Date endTime;

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getTradeDateStart() {
        return tradeDateStart;
    }

    public void setTradeDateStart(String tradeDateStart) {
        this.tradeDateStart = tradeDateStart;
    }

    public String getTradeDateEnd() {
        return tradeDateEnd;
    }

    public void setTradeDateEnd(String tradeDateEnd) {
        this.tradeDateEnd = tradeDateEnd;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
