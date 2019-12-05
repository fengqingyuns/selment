package com.hanyun.platform.settle.quartz;

import com.hanyun.ground.util.date.DateCalcUtil;
import com.hanyun.ground.util.date.DateFormatUtil;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.platform.settle.service.CommissionBillService;
import com.hanyun.platform.settle.vo.settlebill.CommissionBillGenResultVo;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 佣金结算单生成 (日、月、季度、年)
 * @author wangjie@hanyun.com
 * @Date 2018/3/2 21:33
 */
@JobHander(value="commissionBillGenerateJobHandler")
@Component
public class CommissionBillGenerateJobHandler extends IJobHandler {

    @Resource
    private CommissionBillService commissionBillService;

    @Override
    public ReturnT<String> execute(String... params) throws Exception {
        Date date = null;//时间
        String brandId = null;//品牌
        CommissionBillGenResultVo result;
        if(params != null){
            if(params.length >= 1){
                date = DateFormatUtil.parseDateNoSep(params[0]);
            }

            if(params.length >= 2){
                if(StringUtils.isNotBlank(params[1])){
                    brandId = params[1].trim();
                }
            }
        }

        if(date == null && brandId == null){//自动执行
            date = DateCalcUtil.getPreDay(new Date());
            result = commissionBillService.genCommissionBill(date, brandId);
        }else{//指定日期

            if (commissionBillService.judeCommissionSettleId(date, brandId)>0){
                result = commissionBillService.designationCommissionInfo(date, brandId);
            }else{
                result = commissionBillService.genCommissionBill(date, brandId);
            }
        }

        XxlJobLogger.log(JsonUtil.toJson(result));

        if(! result.isSuccess()){
            return ReturnT.FAIL;
        }

        return ReturnT.SUCCESS;
    }
}
