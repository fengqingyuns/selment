package com.hanyun.platform.settle.service;

import java.util.Date;

import com.hanyun.platform.settle.vo.settlebill.SettleBillGenResultVo;

/**
 * Created by jack on 2017/4/6.
 */
public interface SettleBillGenService {

    /**
     * 预生成结算单
     * @param date 交易日期
     * @param brandId 品牌编号
     * @return
     */
    public SettleBillGenResultVo preGenSettleBill(Date date, String brandId);

    /**
     * 生成结算单
     * @param date 交易日期
     * @param brandId 品牌编号
     * @return
     */
    public SettleBillGenResultVo genSettleBill(Date date, String brandId);
}
