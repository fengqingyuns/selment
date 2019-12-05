package com.hanyun.platform.settle.domain;

import com.hanyun.platform.settle.vo.base.PageRequest;

import java.util.Date;

/**
 * <pre>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * </pre>
 */
public class ProcessInstance {

    private Long id;

    private Date createTime;

    private Date updateTime;

    private String brandId;

    private String storeId;

    private String businessKey;

    private Integer businessType;

    private String processDefId;

    private String processInstId;

    private Integer processStatus;

    private Date startTime;

    private Date endTime;

    private String curTaskDefKey;

    private String curTaskDefName;

    private String curTaskId;

    private Integer curAuditorType;

    private String curAuditorId;

    private String curAuditorName;

    private String starterId;

    private String starterName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getProcessDefId() {
        return processDefId;
    }

    public void setProcessDefId(String processDefId) {
        this.processDefId = processDefId;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCurTaskDefKey() {
        return curTaskDefKey;
    }

    public void setCurTaskDefKey(String curTaskDefKey) {
        this.curTaskDefKey = curTaskDefKey;
    }

    public String getCurTaskDefName() {
        return curTaskDefName;
    }

    public void setCurTaskDefName(String curTaskDefName) {
        this.curTaskDefName = curTaskDefName;
    }

    public String getCurTaskId() {
        return curTaskId;
    }

    public void setCurTaskId(String curTaskId) {
        this.curTaskId = curTaskId;
    }

    public Integer getCurAuditorType() {
        return curAuditorType;
    }

    public void setCurAuditorType(Integer curAuditorType) {
        this.curAuditorType = curAuditorType;
    }

    public String getCurAuditorId() {
        return curAuditorId;
    }

    public void setCurAuditorId(String curAuditorId) {
        this.curAuditorId = curAuditorId;
    }

    public String getCurAuditorName() {
        return curAuditorName;
    }

    public void setCurAuditorName(String curAuditorName) {
        this.curAuditorName = curAuditorName;
    }

    public String getStarterId() {
        return starterId;
    }

    public void setStarterId(String starterId) {
        this.starterId = starterId;
    }

    public String getStarterName() {
        return starterName;
    }

    public void setStarterName(String starterName) {
        this.starterName = starterName;
    }
}