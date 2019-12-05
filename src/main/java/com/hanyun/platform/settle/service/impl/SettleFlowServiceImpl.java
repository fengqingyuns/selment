package com.hanyun.platform.settle.service.impl;

/**
 * Created by jack on 2017/5/22.
 */

import com.hanyun.ground.util.date.DateFormatUtil;
import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.consts.BizResCode;
import com.hanyun.platform.settle.consts.SettlementConsts;
import com.hanyun.platform.settle.dao.ProcessInstanceDao;
import com.hanyun.platform.settle.dao.SettleBillDao;
import com.hanyun.platform.settle.domain.ProcessInstance;
import com.hanyun.platform.settle.domain.SettleBill;
import com.hanyun.platform.settle.domain.activiti.*;
import com.hanyun.platform.settle.service.SettleFlowService;
import com.hanyun.platform.settle.util.ActivitiUtil;
import com.hanyun.platform.settle.util.BizResUtil;
import com.hanyun.platform.settle.util.PropertiesUtil;
import com.hanyun.platform.settle.vo.*;
import com.hanyun.platform.settle.vo.base.PageResData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Service
public class SettleFlowServiceImpl implements SettleFlowService {
    private static Logger LOGGER = LoggerFactory.getLogger(SettleFlowServiceImpl.class);

    @Resource
    private ProcessInstanceDao processInstanceDao;
    @Resource
    private SettleBillDao settleBillDao;


    /**
     * 启动流程
     * @param processInstanceReq
     * @return
     */
    @Override
    public HttpResponse startFlow(ProcessInstanceReq processInstanceReq) {
        HttpResponse httpResponse = null;
        ProcessInstance processInstance = new ProcessInstance();
        PropertiesUtil propertiesUtil = new PropertiesUtil();
        Properties properties = null;
        try {
            properties = propertiesUtil.getProperties("settlement-api.properties");
        } catch (Exception e) {
            LOGGER.error("找不到配置文件!", e);
            return HttpResponse.failure(BizResCode.FILENOTFOUNDEXCEPTION);
        }
        String processDefinitionId = properties.getProperty("processDefinitionId");//获取配置文件中的processDefinitionId
        String businessKey = processInstanceReq.getBusinessKey();
        ActivitiUtil.getInstance();
        //流程实例化
        ActivitiProcessInstance activitiProcessInstance = ActivitiUtil.startupProcess(processDefinitionId, businessKey);
        LOGGER.info("获取流程实例 success: {}", JsonUtil.toJson(activitiProcessInstance));

        if(activitiProcessInstance == null){
            return HttpResponse.failure(BizResCode.STARTPROCESSFAIL);
        }
        processInstance.setCreateTime(new Date());
        processInstance.setUpdateTime(new Date());
        processInstance.setBrandId(processInstanceReq.getBrandId());
        processInstance.setStoreId(processInstanceReq.getStoreId());
        processInstance.setBusinessKey(businessKey);
        processInstance.setBusinessType(SettlementConsts.BUSINESS_KEY_SETTLE);
        processInstance.setProcessDefId(processDefinitionId);
        processInstance.setProcessInstId(activitiProcessInstance.getId());
        processInstance.setProcessStatus(SettlementConsts.PROCESS_STATUS_IN_AUDIT);
        processInstance.setStarterId(processInstanceReq.getUserId());
        processInstance.setStarterName(processInstanceReq.getUserName());
        int processCount = processInstanceDao.insertSelective(processInstance);
        LOGGER.info("新增流程实例化数据{}",processCount);
        if (processCount < 0) {
            return HttpResponse.failure(BizResCode.INSERTPROCESSINSTANCEFAIL);
        }
        //获取流程当前任务
        QueryCurrentTask(processInstanceReq.getBusinessKey());
        //获取当前执行角色信息
        QueryCurrentRole(processInstanceReq.getBusinessKey());
        //启动流程后，对结算单进行更新审核流程

        SettleBill settleBill = new SettleBill();
        settleBill.setAuditStatus(SettlementConsts.PROCESS_STATUS_IN_AUDIT);
        settleBill.setBillId(processInstanceReq.getBusinessKey());
        int updateSettleBillCount = settleBillDao.updateSettleBill(settleBill);
        if(updateSettleBillCount <0){
            LOGGER.info("对结算单进行更新审核流程失败！");
            return HttpResponse.failure(BizResCode.UPDATEAUDITSTATUSEXCEPTION);
        }
        return httpResponse = BizResUtil.succ("OK");
    }

