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
public class CommissionDeductionDetail {

    private Long id;

    private Date createTime;

    private Date updateTime;

    private String commissionDeductionId;

    private String commissionBillId;

    private String billId;

    private String brandId;

    private String storeId;

    private String brandName;

    private String storeName;

    private Date tradeDateStart;

    private Date tradeDateEnd;

    private Integer commissionSettleType;

    private Long commissionAmount;

    private Long deductionAmount;

    private Long settledAmount;

    private String remark;

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

    public String getCommissionDeductionId() {
        return commissionDeductionId;
    }

    public void setCommissionDeductionId(String commissionDeductionId) {
        this.commissionDeductionId = commissionDeductionId;
    }

    public String getCommissionBillId() {
        return commissionBillId;
    }

    public void setCommissionBillId(String commissionBillId) {
        this.commissionBillId = commissionBillId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public Integer getCommissionSettleType() {
        return commissionSettleType;
    }

    public void setCommissionSettleType(Integer commissionSettleType) {
        this.commissionSettleType = commissionSettleType;
    }

    public Long getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(Long commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public Long getDeductionAmount() {
        return deductionAmount;
    }

    public void setDeductionAmount(Long deductionAmount) {
        this.deductionAmount = deductionAmount;
    }

    public Long getSettledAmount() {
        return settledAmount;
    }

    public void setSettledAmount(Long settledAmount) {
        this.settledAmount = settledAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}