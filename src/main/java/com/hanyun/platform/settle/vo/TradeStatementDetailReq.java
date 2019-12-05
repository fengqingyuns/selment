package com.hanyun.platform.settle.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyun.platform.settle.vo.base.PageRequest;

public class TradeStatementDetailReq extends PageRequest{
	private String stateId;
	private String brandId;
	private String storeId;
	private String orderNum;
	@JsonFormat(pattern="yyyy-MM-dd")	
	private Date orderBegin;
	@JsonFormat(pattern="yyyy-MM-dd")	
	private Date orderEnd;
	@JsonFormat(pattern="yyyy-MM-dd")	
	private Date finishBeginTime;
	@JsonFormat(pattern="yyyy-MM-dd")	
	private Date finishEndTime;
	private Integer settleType;
	private String payType;
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
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
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Date getOrderBegin() {
		return orderBegin;
	}
	public void setOrderBegin(Date orderBegin) {
		this.orderBegin = orderBegin;
	}
	public Date getOrderEnd() {
		return orderEnd;
	}
	public void setOrderEnd(Date orderEnd) {
		this.orderEnd = orderEnd;
	}
	public Date getFinishBeginTime() {
		return finishBeginTime;
	}
	public void setFinishBeginTime(Date finishBeginTime) {
		this.finishBeginTime = finishBeginTime;
	}
	public Date getFinishEndTime() {
		return finishEndTime;
	}
	public void setFinishEndTime(Date finishEndTime) {
		this.finishEndTime = finishEndTime;
	}

	public Integer getSettleType() {
		return settleType;
	}
	public void setSettleType(Integer settleType) {
		this.settleType = settleType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
}
