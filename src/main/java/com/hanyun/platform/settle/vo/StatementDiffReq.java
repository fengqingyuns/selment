package com.hanyun.platform.settle.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyun.platform.settle.vo.base.PageRequest;

public class StatementDiffReq extends PageRequest{
    private String transId;
	private String brandId;
	private String storeId;
	@JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date reportStartTime;
	@JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date reportEndTime;
	private Integer diffSrc;
	private Integer diffStatus;
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
	public Date getReportStartTime() {
		return reportStartTime;
	}
	public void setReportStartTime(Date reportStartTime) {
		this.reportStartTime = reportStartTime;
	}
	public Date getReportEndTime() {
		return reportEndTime;
	}
	public void setReportEndTime(Date reportEndTime) {
		this.reportEndTime = reportEndTime;
	}
	public Integer getDiffSrc() {
		return diffSrc;
	}
	public void setDiffSrc(Integer diffSrc) {
		this.diffSrc = diffSrc;
	}
	public Integer getDiffStatus() {
		return diffStatus;
	}
	public void setDiffStatus(Integer diffStatus) {
		this.diffStatus = diffStatus;
	}
    public String getTransId() {
        return transId;
    }
    public void setTransId(String transId) {
        this.transId = transId;
    }
	
}
