package com.hanyun.platform.settle.vo;

import java.util.List;

/**
 * @author wangjie@hanyun.com
 * @Date 2018/3/22 16:48
 */
public class OperationsSettle {

    //结算收入
    private List<SettleIncome> settleIncomeList;
    //联动对账
    private List<UmpayBill> umpayBillList;

    public List<SettleIncome> getSettleIncomeList() {
        return settleIncomeList;
    }

    public void setSettleIncomeList(List<SettleIncome> settleIncomeList) {
        this.settleIncomeList = settleIncomeList;
    }

    public List<UmpayBill> getUmpayBillList() {
        return umpayBillList;
    }

    public void setUmpayBillList(List<UmpayBill> umpayBillList) {
        this.umpayBillList = umpayBillList;
    }
}
