/**
 * 
 */
package com.hanyun.platform.settle.quartz;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.hanyun.ground.util.date.DateCalcUtil;
import com.hanyun.ground.util.date.DateFormatUtil;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.platform.settle.service.SettleBillGenService;
import com.hanyun.platform.settle.vo.settlebill.SettleBillGenResultVo;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * 结算单预生成
 * @author liyinglong@hanyun.com
 * @date 2017年11月2日 下午2:38:30
 */
@JobHander(value="settleBillPreGenerateJobHandler")
@Component
public class SettleBillPreGenerateJobHandler extends IJobHandler {
    @Resource
    private SettleBillGenService settleBillGenService;

    /**
     * 调度中心可传参数：
     * 1、[交易日期,格式 20171024 :非必填，不填则默认取昨日]
     * 2、[品牌编号:非必填，不填则默认取全部]
     */
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
        
        SettleBillGenResultVo result = settleBillGenService.preGenSettleBill(date, brandId);
        
        XxlJobLogger.log(JsonUtil.toJson(result));
        
        if(! result.isSuccess()){
            return ReturnT.FAIL;
        }
        
        return ReturnT.SUCCESS;
    }

}
