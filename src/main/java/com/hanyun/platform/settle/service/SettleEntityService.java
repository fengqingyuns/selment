package com.hanyun.platform.settle.service;

import com.hanyun.platform.settle.domain.SettleEntity;
import com.hanyun.platform.settle.domain.SettleSetting;
import com.hanyun.platform.settle.vo.SettleSettingReq;
import com.hanyun.platform.settle.vo.base.PageResData;

/**
 * Created by jack on 2017/4/6.
 */
public interface SettleEntityService {

   public PageResData getSettleSetting(SettleSettingReq settleSettingReq);

   SettleEntity selectSettleSetting(SettleSetting settleSetting);

   public String addSettleSetting(SettleSetting settleSetting);

   public String deleteSettleSetting(SettleSettingReq settleSettingReq);

   public String updateSettleSetting(SettleSetting settleSetting);
}
