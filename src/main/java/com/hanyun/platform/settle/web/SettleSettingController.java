package com.hanyun.platform.settle.web;

import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.consts.BizResCode;
import com.hanyun.platform.settle.consts.CommissionConsts;
import com.hanyun.platform.settle.domain.SettleEntity;
import com.hanyun.platform.settle.domain.SettleSetting;
import com.hanyun.platform.settle.service.SettleEntityService;
import com.hanyun.platform.settle.util.BizResUtil;
import com.hanyun.platform.settle.vo.SettleSettingReq;
import com.hanyun.platform.settle.vo.base.PageResData;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by jack on 2017/5/13.
 */
@Controller
@RequestMapping(value = "/settle-setting")
public class SettleSettingController {
    private static Logger LOGGER = LoggerFactory.getLogger(SettleSettingController.class);
    private String success = "0";
    @Resource
    private SettleEntityService settleEntityService;

    /**
     * 查询
     * @param settleSettingReq
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/query")
    public HttpResponse<PageResData> getSettleSetting(@RequestBody SettleSettingReq settleSettingReq){
        HttpResponse<PageResData> httpResponse = null;

      try{
         PageResData pageResData = settleEntityService.getSettleSetting(settleSettingReq);
          httpResponse = BizResUtil.succ(pageResData);
      } catch (Exception e) {
          LOGGER.error("结算设置查询异常",e);
      }
      return httpResponse;
    }

    /**
     * 新增结算基础设置
     * @param settleSetting
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    public HttpResponse<String> addSetttleSetting(@RequestBody SettleSetting settleSetting){
        HttpResponse<String> httpResponse = null;

        LOGGER.info("新增结算设置", JsonUtil.toJson(settleSetting));
        if (!success.equals(settleParameterVerify(settleSetting).getCode())){
            return settleParameterVerify(settleSetting);
        }
        String addSettle = settleEntityService.addSettleSetting(settleSetting);
        httpResponse = BizResUtil.succ(addSettle);
        return httpResponse;
    }

    /**
     * 删除结算基础设置
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete")
    public HttpResponse<String> deleteSettleSetting(@RequestBody SettleSettingReq settleSettingReq){
        HttpResponse<String> httpResponse = null;
        LOGGER.info("删除结算设置", JsonUtil.toJson(settleSettingReq));
        if (StringUtils.isEmpty(settleSettingReq.getEntityId())) {
            LOGGER.error("结算基础设置结算ID不能为空");
            return BizResUtil.fail(BizResCode.PARAMERROR);
        }
        String delSettle = settleEntityService.deleteSettleSetting(settleSettingReq);
        httpResponse = BizResUtil.succ(delSettle);
        return httpResponse;
    }

    /**
     * 修改结算单基础信息
     * @param settleSetting
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update")
    public HttpResponse<String> updateSettleSetting(@RequestBody SettleSetting settleSetting){
        HttpResponse<String> httpResponse = null;
        LOGGER.info("修改结算单设置",JsonUtil.toJson(settleSetting));
        if (StringUtils.isEmpty(settleSetting.getEntityId()) || StringUtils.isEmpty(settleSetting.getBrandId())) {
            LOGGER.error("修改结算基础设置品牌和结算ID不能为空");
            return BizResUtil.fail(BizResCode.PARAMERROR);
        }
        if (!success.equals(settleParameterVerify(settleSetting).getCode())){
            return settleParameterVerify(settleSetting);
        }
        String updateSettle = settleEntityService.updateSettleSetting(settleSetting);
        httpResponse = BizResUtil.succ(updateSettle);
        return httpResponse;

    }

    /**
     * 结算信息详情
     * 非独立佣金结算时获取品牌佣金结算设置信息
     * @param settleSetting
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/select-settle-setting")
    public HttpResponse selectSettleSetting(@RequestBody SettleSetting settleSetting){
        HttpResponse httpResponse;
        LOGGER.info("获取结算信息详情",JsonUtil.toJson(settleSetting));
        if (StringUtils.isEmpty(settleSetting.getBrandId())) {
            LOGGER.error("结算基础设置品牌和结算ID不能为空");
            return BizResUtil.fail(BizResCode.PARAMERROR);
        }
        SettleEntity settleEntity = settleEntityService.selectSettleSetting(settleSetting);
        httpResponse = BizResUtil.succ(settleEntity);
        return httpResponse;
    }

    /**
     * 佣金结算参数校验
     * @param settleSetting
     * @return
     */
    private HttpResponse settleParameterVerify(SettleSetting settleSetting){
        if (CommissionConsts.COMMISSION_SETTLEMENT_SWITCH_YES.equals(settleSetting.getCommissionSettlementSwitch())){
            if (null == settleSetting.getTurnoverPoints() || null == settleSetting.getOnlineCommission() || null == settleSetting.getCommissionSettlementCircle()){
                LOGGER.error("流水提点，是否线上抽佣，佣金结算周期不能为空");
                return BizResUtil.fail(BizResCode.PARAMERROR);
            }
        }
        return HttpResponse.success();
    }
}
