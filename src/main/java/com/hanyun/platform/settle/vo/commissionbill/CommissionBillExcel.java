package com.hanyun.platform.settle.vo.commissionbill;

import java.util.Date;

/**
 * @author wangjie@hanyun.com
 * @Date 2018/3/1 19:01
 */
public class CommissionBillExcel {

    private String commissionBillId;

    private String brandName;

    private String storeName;

    private String tradeDate;

    private String commissionBillCircle;

    private Double   commissionAmount;

    private String commissionBillStatus;

    private Double settledAmount;

    private Double unSettleAmount;

    public String getCommissionBillId() {
        return commissionBillId;
    }

    public void setCommissionBillId(String commissionBillId) {
        this.commissionBillId = commissionBillId;
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

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getCommissionBillCircle() {
        return commissionBillCircle;
    }

    public void setCommissionBillCircle(String commissionBillCircle) {
        this.commissionBillCircle = commissionBillCircle;
    }

    public Double getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(Double commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public String getCommissionBillStatus() {
        return commissionBillStatus;
    }

    public void setCommissionBillStatus(String commissionBillStatus) {
        this.commissionBillStatus = commissionBillStatus;
    }

    public Double getSettledAmount() {
        return settledAmount;
    }

    public void setSettledAmount(Double settledAmount) {
        this.settledAmount = settledAmount;
    }

    public Double getUnSettleAmount() {
        return unSettleAmount;
    }

    public void setUnSettleAmount(Double unSettleAmount) {
        this.unSettleAmount = unSettleAmount;
    }
}
