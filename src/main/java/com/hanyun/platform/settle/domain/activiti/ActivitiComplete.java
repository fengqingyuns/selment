package com.hanyun.platform.settle.domain.activiti;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2017/5/24.
 */
public class ActivitiComplete {

    private String action;
    private List<TaskVariables> variables = new ArrayList<TaskVariables>();

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<TaskVariables> getVariables() {
        return variables;
    }

    public void setVariables(List<TaskVariables> variables) {
        this.variables = variables;
    }
}
