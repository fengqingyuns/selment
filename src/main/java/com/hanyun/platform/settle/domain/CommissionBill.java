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
public class CommissionBill {

    private Integer id;

    private String commissionBillId;

    private String entityId;

    private Integer entityType;

    private String entityName;

    private String brandId;

    private String brandName;

    private String storeId;

    private String storeName;

    private Date tradeDateStart;

    private Date tradeDateEnd;

    private Integer commissionBillCircle;

    private Integer commissionBillStatus;

    private Long commissionAmount;

    private Long settledAmount;

    private Integer onlineCommission;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommissionBillId() {
        return commissionBillId;
    }

    public void setCommissionBillId(String commissionBillId) {
        this.commissionBillId = commissionBillId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

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

    public Integer getCommissionBillCircle() {
        return commissionBillCircle;
    }

    public void setCommissionBillCircle(Integer commissionBillCircle) {
        this.commissionBillCircle = commissionBillCircle;
    }

    public Integer getCommissionBillStatus() {
        return commissionBillStatus;
    }

    public void setCommissionBillStatus(Integer commissionBillStatus) {
        this.commissionBillStatus = commissionBillStatus;
    }

    public Long getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(Long commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public Long getSettledAmount() {
        return settledAmount;
    }

    public void setSettledAmount(Long settledAmount) {
        this.settledAmount = settledAmount;
    }

    public Integer getOnlineCommission() {
        return onlineCommission;
    }

    public void setOnlineCommission(Integer onlineCommission) {
        this.onlineCommission = onlineCommission;
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
}