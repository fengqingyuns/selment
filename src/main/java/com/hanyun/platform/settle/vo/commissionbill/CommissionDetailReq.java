package com.hanyun.platform.settle.vo.commissionbill;

import com.hanyun.platform.settle.vo.base.PageRequest;

/**
 * @author wangjie@hanyun.com
 * @Date 2018/3/1 12:18
 */
public class CommissionDetailReq extends PageRequest {
    private String commissionBillId;

    public String getCommissionBillId() {
        return commissionBillId;
    }

    public void setCommissionBillId(String commissionBillId) {
        this.commissionBillId = commissionBillId;
    }
}
