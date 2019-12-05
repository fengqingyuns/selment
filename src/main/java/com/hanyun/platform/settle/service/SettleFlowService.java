package com.hanyun.platform.settle.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.vo.PendingAuditReq;
import com.hanyun.platform.settle.vo.ProcessInstanceReq;
import com.hanyun.platform.settle.vo.base.PageResData;
import com.hanyun.platform.settle.vo.ExecuteAuditReq;

/**
 * Created by jack on 2017/5/22.
 */
public interface SettleFlowService {

    public HttpResponse startFlow(ProcessInstanceReq processInstanceReq);

    public PageResData getPendingAuditList(PendingAuditReq pendingAuditReq);

    public HttpResponse executeAudit(ExecuteAuditReq executeAuditReq);

    public PageResData auditTaskList(String businessKey, String processInstanceId);

    public HttpResponse readPicture(String businessKey);

}
