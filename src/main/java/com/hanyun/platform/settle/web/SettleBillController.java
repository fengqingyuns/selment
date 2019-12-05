package com.hanyun.platform.settle.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.consts.BizResCode;
import com.hanyun.platform.settle.domain.SettleBill;
import com.hanyun.platform.settle.domain.SettleBillPaychn;
import com.hanyun.platform.settle.service.SettleBillService;
import com.hanyun.platform.settle.util.BizResUtil;
import com.hanyun.platform.settle.util.ExcelUtil;
import com.hanyun.platform.settle.vo.base.PageResData;
import com.hanyun.platform.settle.vo.settlebill.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:wangximin
 * @date:2017/4/6
 */
@Controller
@RequestMapping(value = "/settle-bill")
public class SettleBillController {
    private static Logger LOGGER = LoggerFactory.getLogger(SettleBillController.class);

    @Resource
    private SettleBillService settleBillService;

   
    /**
     * @Author : wangximin
     * @Date : 2017/4/6 18:38
     * @Describe : 商户后台查询结算单列表
     */
    @RequestMapping(value = "/consumer-list", method = { RequestMethod.POST })
    @ResponseBody
    public HttpResponse<PageResData> getConsumerSettleBillList(@RequestBody SettleBillListReq settleBillListReq){
        HttpResponse<PageResData> httpResponse = null;
        if(StringUtils.isBlank(settleBillListReq.getBrandId())){
            LOGGER.error("商户后台查询结算单列表必须有品牌Id");
            return BizResUtil.fail(BizResCode.PARAMERROR);
        }
        try {
            PageResData pageResData = settleBillService.getSettleBillList(settleBillListReq);
            httpResponse = BizResUtil.succ(pageResData);
        } catch (Exception e) {
            LOGGER.error("查询结算单列表异常",e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);
        }
        return httpResponse;
    }

    /**
     * @Author : wangximin
     * @Date : 2017年4月7日15:17:39
     * @Describe : 汉云后台查询结算单列表
     */
    @RequestMapping(value = "/hanyun-list", method = { RequestMethod.POST })
    @ResponseBody
    public HttpResponse<PageResData> getSettleBillList(@RequestBody SettleBillListReq settleBillListReq){
        HttpResponse<PageResData> httpResponse = null;
        try {
            PageResData pageResData = settleBillService.getSettleBillList(settleBillListReq);
            httpResponse = BizResUtil.succ(pageResData);
        } catch (Exception e) {
            LOGGER.error("汉云后台查询结算单列表",e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);
        }
        return httpResponse;
    }

    /**
     * @Author : wangximin
     * @Date : 2017/4/7 14:32
     * @Describe : 结算明细中已解决对账差异列表
     */
    @RequestMapping(value = "/diff-detail-list", method = { RequestMethod.POST })
    @ResponseBody
    public HttpResponse<PageResData> getSettleDiffDetailList(@RequestBody SettleBillDetailReq settleBillDetailReq) {
        HttpResponse<PageResData> httpResponse = null;

        try {
            PageResData pageResData = settleBillService.getSettleDiffDetailList(settleBillDetailReq);
            httpResponse = BizResUtil.succ(pageResData);
        } catch (Exception e) {
            LOGGER.error("结算明细中对账差异列表",e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);
        }
        return httpResponse;
    }

    /**
     * @Author : wangximin
     * @Date : 2017/4/7 14:32
     * @Describe : 结算明细中新增对账差异列表
     */
    @RequestMapping(value = "/add-diff-detail-list", method = { RequestMethod.POST })
    @ResponseBody
    public HttpResponse<PageResData> getAddSettleDiffDetailList(@RequestBody SettleBillDetailReq settleBillDetailReq) {
        HttpResponse<PageResData> httpResponse = null;

        try {
            PageResData pageResData = settleBillService.getAddSettleDiffDetailList(settleBillDetailReq);
            httpResponse = BizResUtil.succ(pageResData);
        } catch (Exception e) {
            LOGGER.error("结算明细中对账差异列表",e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);
        }
        return httpResponse;
    }

    /**
     * @Author : wangximin
     * @Date : 2017/4/7 14:29
     * @Describe : 获取单个结算单的详情
     */
    @RequestMapping(value = "/single-settle-bill", method = {RequestMethod.POST })
    @ResponseBody
    public HttpResponse<SettleBill> getSingleSettleBill(@RequestBody SettleBill param) {
        HttpResponse<SettleBill> httpResponse = null;
        try {
            SettleBill settleBill = settleBillService.getSingleSettleBill(param);
            httpResponse = BizResUtil.succ(settleBill);
        } catch (Exception e) {
            LOGGER.error("system error!", e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);
        }
        return httpResponse;
    }

    /**
     * @Author : wangximin
     * @Date : 2017/4/7 14:36
     * @Describe : 结算单常规明细
     */
    @RequestMapping(value = "/settle-detail-list", method = { RequestMethod.POST })
    @ResponseBody
    public HttpResponse<PageResData> getSettleDetailList(@RequestBody SettleBillDetailReq settleBillDetailReq) {
        HttpResponse<PageResData> httpResponse = null;
        try {
            PageResData pageResData = settleBillService.getSettleDetailList(settleBillDetailReq);
            httpResponse = BizResUtil.succ(pageResData);

        } catch (Exception e) {
            LOGGER.error("system error!", e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);
        }
        return httpResponse;
    }

