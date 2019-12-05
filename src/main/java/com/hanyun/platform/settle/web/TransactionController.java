package com.hanyun.platform.settle.web;


import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.consts.BizResCode;

import com.hanyun.platform.settle.service.PayTransactionService;
import com.hanyun.platform.settle.util.BizResUtil;

import com.hanyun.platform.settle.vo.PayTransactionReq;
import com.hanyun.platform.settle.vo.base.PageResData;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:wangjie
 * @date:2017/5/4
 */
@Controller
@RequestMapping(value = "/transaction")
public class TransactionController {
    private static Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @Resource
    private PayTransactionService payTransactionService;

    @RequestMapping(value = "/transaction-list", method = { RequestMethod.GET })
    @ResponseBody
    public HttpResponse<PageResData> selectPayTransactionList(PayTransactionReq payTransactionReq){
        HttpResponse<PageResData> httpResponse = null;
        if(StringUtils.isBlank(payTransactionReq.getBrandId())){
            LOGGER.error("汉云后台管理系统必须品牌ID");
            return BizResUtil.fail(BizResCode.PARAMERROR);
        }
        try {
            PageResData pageResData = payTransactionService.selectPayTransactionList(payTransactionReq);
            httpResponse = BizResUtil.succ(pageResData);
        } catch (Exception e) {
            LOGGER.error("查询交易列表异常",e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);
        }
        return httpResponse;
    }

    @RequestMapping(value = "/expPayTransactionList")
    @ResponseBody
    public void expPayTransactionList(HttpServletResponse response, HttpServletRequest request, PayTransactionReq payTransactionReq) throws Exception {
        try {
            payTransactionService.expPayTransactionList(response, request, payTransactionReq);
        } catch(Exception e){
            LOGGER.error("交易明细导出异常",e);
        }
    }

}
