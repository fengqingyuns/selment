package com.hanyun.platform.settle.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.consts.BizResCode;
import com.hanyun.platform.settle.domain.StatementDiff;
import com.hanyun.platform.settle.domain.StatementDiffDetail;
import com.hanyun.platform.settle.service.StatementDiffService;
import com.hanyun.platform.settle.util.BizResUtil;
import com.hanyun.platform.settle.vo.StatementDiffReq;

@Controller
@RequestMapping(value = "/statementDiff")
public class StatementDiffController {
    private static Logger LOGGER = LoggerFactory.getLogger(StatementDiffController.class);
    @Resource
    private StatementDiffService statementDiffService;

    /**
     * 根据ID查询单个对账差异明细
     */
    @RequestMapping(value = "/statementDiffDetail", method = { RequestMethod.POST })
    @ResponseBody
    public HttpResponse<StatementDiffDetail> getStatementDiffDetail(@RequestBody StatementDiffReq statementDiffReq) {
        HttpResponse<StatementDiffDetail> httpResponse = null;
        try {
            StatementDiffDetail statementDiffDetail = statementDiffService
                    .getStatementDiffDetailByTransId(statementDiffReq);
            httpResponse = BizResUtil.succ(statementDiffDetail);

        } catch (Exception e) {
            LOGGER.error("获取单个信息失败!", e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);
        }

        return httpResponse;
    }

    /**
     * 新增对账差异提报
     */
    @RequestMapping(value = "/saveStatementDiffSubmit", method = { RequestMethod.POST })
    @ResponseBody
    public HttpResponse saveStatementDiffSubmit(@RequestBody StatementDiff statementDiff) {
        return statementDiffService.addStatementDiffSubmit(statementDiff);
    }

    /**
     * 修改对账差异提报
     */
    @RequestMapping(value = "/updateStatementDiffSubmit", method = { RequestMethod.POST })
    @ResponseBody
    public HttpResponse<String> updateStatementDiffSubmit(@RequestBody StatementDiff statementDiff) {
        System.out.println(statementDiff);
        HttpResponse<String> httpResponse = null;
        try {
            statementDiffService.updateStatementDiffSubmit(statementDiff);
            httpResponse = BizResUtil.succ("submitOk");

        } catch (Exception e) {
            LOGGER.error("修改失败!", e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);
        }

        return httpResponse;
    }

}