    @Override
    /**
     * 获取待审核列表
     */
    public PageResData getPendingAuditList(PendingAuditReq pendingAuditReq) {
        PageResData pageResData = new PageResData();
        ProcessInstanceDetailReq processInstanceDetailReq = new ProcessInstanceDetailReq();
        processInstanceDetailReq.setBrandId(pendingAuditReq.getBrandId());
        processInstanceDetailReq.setStoreId(pendingAuditReq.getStoreId());
        processInstanceDetailReq.setCurAuditorType(SettlementConsts.PROCESS_AUDIT_ROLE);//审核类型为 角色
        processInstanceDetailReq.setCurAuditorId(pendingAuditReq.getRoleId());//角色id
        processInstanceDetailReq.setProcessStatus(SettlementConsts.PROCESS_STATUS_IN_AUDIT);//审核中
        processInstanceDetailReq.setPageSize(pendingAuditReq.getPageSize());
        processInstanceDetailReq.setPageNo(pendingAuditReq.getPageNo());
        processInstanceDetailReq.setBeginIndex(pendingAuditReq.getBeginIndex());
        List<ProcessInstanceDetailReq> processInstanceList = processInstanceDao.selectProcessInstance(processInstanceDetailReq);
        int count  = processInstanceDao.selectProcessInstanceCount(processInstanceDetailReq);
        pageResData.setDataList(processInstanceList);
        pageResData.setTotalCount(count);
        return pageResData;
    }

    /**
     * 执行审核操作
     * @param executeAuditReq
     * @return
     */
    @Override
    public HttpResponse executeAudit(ExecuteAuditReq executeAuditReq) {
        HttpResponse httpResponse = null;
        ActivitiUtil.getInstance();
        try {
            //设置变量
            ActivitiUtil.setVariables(executeAuditReq);
            //声明审核人
            ActivitiUtil.setAuditor(executeAuditReq);
            //执行任务
            ActivitiUtil.executeTask(executeAuditReq);
        } catch (Exception e) {
            LOGGER.error("执行任务异常{}",e);
            httpResponse = BizResUtil.fail(BizResCode.EXECUTETASKEXCEPTION);
        }
        // 查询下一个任务
        QueryCurrentTask(executeAuditReq.getBusinessKey());

        //判断如果是最后一个节点，则不需要去查询获取下一个执行的角色信息
        ProcessInstance  processInstance = processInstanceDao.selectByProcessInstance(executeAuditReq.getBusinessKey());
        if (processInstance == null) {
            HttpResponse.failure(BizResCode.GETPROCESSFAIL);//获取流程失败
        }

        if(SettlementConsts.PROCESS_STATUS_IN_AUDIT.equals(processInstance.getProcessStatus())){
            //获取当前执行角色信息
            QueryCurrentRole(executeAuditReq.getBusinessKey());
        }
        return httpResponse = BizResUtil.succ("OK");
    }

    /**
     *  通过结算单号或者流程实例号查询审批任务列表
     * @param businessKey
     * @param processInstanceId
     * @return
     */
    @Override
    public PageResData auditTaskList(String businessKey, String processInstanceId) {
        //获取审批流程实例
        if (processInstanceId == null) {
            ProcessInstance  processInstance = processInstanceDao.selectByProcessInstance(businessKey);
            processInstanceId = processInstance.getProcessInstId();
        }

        PageResData pageResData = new PageResData();
        List<AuditBase> auditBasesList = new ArrayList<AuditBase>();
        ActivitiUtil.getInstance();
        //任务历史节点
        HistoricActivityInstances historicActivityInstances = ActivitiUtil.historicActivityInstances(processInstanceId);
        //历史任务变量
        HistoricVariable historicVariable =  ActivitiUtil.historicVariableInstances(processInstanceId);

        if (historicActivityInstances != null) {
            List<ActivitHistoric> dataList = historicActivityInstances.getData();
            for (ActivitHistoric activitHistoric:dataList) {
                if (activitHistoric.getTaskId() != null && activitHistoric.getAssignee() !=null ) {
                    AuditBase auditBase = new AuditBase();
                    auditBase.setTaskId(activitHistoric.getTaskId());
                    auditBase.setUserId(activitHistoric.getAssignee());
                    auditBase.setTaskName(activitHistoric.getActivityName());
                    auditBase.setAuditTime(activitHistoric.getEndTime());
                    auditBasesList.add(auditBase);
                }
            }
        }

        if (historicVariable != null) {
            List<VariableInstances>  varDataList =  historicVariable.getData();
            for (AuditBase auditBase:auditBasesList) {
                for (VariableInstances variableInstances:varDataList) {
                    if(variableInstances.getTaskId() != null){
                        if (auditBase.getTaskId().equals(variableInstances.getTaskId())){
                            TaskVariables taskVariables = variableInstances.getVariable();//获取变量对象
                                if("result".equals(taskVariables.getName())){//获取是否同意
                                    auditBase.setAuditResult(taskVariables.getValue());
                                }

                                if ("remark".equals(taskVariables.getName())){//获取审批意见
                                    auditBase.setAuditContent(taskVariables.getValue());
                                }
                        }
                    }
                }
            }
        }
        List<AuditBase> auditList = TranslationUserName(auditBasesList);
        pageResData.setDataList(auditList);
        return pageResData;
    }

