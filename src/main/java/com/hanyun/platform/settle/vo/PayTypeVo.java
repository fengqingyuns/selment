package com.hanyun.platform.settle.vo;

/**
 * @author wangjie@hanyun.com
 * @Date 2018/3/23 7:52
 */
public class PayTypeVo {

    private Long payTypeTotalAmount;

    private Long payTypeCount;

    private Long payTypeFee;

    public Long getPayTypeTotalAmount() {
        return payTypeTotalAmount;
    }

    public void setPayTypeTotalAmount(Long payTypeTotalAmount) {
        this.payTypeTotalAmount = payTypeTotalAmount;
    }

    public Long getPayTypeCount() {
        return payTypeCount;
    }

    public void setPayTypeCount(Long payTypeCount) {
        this.payTypeCount = payTypeCount;
    }

    public Long getPayTypeFee() {
        return payTypeFee;
    }

    public void setPayTypeFee(Long payTypeFee) {
        this.payTypeFee = payTypeFee;
    }
}
