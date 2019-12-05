/**
 * 
 */
package com.hanyun.platform.settle.domain;

import java.util.Date;

/**
 * 
* @Description: 对账差异明细
* @author wangjie@hanyun.com
* @date 2016年8月16日 下午7:48:17
 */
public class StatementDiffDetailExcel {


    private String orderId;
    private Date orderTime;
    private double orderAmount;
    private double payAmount;
    private String payType;
    private String settleType;
    private Date finishTime; 
    private String operateType;
    private double amount;
    private double mchFee;//手续费
    private String diffSrc;
    private Date reportTime;
    private String diffType;
    private String diffStatus;//差异状态    
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getSettleType() {
		return settleType;
	}
	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getMchFee() {
        return mchFee;
    }
    public void setMchFee(double mchFee) {
        this.mchFee = mchFee;
    }
	public String getDiffSrc() {
		return diffSrc;
	}
	public void setDiffSrc(String diffSrc) {
		this.diffSrc = diffSrc;
	}
	public Date getReportTime() {
		return reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	public String getDiffType() {
		return diffType;
	}
	public void setDiffType(String diffType) {
		this.diffType = diffType;
	}
	public String getDiffStatus() {
		return diffStatus;
	}
	public void setDiffStatus(String diffStatus) {
		this.diffStatus = diffStatus;
	}

    
	
}
