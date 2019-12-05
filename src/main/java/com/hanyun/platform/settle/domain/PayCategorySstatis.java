package com.hanyun.platform.settle.domain;

/**
 * Created by jack on 2017/4/10.
 */
public class PayCategorySstatis {
    //支付方式
    private String payTypeCategory;
    //扣款流水
    private long total;
    //手续费
    private long feeTotal;
    //退款流水
    private long refTotal;
    //退款手续费
    private long refFeeTotal;

    public String getPayTypeCategory() {
        return payTypeCategory;
    }

    public void setPayTypeCategory(String payTypeCategory) {
        this.payTypeCategory = payTypeCategory;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(long feeTotal) {
        this.feeTotal = feeTotal;
    }

    public long getRefTotal() {
        return refTotal;
    }

    public void setRefTotal(long refTotal) {
        this.refTotal = refTotal;
    }

    public long getRefFeeTotal() {
        return refFeeTotal;
    }

    public void setRefFeeTotal(long refFeeTotal) {
        this.refFeeTotal = refFeeTotal;
    }
}
