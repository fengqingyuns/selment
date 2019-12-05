package com.hanyun.platform.settle.vo.commissionbill;

import com.hanyun.platform.settle.domain.CommissionBill;
import com.hanyun.platform.settle.domain.CommissionDeductionDetail;

import java.util.List;

/**
 * @author wangjie@hanyun.com
 * @Date 2018/3/1 8:42
 */
public class CommissionBillDetailRes {

    //详情-基本信息
    private CommissionBill commissionBill;
    //详情-结算记录列表
    private List<CommissionDeductionDetail> commissionDeductionDetailList;


    public CommissionBill getCommissionBill() {
        return commissionBill;
    }

    public void setCommissionBill(CommissionBill commissionBill) {
        this.commissionBill = commissionBill;
    }

    public List<CommissionDeductionDetail> getCommissionDeductionDetailList() {
        return commissionDeductionDetailList;
    }

    public void setCommissionDeductionDetailList(List<CommissionDeductionDetail> commissionDeductionDetailList) {
        this.commissionDeductionDetailList = commissionDeductionDetailList;
    }
}
