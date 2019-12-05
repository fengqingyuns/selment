package com.hanyun.platform.settle.service;

import com.hanyun.platform.settle.domain.SettleBill;
import com.hanyun.platform.settle.domain.SettleEntity;
import com.hanyun.platform.settle.vo.settlebill.CommissionDeductionResultVo;

import java.util.Date;

/**
 * Created by admin on 2018/3/29.
 */
public interface CommissionBillDeductionService {
    CommissionDeductionResultVo commissionDeducted(Date date, String brandId);
    /**佣金扣除**/
    long commissionDeducted(SettleEntity settleEntity, SettleBill settleBill);

}
