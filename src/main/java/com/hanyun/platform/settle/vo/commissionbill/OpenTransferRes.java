package com.hanyun.platform.settle.vo.commissionbill;

import com.hanyun.platform.settle.domain.CommissionBill;

import java.util.List;

/**
 * 展示批量转账页面信息
 * @author wangjie@hanyun.com
 * @Date 2018/2/27 17:48
 */
public class OpenTransferRes {

    private String brandName;
    private String storeName;
    private List<CommissionBill> commissionBillList;
    private long amount;

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

    public List<CommissionBill> getCommissionBillList() {
        return commissionBillList;
    }

    public void setCommissionBillList(List<CommissionBill> commissionBillList) {
        this.commissionBillList = commissionBillList;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
