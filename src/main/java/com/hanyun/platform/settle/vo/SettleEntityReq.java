package com.hanyun.platform.settle.vo;

import java.util.Date;

/**
 * Created by jack on 2017/4/6.
 */
public class SettleEntityReq {
    private Long id;

    private Date createTime;

    private Date updateTime;

    private String entityId;

    private String entityName;

    private Integer entityType;

    private String brandId;

    private String storeId;

    private Integer separateSettle;

    private Integer capitalCollect;

    private Integer capitalCollectType;

    private Integer settleCircle;

    private Date lastSettleTime;

    private Integer availStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
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

    public Integer getSeparateSettle() {
        return separateSettle;
    }

    public void setSeparateSettle(Integer separateSettle) {
        this.separateSettle = separateSettle;
    }

    public Integer getCapitalCollect() {
        return capitalCollect;
    }

    public void setCapitalCollect(Integer capitalCollect) {
        this.capitalCollect = capitalCollect;
    }

    public Integer getCapitalCollectType() {
        return capitalCollectType;
    }

    public void setCapitalCollectType(Integer capitalCollectType) {
        this.capitalCollectType = capitalCollectType;
    }

    public Integer getSettleCircle() {
        return settleCircle;
    }

    public void setSettleCircle(Integer settleCircle) {
        this.settleCircle = settleCircle;
    }

    public Date getLastSettleTime() {
        return lastSettleTime;
    }

    public void setLastSettleTime(Date lastSettleTime) {
        this.lastSettleTime = lastSettleTime;
    }

    public Integer getAvailStatus() {
        return availStatus;
    }

    public void setAvailStatus(Integer availStatus) {
        this.availStatus = availStatus;
    }
}
