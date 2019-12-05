package com.hanyun.platform.settle.vo.settlebill;

import com.hanyun.platform.settle.domain.SettleBill;

import java.util.Date;

/**
 * Created by jack on 2017/6/16.
 */
public class SettleBillExcel {

    private String billId;
    private String brandId;
    private String storeId;
    private String tradeDateStart;
    private String settleCircle;
    private Double weixin;
    private Double alipay;
    private Double bankcard;
    private Double cash;
    private Double totalFlowAmt;
    private Double onlineFlowAmt;
    private Double payFee;
    private Double entityActualRecAmt;
    private int diffSubmitCnt;
    private Double diffTotalAmt;
    private Double settleAmt;
    private String settleStatus;

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

    public String getTradeDateStart() {
        return tradeDateStart;
    }

    public void setTradeDateStart(String tradeDateStart) {
        this.tradeDateStart = tradeDateStart;
    }

    public String getSettleCircle() {
        return settleCircle;
    }

    public void setSettleCircle(String settleCircle) {
        this.settleCircle = settleCircle;
    }

    public Double getWeixin() {
        return weixin;
    }

    public void setWeixin(Double weixin) {
        this.weixin = weixin;
    }

    public Double getAlipay() {
        return alipay;
    }

    public void setAlipay(Double alipay) {
        this.alipay = alipay;
    }

    public Double getBankcard() {
        return bankcard;
    }

    public void setBankcard(Double bankcard) {
        this.bankcard = bankcard;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public Double getTotalFlowAmt() {
        return totalFlowAmt;
    }

    public void setTotalFlowAmt(Double totalFlowAmt) {
        this.totalFlowAmt = totalFlowAmt;
    }

    public Double getOnlineFlowAmt() {
        return onlineFlowAmt;
    }

    public void setOnlineFlowAmt(Double onlineFlowAmt) {
        this.onlineFlowAmt = onlineFlowAmt;
    }

    public Double getPayFee() {
        return payFee;
    }

    public void setPayFee(Double payFee) {
        this.payFee = payFee;
    }

    public Double getEntityActualRecAmt() {
        return entityActualRecAmt;
    }

    public void setEntityActualRecAmt(Double entityActualRecAmt) {
        this.entityActualRecAmt = entityActualRecAmt;
    }

    public int getDiffSubmitCnt() {
        return diffSubmitCnt;
    }

    public void setDiffSubmitCnt(int diffSubmitCnt) {
        this.diffSubmitCnt = diffSubmitCnt;
    }

    public Double getDiffTotalAmt() {
        return diffTotalAmt;
    }

    public void setDiffTotalAmt(Double diffTotalAmt) {
        this.diffTotalAmt = diffTotalAmt;
    }

    public Double getSettleAmt() {
        return settleAmt;
    }

    public void setSettleAmt(Double settleAmt) {
        this.settleAmt = settleAmt;
    }

    public String getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus;
    }
}
