package com.hanyun.platform.settle.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.consts.BizResCode;
import com.hanyun.platform.settle.service.SettleFlowService;
import com.hanyun.platform.settle.util.BizResUtil;
import com.hanyun.platform.settle.vo.PendingAuditReq;
import com.hanyun.platform.settle.vo.ProcessInstanceReq;
import com.hanyun.platform.settle.vo.base.PageResData;
import com.hanyun.platform.settle.vo.ExecuteAuditReq;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jack on 2017/5/22.
 */
@Controller
@RequestMapping( value = "/settle-flow")
public class SettleFlowController {
    private static Logger LOGGER = LoggerFactory.getLogger(SettleFlowController.class);

    @Resource
    private SettleFlowService settleFlowService;

    /**
     * 启动流程
     * @param processInstanceReq
     * @return
     */
    @RequestMapping(value = "/startup", method = { RequestMethod.POST })
    @ResponseBody
    public HttpResponse startFlow(@RequestBody ProcessInstanceReq processInstanceReq){
         HttpResponse httpResponse = null;
         try {
             httpResponse =   settleFlowService.startFlow(processInstanceReq);
         } catch (Exception e){
             LOGGER.error("启动流程失败",e);
             httpResponse = BizResUtil.fail(BizResCode.STARTPROCESSEXCEPTION);
         }
    return httpResponse;
    }

    /**
     *查询待审核列表
     * @param pendingAuditReq
     * @return
     */
    @RequestMapping(value = "/getPendingAudiList" , method = { RequestMethod.POST })
    @ResponseBody
    public HttpResponse<PageResData> getPendingAuditList(@RequestBody PendingAuditReq pendingAuditReq){
        HttpResponse<PageResData> httpResponse = null;
        try {
            PageResData pendingAudiList = settleFlowService.getPendingAuditList(pendingAuditReq);
            httpResponse = BizResUtil.succ(pendingAudiList);
        } catch (Exception e){
            LOGGER.error("查询待审核列表失败",e);
            httpResponse = BizResUtil.fail(BizResCode.SELECTPROCESSAUDITEXCEPTION);
        }
        return httpResponse;
    }

    /**
     *  执行审核操作
     * @param executeAuditReq
     * @return
     */
    @RequestMapping(value = "/executeAudit", method = { RequestMethod.POST })
    @ResponseBody
    public HttpResponse executeAudit(@RequestBody ExecuteAuditReq executeAuditReq){
        HttpResponse<PageResData> httpResponse;

        try {
            httpResponse = settleFlowService.executeAudit(executeAuditReq);
        } catch (Exception e) {
            LOGGER.error("执行审核操作失败",e);
            httpResponse = BizResUtil.fail(BizResCode.EXECUTETASKFAIL);
        }
        return httpResponse;
    }

     /**
     * 查询审批任务列表
     * @param processInstanceId
     * @return
     */
    @RequestMapping(value = "/auditTaskList", method = {RequestMethod.GET})
    @ResponseBody
    public HttpResponse<PageResData> auditTaskList(String businessKey, String processInstanceId){
        HttpResponse<PageResData> httpResponse = null;
        if (StringUtils.isEmpty(processInstanceId) && StringUtils.isEmpty(businessKey) ){
            LOGGER.error("请求参数不能为空");
            return  httpResponse = BizResUtil.fail(BizResCode.PARAMERROR);
        }

        try {
            PageResData  auditTaskList = settleFlowService.auditTaskList(businessKey,processInstanceId);
            httpResponse = BizResUtil.succ(auditTaskList);
        } catch (Exception e) {
            LOGGER.error("查询审批任务列表异常",e);
            httpResponse = BizResUtil.fail(BizResCode.QUERYAUDITTASKIDEXCEPTION);
        }
        return httpResponse;
    }

    /**
     * 审批流程图
     * @param businessKey
     * @return
     */
    @RequestMapping(value = "/readPicture", method = {RequestMethod.GET})
    @ResponseBody
    public HttpResponse readPicture(String businessKey){
        HttpResponse httpResponse = null;

        if (StringUtils.isEmpty(businessKey) ){
            LOGGER.error("请求参数不能为空");
            return  httpResponse = BizResUtil.fail(BizResCode.PARAMERROR);

        }
        return httpResponse = settleFlowService.readPicture(businessKey);
    }


    /**
     *批量审核
     * @param executeAuditReqList
     * @return
     */
    @RequestMapping(value = "/batchExecuteAudit", method = {RequestMethod.POST})
    @ResponseBody
    public HttpResponse batchExecuteAudit(@RequestBody List<ExecuteAuditReq> executeAuditReqList){
        HttpResponse<PageResData> httpResponse = null;
        if(executeAuditReqList.size()<=0 || executeAuditReqList == null){
            LOGGER.error("批量审批请求参数不能为空");
            return httpResponse = BizResUtil.fail(BizResCode.BATCHAUDITEXCEPTION);
        }
        for (ExecuteAuditReq executeAuditReq:executeAuditReqList){
            try {
                httpResponse = settleFlowService.executeAudit(executeAuditReq);
            } catch (Exception e) {
                LOGGER.error("执行审核操作失败",e);
                httpResponse = BizResUtil.fail(BizResCode.EXECUTETASKFAIL);
            }
        }

        return httpResponse;
    }

}
