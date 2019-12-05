package com.hanyun.platform.settle.domain;

import java.util.Date;

/**
 * <pre>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * </pre>
 */
public class SettleEntity {

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

    private Integer commissionSettlementSwitch;

    private Long turnoverPoints;

    private Integer onlineCommission;

    private Integer commissionSettlementCircle;

    private Date commissionLastSettleTime;

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

    public Integer getCommissionSettlementSwitch() {
        return commissionSettlementSwitch;
    }

    public void setCommissionSettlementSwitch(Integer commissionSettlementSwitch) {
        this.commissionSettlementSwitch = commissionSettlementSwitch;
    }

    public Long getTurnoverPoints() {
        return turnoverPoints;
    }

    public void setTurnoverPoints(Long turnoverPoints) {
        this.turnoverPoints = turnoverPoints;
    }

    public Integer getOnlineCommission() {
        return onlineCommission;
    }

    public void setOnlineCommission(Integer onlineCommission) {
        this.onlineCommission = onlineCommission;
    }

    public Integer getCommissionSettlementCircle() {
        return commissionSettlementCircle;
    }

    public void setCommissionSettlementCircle(Integer commissionSettlementCircle) {
        this.commissionSettlementCircle = commissionSettlementCircle;
    }

    public Date getCommissionLastSettleTime() {
        return commissionLastSettleTime;
    }

    public void setCommissionLastSettleTime(Date commissionLastSettleTime) {
        this.commissionLastSettleTime = commissionLastSettleTime;
    }
}