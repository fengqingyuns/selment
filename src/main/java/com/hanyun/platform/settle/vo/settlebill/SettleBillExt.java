package com.hanyun.platform.settle.vo.settlebill;

import com.hanyun.platform.settle.domain.SettleBill;

/**
 * Created by jack on 2017/6/16.
 */
public class SettleBillExt extends SettleBill {

    private Long WEIXIN;
    private Long ALIPAY;
    private Long BANKCARD;
    private Long CASH;

    public Long getWEIXIN() {
        return WEIXIN;
    }

    public void setWEIXIN(Long WEIXIN) {
        this.WEIXIN = WEIXIN;
    }

    public Long getALIPAY() {
        return ALIPAY;
    }

    public void setALIPAY(Long ALIPAY) {
        this.ALIPAY = ALIPAY;
    }

    public Long getBANKCARD() {
        return BANKCARD;
    }

    public void setBANKCARD(Long BANKCARD) {
        this.BANKCARD = BANKCARD;
    }

    public Long getCASH() {
        return CASH;
    }

    public void setCASH(Long CASH) {
        this.CASH = CASH;
    }
}
