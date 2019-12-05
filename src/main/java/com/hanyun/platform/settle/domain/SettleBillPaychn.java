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
public class SettleBillPaychn {

    private Long id;

    private Date createTime;

    private Date updateTime;

    private String payTypeCategory;

    private String billId;

    private String entityId;

    private String brandId;

    private String storeId;

    private Long totalFlowAmt;

    private Long payFee;

    private Long entityShaltRecAmt;

    private Long entityActualRecAmt;

    private Long bankCollectAmt;

    private Long platformActualRecAmt;

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

    public String getPayTypeCategory() {
        return payTypeCategory;
    }

    public void setPayTypeCategory(String payTypeCategory) {
        this.payTypeCategory = payTypeCategory;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

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

    public Long getTotalFlowAmt() {
        return totalFlowAmt;
    }

    public void setTotalFlowAmt(Long totalFlowAmt) {
        this.totalFlowAmt = totalFlowAmt;
    }

    public Long getPayFee() {
        return payFee;
    }

    public void setPayFee(Long payFee) {
        this.payFee = payFee;
    }

    public Long getEntityShaltRecAmt() {
        return entityShaltRecAmt;
    }

    public void setEntityShaltRecAmt(Long entityShaltRecAmt) {
        this.entityShaltRecAmt = entityShaltRecAmt;
    }

    public Long getEntityActualRecAmt() {
        return entityActualRecAmt;
    }

    public void setEntityActualRecAmt(Long entityActualRecAmt) {
        this.entityActualRecAmt = entityActualRecAmt;
    }

    public Long getBankCollectAmt() {
        return bankCollectAmt;
    }

    public void setBankCollectAmt(Long bankCollectAmt) {
        this.bankCollectAmt = bankCollectAmt;
    }

    public Long getPlatformActualRecAmt() {
        return platformActualRecAmt;
    }

    public void setPlatformActualRecAmt(Long platformActualRecAmt) {
        this.platformActualRecAmt = platformActualRecAmt;
    }


    public SettleBillPaychn() {
    }

    public SettleBillPaychn(String billId, String brandId) {
        this.billId = billId;
        this.brandId = brandId;
    }
}