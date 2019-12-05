package com.hanyun.platform.settle.web;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanyun.ground.util.date.DateCalcUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.consts.BizResCode;
import com.hanyun.platform.settle.domain.StatementDiffDetail;
import com.hanyun.platform.settle.domain.TradeStatementDetail;
import com.hanyun.platform.settle.service.TradeStatementDetailService;
import com.hanyun.platform.settle.util.BizResUtil;
import com.hanyun.platform.settle.vo.TradeStatementDetailReq;
import com.hanyun.platform.settle.vo.StatementDiffReq;
import com.hanyun.platform.settle.vo.base.PageResData;

@Controller
@RequestMapping(value = "/statement")
public class TradeStatementController {

    private static Logger LOGGER = LoggerFactory.getLogger(TradeStatementController.class);
    @Resource
    private TradeStatementDetailService tradeStatementDetailService;

    /**
     * @Description: 当日对账
     */
    @RequestMapping(value = "/statementDetailListToday", method = { RequestMethod.POST })
    @ResponseBody
    public HttpResponse<PageResData> statementDetailListToday(@RequestBody TradeStatementDetailReq statementDetailReq) {
        HttpResponse<PageResData> httpResponse = null;
        try {
            Date now = new Date();
            Date finishBeginTime = DateCalcUtil.getDayBegin(now);
            Date finishEndTime = DateCalcUtil.getDayEnd(now);
            statementDetailReq.setFinishBeginTime(finishBeginTime);
            statementDetailReq.setFinishEndTime(finishEndTime);

            List<TradeStatementDetail> statementDetailList = tradeStatementDetailService
                    .statementDetailList(statementDetailReq);// 获取列表
            Integer statementCount = tradeStatementDetailService.statementCount(statementDetailReq);// 获取条数

            httpResponse = BizResUtil.succPageList(statementCount, statementDetailList);

        } catch (Exception e) {
            LOGGER.error("system error!", e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);

        }
        return httpResponse;
    }

    @RequestMapping(value = "/statementDiffList", method = { RequestMethod.POST })
    @ResponseBody
    public HttpResponse<PageResData> statementDiffList(@RequestBody StatementDiffReq statementDiffReq) {
        HttpResponse<PageResData> httpResponse = null;
        try {
            Date reportStartTime = null;
            Date reportEndTime = null;

            if (statementDiffReq.getReportStartTime() != null) {
                reportStartTime = DateCalcUtil.getDayBegin(statementDiffReq.getReportStartTime());// 对账开始时间
            }

            if (statementDiffReq.getReportEndTime() != null) {
                reportEndTime = DateCalcUtil.getDayEnd(statementDiffReq.getReportEndTime());// 对账结束时间
            }
            statementDiffReq.setReportStartTime(reportStartTime);
            statementDiffReq.setReportEndTime(reportEndTime);
            List<StatementDiffDetail> statementDifflist = tradeStatementDetailService
                    .statementDiffList(statementDiffReq);
            Integer count = tradeStatementDetailService.statementDiffCount(statementDiffReq);

            httpResponse = BizResUtil.succPageList(count, statementDifflist);

        } catch (Exception e) {
            LOGGER.error("system error!", e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);

        }
        return httpResponse;
    }

}
