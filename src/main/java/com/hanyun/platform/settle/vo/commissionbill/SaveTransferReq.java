package com.hanyun.platform.settle.vo.commissionbill;

/**
 * @author wangjie@hanyun.com
 * @Date 2018/2/28 12:00
 */
public class SaveTransferReq {

    private String[] commissionBillIds;
    private Long  amount;
    private String remark;

    public String[] getCommissionBillIds() {
        return commissionBillIds;
    }

    public void setCommissionBillIds(String[] commissionBillIds) {
        this.commissionBillIds = commissionBillIds;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
