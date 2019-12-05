package com.hanyun.platform.settle.quartz;

import com.hanyun.ground.util.date.DateCalcUtil;
import com.hanyun.ground.util.date.DateFormatUtil;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.platform.settle.service.CommissionBillDeductionService;
import com.hanyun.platform.settle.vo.settlebill.CommissionBillGenResultVo;
import com.hanyun.platform.settle.vo.settlebill.CommissionDeductionResultVo;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by admin on 2018/3/30.
 */
@JobHander(value="commissionDeductionJobHandler")
@Component
public class CommissionDeductionJobHandler extends IJobHandler {

    @Resource
    private CommissionBillDeductionService commissionBillDeductionService;


    @Override
    public ReturnT<String> execute(String... params) throws Exception {
        Date date = null;
        String brandId = null;
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
        if(date == null){
            date = DateCalcUtil.getPreDay(new Date());
        }

        CommissionDeductionResultVo result = commissionBillDeductionService.commissionDeducted(date, brandId);

        XxlJobLogger.log(JsonUtil.toJson(result));

        if(! result.isSuccess()){
            return ReturnT.FAIL;
        }

        return ReturnT.SUCCESS;
    }
}
