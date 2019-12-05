package com.hanyun.platform.settle.vo;

/**
 * 结算收入
 * @author wangjie@hanyun.com
 * @Date 2018/3/20 14:24
 */
public class SettleIncome {
    //日期
    private String dateStr;
    //分类
    private String industryId;
    private String industryName;
    private String brandId;
    //品牌
    private String brandName;
    private String storeId;
    //门店
    private String storeName;
    //省
    private String provice;
    //微信
    private Double wxAmount;
    //微信笔数
    private Long wxCount;
    //微信手续费
    private Double wxFee;
    //微信退款
    private Double wxRefund;
    //退款笔数
    private Long wxRefundCount;
    //退款手续费
    private Double wxRefundFee;
    //现金
    private Double cashAmount;
    //现金笔数
    private Long cashCount;
    //现金退款
    private Double cashRefund;
    //现金退款笔数
    private Long cashRefundCount;
    //银行卡
    private Double bankCarAmount;
    //银行卡笔数
    private Long bankCarCount;
    //银行卡手续费
    private Double bankCarFee;
    //银行卡退款
    private Double bankCarRefund;
    //退款笔数
    private Long bankCarRefundCount;
    //退款手续费
    private Double bankCarRefundFee;
    //支付宝
    private Double alipay;
    //支付宝笔数
    private Long alipayCount;
    //支付宝手续费
    private Double alipayFee;
    //支付宝退款
    private Double alipayRefund;
    //支付宝笔数
    private Long alipayRefundCount;
    //退款手续费
    private Double alipayRefundFee;
    //商场
    private Double shoppingMall;
    //商场笔数
    private Long shoppingMallCount;
    //商场收入
    private Double shoppingMallRefund;
    //退款笔数
    private Long shoppingMallRefundCount;
    //订单总数
    private Long orderCount;
    //总流水
    private Double totalFlow;
    //线上总流水
    private Double onlineTotalFlow;
    //手续费
    private Double Fee;
    //退款总金额
    private Double refundAmount;
    //汉云实收
    private Double hanyunRealAmount;
    //当日产生异常订单数
    private Long todayAbnormalCount;
    //异常合计金额
    private Double abnormalTotalAmount;
    //佣金扣除前金额
    private Double commissionDeductionBeforeAmount;
    //流水提点
    private Double turnoverPoints;
    //提点金额
    private Double todayCommission;
    //扣除佣金
    private Double commissionDeduction;
    //实际结算金额
    private Double settleAmt;
    //线下金额
    private Double offlineFlowAmt;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
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

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public Double getWxAmount() {
        return wxAmount;
    }

    public void setWxAmount(Double wxAmount) {
        this.wxAmount = wxAmount;
    }

    public Long getWxCount() {
        return wxCount;
    }

    public void setWxCount(Long wxCount) {
        this.wxCount = wxCount;
    }

    public Double getWxFee() {
        return wxFee;
    }

    public void setWxFee(Double wxFee) {
        this.wxFee = wxFee;
    }

    public Double getWxRefund() {
        return wxRefund;
    }

    public void setWxRefund(Double wxRefund) {
        this.wxRefund = wxRefund;
    }

    public Long getWxRefundCount() {
        return wxRefundCount;
    }

    public void setWxRefundCount(Long wxRefundCount) {
        this.wxRefundCount = wxRefundCount;
    }

    public Double getWxRefundFee() {
        return wxRefundFee;
    }

    public void setWxRefundFee(Double wxRefundFee) {
        this.wxRefundFee = wxRefundFee;
    }

    public Double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Long getCashCount() {
        return cashCount;
    }

    public void setCashCount(Long cashCount) {
        this.cashCount = cashCount;
    }

    public Double getCashRefund() {
        return cashRefund;
    }

    public void setCashRefund(Double cashRefund) {
        this.cashRefund = cashRefund;
    }

    public Long getCashRefundCount() {
        return cashRefundCount;
    }

    public void setCashRefundCount(Long cashRefundCount) {
        this.cashRefundCount = cashRefundCount;
    }

    public Double getBankCarAmount() {
        return bankCarAmount;
    }

    public void setBankCarAmount(Double bankCarAmount) {
        this.bankCarAmount = bankCarAmount;
    }

    public Long getBankCarCount() {
        return bankCarCount;
    }

    public void setBankCarCount(Long bankCarCount) {
        this.bankCarCount = bankCarCount;
    }

    public Double getBankCarFee() {
        return bankCarFee;
    }

    public void setBankCarFee(Double bankCarFee) {
        this.bankCarFee = bankCarFee;
    }

    public Double getBankCarRefund() {
        return bankCarRefund;
    }

    public void setBankCarRefund(Double bankCarRefund) {
        this.bankCarRefund = bankCarRefund;
    }

