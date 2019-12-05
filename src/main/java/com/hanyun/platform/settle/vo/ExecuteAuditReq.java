package com.hanyun.platform.settle.vo;

/**
 * Created by jack on 2017/5/24.
 */
public class ExecuteAuditReq {

    /**
     * 业务单据编号
     */
    private String businessKey;

    /**
     * 任务Id
     */
    private String taskId;

    /**
     *  通过审核 或者退回(true,false)
     */
    private String result;

    /**
     *  审核意见
     */
    private String remark;

    /**
     * 审核人id
     */
    private String userId;

    /**
     * 审核人名称
     */
    private String userName;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }
}
