package com.hanyun.platform.settle.domain.activiti;

import java.util.List;

/**
 * Created by jack on 2017/5/26.
 */
public class VariableInstances {
    private String id;
    private String processInstanceId;
    private String processInstanceUrl;
    private String taskId;
    private TaskVariables variable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceUrl() {
        return processInstanceUrl;
    }

    public void setProcessInstanceUrl(String processInstanceUrl) {
        this.processInstanceUrl = processInstanceUrl;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public TaskVariables getVariable() {
        return variable;
    }

    public void setVariable(TaskVariables variable) {
        this.variable = variable;
    }
}
