package com.hanyun.platform.settle.vo.settlebill;

import com.hanyun.platform.settle.domain.SettleBill;
import com.hanyun.platform.settle.domain.SettleBillPaychn;
import com.hanyun.platform.settle.domain.SettleEntityBankAcc;

import java.util.List;

/**
 * @author:wangximin
 * @date:2017/4/7
 */
public class SettleBillRes {

    private SettleBill settleBill;

    private List<SettleBillPaychn> settleBillPaychnsList;

    private SettleEntityBankAcc settleEntityBankAcc;

    private List<SettleBill> settleBillList;

    public List<SettleBill> getSettleBillList() {
        return settleBillList;
    }

    public void setSettleBillList(List<SettleBill> settleBillList) {
        this.settleBillList = settleBillList;
    }

    public SettleEntityBankAcc getSettleEntityBankAcc() {
        return settleEntityBankAcc;
    }

    public void setSettleEntityBankAcc(SettleEntityBankAcc settleEntityBankAcc) {
        this.settleEntityBankAcc = settleEntityBankAcc;
    }

    public SettleBill getSettleBill() {
        return settleBill;
    }

    public void setSettleBill(SettleBill settleBill) {
        this.settleBill = settleBill;
    }

    public List<SettleBillPaychn> getSettleBillPaychnsList() {
        return settleBillPaychnsList;
    }

    public void setSettleBillPaychnsList(List<SettleBillPaychn> settleBillPaychnsList) {
        this.settleBillPaychnsList = settleBillPaychnsList;
    }
}
