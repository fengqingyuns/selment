package com.hanyun.platform.settle.domain.activiti;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2017/5/22.
 */
public class ActivitiProcessInstance {

    private String id;
    private String url;
    private String businessKey;
    private String suspended;
    private String ended;
    private String processDefinitionId;
    private String processDefinitionUrlid;
    private String processDefinitionKey;
    private String activityId;
    private List variables = new ArrayList();
    private String tenantId;
    private String name;
    private String completed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getSuspended() {
        return suspended;
    }

    public void setSuspended(String suspended) {
        this.suspended = suspended;
    }

    public String getEnded() {
        return ended;
    }

    public void setEnded(String ended) {
        this.ended = ended;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionUrlid() {
        return processDefinitionUrlid;
    }

    public void setProcessDefinitionUrlid(String processDefinitionUrlid) {
        this.processDefinitionUrlid = processDefinitionUrlid;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public List getVariables() {
        return variables;
    }

    public void setVariables(List variables) {
        this.variables = variables;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
}