    public Long getBankCarRefundCount() {
        return bankCarRefundCount;
    }

    public void setBankCarRefundCount(Long bankCarRefundCount) {
        this.bankCarRefundCount = bankCarRefundCount;
    }

    public Double getBankCarRefundFee() {
        return bankCarRefundFee;
    }

    public void setBankCarRefundFee(Double bankCarRefundFee) {
        this.bankCarRefundFee = bankCarRefundFee;
    }

    public Double getAlipay() {
        return alipay;
    }

    public void setAlipay(Double alipay) {
        this.alipay = alipay;
    }

    public Long getAlipayCount() {
        return alipayCount;
    }

    public void setAlipayCount(Long alipayCount) {
        this.alipayCount = alipayCount;
    }

    public Double getAlipayFee() {
        return alipayFee;
    }

    public void setAlipayFee(Double alipayFee) {
        this.alipayFee = alipayFee;
    }

    public Double getAlipayRefund() {
        return alipayRefund;
    }

    public void setAlipayRefund(Double alipayRefund) {
        this.alipayRefund = alipayRefund;
    }

    public Long getAlipayRefundCount() {
        return alipayRefundCount;
    }

    public void setAlipayRefundCount(Long alipayRefundCount) {
        this.alipayRefundCount = alipayRefundCount;
    }

    public Double getAlipayRefundFee() {
        return alipayRefundFee;
    }

    public void setAlipayRefundFee(Double alipayRefundFee) {
        this.alipayRefundFee = alipayRefundFee;
    }

    public Double getShoppingMall() {
        return shoppingMall;
    }

    public void setShoppingMall(Double shoppingMall) {
        this.shoppingMall = shoppingMall;
    }

    public Long getShoppingMallCount() {
        return shoppingMallCount;
    }

    public void setShoppingMallCount(Long shoppingMallCount) {
        this.shoppingMallCount = shoppingMallCount;
    }

    public Double getShoppingMallRefund() {
        return shoppingMallRefund;
    }

    public void setShoppingMallRefund(Double shoppingMallRefund) {
        this.shoppingMallRefund = shoppingMallRefund;
    }

    public Long getShoppingMallRefundCount() {
        return shoppingMallRefundCount;
    }

    public void setShoppingMallRefundCount(Long shoppingMallRefundCount) {
        this.shoppingMallRefundCount = shoppingMallRefundCount;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }

    public Double getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(Double totalFlow) {
        this.totalFlow = totalFlow;
    }

    public Double getOnlineTotalFlow() {
        return onlineTotalFlow;
    }

    public void setOnlineTotalFlow(Double onlineTotalFlow) {
        this.onlineTotalFlow = onlineTotalFlow;
    }

    public Double getFee() {
        return Fee;
    }

    public void setFee(Double fee) {
        Fee = fee;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Double getHanyunRealAmount() {
        return hanyunRealAmount;
    }

    public void setHanyunRealAmount(Double hanyunRealAmount) {
        this.hanyunRealAmount = hanyunRealAmount;
    }



    public Double getAbnormalTotalAmount() {
        return abnormalTotalAmount;
    }

    public void setAbnormalTotalAmount(Double abnormalTotalAmount) {
        this.abnormalTotalAmount = abnormalTotalAmount;
    }

    public Double getCommissionDeductionBeforeAmount() {
        return commissionDeductionBeforeAmount;
    }

    public void setCommissionDeductionBeforeAmount(Double commissionDeductionBeforeAmount) {
        this.commissionDeductionBeforeAmount = commissionDeductionBeforeAmount;
    }

    public Double getTurnoverPoints() {
        return turnoverPoints;
    }

    public void setTurnoverPoints(Double turnoverPoints) {
        this.turnoverPoints = turnoverPoints;
    }

    public Double getTodayCommission() {
        return todayCommission;
    }

    public void setTodayCommission(Double todayCommission) {
        this.todayCommission = todayCommission;
    }

    public Double getCommissionDeduction() {
        return commissionDeduction;
    }

    public void setCommissionDeduction(Double commissionDeduction) {
        this.commissionDeduction = commissionDeduction;
    }

    public Double getSettleAmt() {
        return settleAmt;
    }

    public void setSettleAmt(Double settleAmt) {
        this.settleAmt = settleAmt;
    }

    public Double getOfflineFlowAmt() {
        return offlineFlowAmt;
    }

    public void setOfflineFlowAmt(Double offlineFlowAmt) {
        this.offlineFlowAmt = offlineFlowAmt;
    }

    public Long getTodayAbnormalCount() {
        return todayAbnormalCount;
    }

    public void setTodayAbnormalCount(Long todayAbnormalCount) {
        this.todayAbnormalCount = todayAbnormalCount;
    }
}
