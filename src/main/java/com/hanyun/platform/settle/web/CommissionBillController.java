package com.hanyun.platform.settle.web;

import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.consts.BizResCode;
import com.hanyun.platform.settle.domain.CommissionDeductionDetail;
import com.hanyun.platform.settle.service.CommissionBillService;
import com.hanyun.platform.settle.util.BizResUtil;
import com.hanyun.platform.settle.vo.CommissionStatisticsVo;
import com.hanyun.platform.settle.vo.base.PageResData;
import com.hanyun.platform.settle.vo.commissionbill.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 佣金结算相关的接口
 * @author wangjie@hanyun.com
 * @Date 2018/2/27 11:29
 */
@RestController
@RequestMapping("/commission-bill")
public class CommissionBillController {
    private static Logger LOGGER = LoggerFactory.getLogger(CommissionBillController.class);

    @Resource
    private CommissionBillService commissionBillService;

    /**
     * 查询佣金结算单
     * @param commissionBillReq
     * @return
     */
    @GetMapping("/query")
    public HttpResponse<PageResData> query(CommissionBillReq commissionBillReq){
        LOGGER.info("查询佣金结算单列表{}", JsonUtil.toJson(commissionBillReq));
        HttpResponse httpResponse = null;
        try{
            PageResData pageResData = commissionBillService.query(commissionBillReq);
            httpResponse = BizResUtil.succ(pageResData);

        }catch (Exception e){
            LOGGER.error("查询佣金结算单列表异常",e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);

        }
        return httpResponse;
    }

    /**
     *  打开批量转账页面
     * @param commissionBillIds
     * @return
     */
    @GetMapping("/open-transfer")
    public HttpResponse<OpenTransferRes> openTransfer(String... commissionBillIds){
        LOGGER.info("查询佣金结算单列表{}", JsonUtil.toJson(commissionBillIds));
        if(null == commissionBillIds || commissionBillIds.length == 0){
            LOGGER.info("请求参数不能为空");
            return  HttpResponse.failure(BizResCode.PARANOTNULL);
        }
        return commissionBillService.openTransfer(commissionBillIds);
    }

    /**
     *  保存批量转账
     * @param req
     * @return
     */
    @PostMapping("/save-transfer")
    public HttpResponse saveTransfer(@RequestBody SaveTransferReq req){
        LOGGER.info("保存批量转账请求参数{}",JsonUtil.toJson(req));
        if( null == req.getCommissionBillIds() || null == req.getAmount()){
            LOGGER.error("请求参数错误");
            return HttpResponse.failure(BizResCode.PARAMERROR);
        }
        return commissionBillService.saveTransfer(req);
    }


    /**
     * 佣金结算单详情 -基本信息-结算信息
     * @param commissionBillId
     * @return
     */
    @GetMapping("/detail/{commissionBillId}")
    public HttpResponse<CommissionBillDetailRes> detail(@PathVariable("commissionBillId") String commissionBillId){
        LOGGER.info("请求参数：{}",commissionBillId);
        return commissionBillService.detail(commissionBillId);
    }

    /**
     * 佣金结算详情 -佣金明细
     * @param commissionDetailReq
     * @return
     */
    @GetMapping("/detail/commission-detail")
    public HttpResponse<PageResData> getCommissionDetail(CommissionDetailReq commissionDetailReq){
        LOGGER.info("请求参数 {}",JsonUtil.toJson(commissionDetailReq));
        HttpResponse httpResponse = null;
        try{
            PageResData pageResData = commissionBillService.getCommissionDetail(commissionDetailReq);
            httpResponse = BizResUtil.succ(pageResData);

        }catch (Exception e){
            LOGGER.error("查询佣金详情异常",e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);

        }
        return httpResponse;


    }

    /**
     *   Excel 导出佣金结算单记录
     * @param response
     * @param request
     * @param req
     */
    @RequestMapping(value = "/exp-commission-bill-record")
    @ResponseBody
    public void expCommissionBillList(HttpServletResponse response, HttpServletRequest request, CommissionBillReq req){
        try {
            commissionBillService.expCommissionBillList(response, request, req);
        } catch(Exception e){
            LOGGER.error("佣金结算单导出异常",e);
        }
    }

    /**
     * 佣金统计查询
     * @param queryCommissionPara
     * @return
     */
    @GetMapping("/commission-statistics")
    public HttpResponse<CommissionStatisticsVo> commissionStatistics(QueryCommissionPara queryCommissionPara){
        LOGGER.info("请求参数 {}",JsonUtil.toJson(queryCommissionPara));
        HttpResponse httpResponse = null;
        try{
            CommissionStatisticsVo commissionStatisticsVo = commissionBillService.commissionStatistics(queryCommissionPara);
            httpResponse = BizResUtil.succ(commissionStatisticsVo);
        }catch (Exception e){
            LOGGER.error("查询佣金结统计列表异常",e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);
        }
        return httpResponse;
    }


    /**
     *   Excel 导出佣金统计
     * @param response
     * @param request
     * @param req
     */
    @RequestMapping(value = "/exp-commission-statistics-record")
    @ResponseBody
    public void expCommissionStatisticsList(HttpServletResponse response, HttpServletRequest request, QueryCommissionPara req){
        LOGGER.info("请求参数 {}",JsonUtil.toJson(req));
        try {
            commissionBillService.expCommissionStatisticsList(response, request, req);
        } catch(Exception e){
            LOGGER.error("佣金统计导出异常",e);
        }
    }

    /**
     * 获取佣金扣除明细
     * @param commissionDeductionDetail
     * @return
     */
    @GetMapping("/get-commission-deduction-detail")
    public HttpResponse getCommissionDeductionDetail(CommissionDeductionDetail commissionDeductionDetail){
        HttpResponse httpResponse;
        LOGGER.info("请求参数 {}",JsonUtil.toJson(commissionDeductionDetail));
        try {
            if( null == commissionDeductionDetail.getBillId()){
                LOGGER.error("请求参数错误");
                return HttpResponse.failure(BizResCode.PARAMERROR);
            }
            List<CommissionDeductionDetail> commissionDeductionDetailList = commissionBillService.getCommissionDeductionDetail(commissionDeductionDetail);
            httpResponse = BizResUtil.succ(commissionDeductionDetailList);
        }catch (Exception e){
            LOGGER.error("查询佣金扣除明细异常",e);
            httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);
        }
        return httpResponse;
    }

}
