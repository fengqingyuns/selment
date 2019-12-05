package com.hanyun.platform.settle.vo;

import java.util.List;
import java.util.Map;

/**
 * Created by jack on 2017/5/27.
 */
public class UserInfo {

    private String server_machine_name;
    private String server_ip;
    private String server_current_time;
    private String code;
    private String message;
    private Map data;

    public String getServer_machine_name() {
        return server_machine_name;
    }

    public void setServer_machine_name(String server_machine_name) {
        this.server_machine_name = server_machine_name;
    }

    public String getServer_ip() {
        return server_ip;
    }

    public void setServer_ip(String server_ip) {
        this.server_ip = server_ip;
    }

    public String getServer_current_time() {
        return server_current_time;
    }

    public void setServer_current_time(String server_current_time) {
        this.server_current_time = server_current_time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }
}