    /**
     * 获取图片接口
     * @param businessKey
     * @return
     */
    @Override
    public HttpResponse readPicture(String businessKey) {
        HttpResponse httpResponse =null;
        String str = "";
        //获取审批流程实例
        ProcessInstance  processInstance = processInstanceDao.selectByProcessInstance(businessKey);
       // processInstanceId = processInstance.getProcessInstId();//获取流程实例id
        if (processInstance == null) {
            HttpResponse.failure(BizResCode.GETPROCESSFAIL);//获取流程失败
        }
        if(!SettlementConsts.PROCESS_STATUS_AUDIT_COMPLETED.equals(processInstance.getProcessStatus())){
            try {
                str =  ActivitiUtil.flowChart(processInstance.getProcessInstId());
                httpResponse = BizResUtil.succ("OK");
                return httpResponse.setData(str);
            } catch (IOException e) {
                LOGGER.error("获取流程图失败", e);
            }
        }
        return httpResponse = BizResUtil.succ("noPicture");
    }

    /**
     * 将用户id转为name
     * @param auditBasesList
     * @return
     */
    public List<AuditBase> TranslationUserName(List<AuditBase> auditBasesList){
        PropertiesUtil propertiesUtil = new PropertiesUtil();
        Properties properties = null;
        try {
            properties = propertiesUtil.getProperties("settlement-api.properties");
        } catch (Exception e) {
            LOGGER.error("找不到配置文件!", e);
        }
        if (auditBasesList != null && auditBasesList.size()>0) {
            for (AuditBase auditBase:auditBasesList) {
                if (auditBase != null){
                    List<String>  userIdList = new ArrayList<String>();
                    userIdList.add(auditBase.getUserId());
                    Map<String,List<String>> userListMap = new HashMap<>();
                    userListMap.put("userIdList", userIdList);
                    String result = HttpClient.post(properties.getProperty("get_user_info_url")).json(JsonUtil.toJson(userListMap)).action().result();
                    LOGGER.info("通过id获取人员名称 hanyun/employee/names{}",result);
                    UserInfo userInfo = JsonUtil.fromJson(result, UserInfo.class);
                    Map  map = userInfo.getData();
                    String userName = (String)map.get(auditBase.getUserId());
                    auditBase.setUserId(userName);
                    try {
                        auditBase.setAuditTime(dealDateFormat(auditBase.getAuditTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return auditBasesList;
    }

    /**
     * 转换格式
     * @param oldDateStr
     * @return
     * @throws ParseException
     */
    public static String dealDateFormat(String oldDateStr) throws ParseException {
        if(oldDateStr == null){
            return "";
        }
        Date  date = DateFormatUtil.parse(oldDateStr, "yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        return DateFormatUtil.format(date, "yyyy-MM-dd HH:mm:ss");
    }


    /**
     *获取流程当前任务
     * @param businessKey
     */
    public HttpResponse QueryCurrentTask(String businessKey){
        HttpResponse httpResponse = null;

        ProcessInstance  processInstancetask = processInstanceDao.selectByProcessInstance(businessKey);
        //获取流程当前任务
        ActivitQueryTask activitQueryTask = ActivitiUtil.getProcessCurrentTasks(processInstancetask.getProcessInstId());
        if (activitQueryTask == null) {
            return HttpResponse.failure(BizResCode.GETPROCESSCURRENTTASKSFAIL);//获取流程当前任务失败
        }
        List taskList = activitQueryTask.getData();
        if (taskList.size() >0 && taskList != null ) {//查询到任务

            for (int i = 0; i < taskList.size() ; i++) {
                ActivitQueryTaskContent activitQueryTaskContent  = (ActivitQueryTaskContent)taskList.get(0);
                if (activitQueryTaskContent == null) {
                    return HttpResponse.failure(BizResCode.GETPROCESSCURRENTTASKSCONTENTFAIL);//获取当前任务内容失败
                }
                ProcessInstance updateTasks = new ProcessInstance();
                updateTasks.setBrandId(processInstancetask.getBrandId());
                updateTasks.setStoreId(processInstancetask.getStoreId());
                updateTasks.setUpdateTime(new Date());
                updateTasks.setBusinessKey(processInstancetask.getBusinessKey());
                updateTasks.setCurTaskId(activitQueryTaskContent.getId());
                updateTasks.setCurTaskDefKey(activitQueryTaskContent.getTaskDefinitionKey());
                updateTasks.setCurTaskDefName(activitQueryTaskContent.getName());
                int updateTaskCount   = processInstanceDao.updateByProcessInstance(updateTasks);
                LOGGER.info("更新流程实例化数据任务{}",updateTaskCount);
            }

        }else{//未查询到任务
            ProcessInstance updateTasks = new ProcessInstance();
            updateTasks.setBrandId(processInstancetask.getBrandId());
            updateTasks.setStoreId(processInstancetask.getStoreId());
            updateTasks.setProcessStatus(SettlementConsts.PROCESS_STATUS_AUDIT_COMPLETED);//设置为审核完成
            updateTasks.setUpdateTime(new Date());
            updateTasks.setBusinessKey(processInstancetask.getBusinessKey());
            int updateTaskCount   = processInstanceDao.updateByProcessInstance(updateTasks);
            LOGGER.info("更新流程实例化数据设置为审核完成{}",updateTaskCount);

            //更新结算单审核状态和结算状态
            SettleBill settleBill = new SettleBill();
            settleBill.setBillId(businessKey);
            settleBill.setSettleStatus(SettlementConsts.NEW_SETTLE_STATUS_SETTLED);
            settleBill.setAuditStatus(SettlementConsts.PROCESS_STATUS_AUDIT_COMPLETED);
            int updateSettleBillStatus = settleBillDao.updateSettleBill(settleBill);
            LOGGER.info("更新结算单状态{}",updateSettleBillStatus);
        }
        return httpResponse;
    }

    /**
     * 获取当前执行角色信息
     * @param businessKey
     */
    public HttpResponse QueryCurrentRole(String businessKey){
        HttpResponse httpResponse = null;

        ProcessInstance  processInstancetask = processInstanceDao.selectByProcessInstance(businessKey);
        if (processInstancetask == null) {
            return HttpResponse.failure(BizResCode.GETPROCESSFAIL);//获取流程实例失败
        }
        //获取流程当前执行角色
        List<ActivitiIdentity>  activitiIdentityList = ActivitiUtil.getProcessCurrentTasksRole(processInstancetask.getCurTaskId());
        for (int i = 0; i < activitiIdentityList.size(); i++) {
            ProcessInstance updateTasksRole = new ProcessInstance();
            ActivitiIdentity  activitiIdentity = (ActivitiIdentity)activitiIdentityList.get(i);
            updateTasksRole.setBrandId(processInstancetask.getBrandId());
            updateTasksRole.setStoreId(processInstancetask.getStoreId());
            updateTasksRole.setUpdateTime(new Date());
            updateTasksRole.setBusinessKey(processInstancetask.getBusinessKey());
            updateTasksRole.setCurAuditorType(SettlementConsts.PROCESS_AUDIT_ROLE);
            updateTasksRole.setCurAuditorId(activitiIdentity.getGroup());
            updateTasksRole.setCurAuditorName("");
            int updateTaskRoleCount = processInstanceDao.updateByProcessInstance(updateTasksRole);
            LOGGER.info("更新流程实例化数据角色{}",updateTaskRoleCount);
        }
        return httpResponse;
    }


}
