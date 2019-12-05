package com.hanyun.platform.settle.domain.activiti;

/**
 * Created by jack on 2017/5/23.
 */
public class ActivitiIdentity {
    private String url;
    private String user;
    private String group;
    private String type;

    public ActivitiIdentity() {
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
