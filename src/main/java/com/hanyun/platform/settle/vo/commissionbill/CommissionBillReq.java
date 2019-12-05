package com.hanyun.platform.settle.vo.commissionbill;

import com.hanyun.platform.settle.vo.base.PageRequest;

import java.util.Date;

/**
 * @author wangjie@hanyun.com
 * @Date 2018/2/27 12:09
 */
public class CommissionBillReq extends PageRequest {

    private Integer id;

    private String commissionBillId;

    private String entityId;

    private Integer entityType;

    private String entityName;

    private String brandId;

    private String brandName;

    private String storeId;

    private String storeName;

    private String tradeDateStart;

    private String tradeDateEnd;

    private Date beginTime;

    private Date endTime;

    private Integer commissionBillCircle;

    private Integer commissionBillStatus;

    private Long commissionAmount;

    private Long settledAmount;

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

    public void setTradeDateStart(String tradeDateStart) {
        this.tradeDateStart = tradeDateStart;
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

    public String getTradeDateStart() {
        return tradeDateStart;
    }

    public String getTradeDateEnd() {
        return tradeDateEnd;
    }
}