    /**
     * @Author : wangximin
     * @Date : 2017/4/7 15:30
     * @Describe : 结算单详细信息(根据不同类型查询数据)
     */
    @RequestMapping(value = "/detail", method = { RequestMethod.POST })
    @ResponseBody
    public HttpResponse<SettleBillRes> getSettleBill(@RequestBody SettleBillReq settleBillReq){
        HttpResponse<SettleBillRes> httpResponse = null;
        try {
            SettleBillRes settleBillRes = settleBillService.getSettleBill(settleBillReq);
            httpResponse = BizResUtil.succ(settleBillRes);

        } catch (Exception e) {
            LOGGER.error("system error!", e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);
        }
        return httpResponse;
    }
    /**
     * @Author : wangximin
     * @Date : 2017/4/12 10:08
     * @Describe : 更改结算状态
     */
    @RequestMapping(value = "/update-settle", method = { RequestMethod.POST })
    @ResponseBody
    public HttpResponse updateSettleStatus(@RequestBody SettleBillReq settleBillReq){
        HttpResponse httpResponse = null;
        try {
            if(StringUtils.isBlank(settleBillReq.getBillId())||StringUtils.isBlank(settleBillReq.getBrandId())){
                return BizResUtil.fail(BizResCode.PARAMERROR);
            }
            httpResponse = settleBillService.updateSettleStatus(settleBillReq);
        } catch (Exception e) {
            LOGGER.error("system error!", e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);
        }
        return httpResponse;
    }

    /**
     *
     * @param settleBillPaychn
     * @return
     * @Describe : 获取支付通道明细
     */
    @RequestMapping(value = "/pay-chn-list", method = { RequestMethod.POST})
    @ResponseBody
    public HttpResponse getSettlePayChnList(@RequestBody SettleBillPaychn settleBillPaychn){
        HttpResponse httpResponse = null;

        if(StringUtils.isBlank(settleBillPaychn.getBillId())){
            LOGGER.error("商户后台查询结算单列表中支付方式必须有结算单号");
            return BizResUtil.fail(BizResCode.PARAMERROR);
        }
        try {
            List<SettleBillPaychn> settleBillPaychnList =  settleBillService.getSettlePayChnList(settleBillPaychn);
            httpResponse = BizResUtil.succ(settleBillPaychnList);
        } catch (Exception e) {
            LOGGER.error("system error!", e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);
        }
        return httpResponse;
    }

    /**
     * 获取结算单列表并导出
     * @param response
     * @param request
     * @param pettleBillListReq
     * @throws Exception
     */
    @RequestMapping(value = "/expSettleBillList")
    @ResponseBody
    public void expSettleBillList(HttpServletResponse response, HttpServletRequest request, SettleBillListReq pettleBillListReq) throws Exception {
        try {
            settleBillService.expSettleBillList(response, request, pettleBillListReq);
        } catch(Exception e){
            LOGGER.error("结算单导出异常",e);
        }
    }
    /**
     * 结算单导出
     * @param response
     * @param request
     * @param settleBillListReq
     * @throws Exception
     */
    @GetMapping(value = "/expSettleBillListAll")
    @ResponseBody
    public void expSettleBillAll(HttpServletResponse response, HttpServletRequest request,SettleBillListReq settleBillListReq) throws Exception{
    	
    	try {
            settleBillService.getSettleDataLists(response, request,settleBillListReq);
        } catch(Exception e){
            LOGGER.error("结算导出异常",e);
        }
	}
    
    /**
     * 查询结算单和结算明细汇总
     * @param settleBillListReq
     * @return
     */
    @RequestMapping(value = "/getSummarySettleBill",method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse getSummarySettleBill(@RequestBody SettleBillListReq settleBillListReq){
        HttpResponse httpResponse = null;

        if(StringUtils.isBlank(settleBillListReq.getBrandId())){
            LOGGER.error("至少有品牌名称");
            return BizResUtil.fail(BizResCode.PARAMERROR);
        }
        try {
            SettleBillExt settleBillExt  =  settleBillService.getSummarySettleBill(settleBillListReq);
            httpResponse = BizResUtil.succ(settleBillExt);
        } catch (Exception e) {
            LOGGER.error("system error!", e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);
        }
        return httpResponse;
    }


    /**
     * @Author : wangximin
     * @Date : 2017/4/7 14:36
     * @Describe : 结算单常规明细
     */
    @RequestMapping(value = "/settle-refund-list", method = { RequestMethod.POST })
    @ResponseBody
    public HttpResponse getSettleRefundDetailList(@RequestBody SettleBillDetailReq settleBillDetailReq) {
        HttpResponse httpResponse = null;
        try {
            PageResData pageResData = settleBillService.getSettleRefundDetailList(settleBillDetailReq);
            httpResponse = BizResUtil.succ(pageResData);

        } catch (Exception e) {
            LOGGER.error("system error!", e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);
        }
        return httpResponse;
    }

    /**
     * 导出运营结算信息Excel
     * @param pettleBillListReq
     * @return
     */
    @GetMapping(value = "/getOperationsSettleData")
    @ResponseBody
    public void getOperationsSettleData(HttpServletResponse response, HttpServletRequest request, SettleBillListReq pettleBillListReq) throws Exception {
        try {
            settleBillService.getOperationsSettleData(response, request,pettleBillListReq);
        } catch(Exception e){
            LOGGER.error("运营结算导出异常",e);
        }

    }
    
   
}
