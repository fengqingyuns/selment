/**
 * 
 */
package com.hanyun.platform.settle.domain;

import java.util.Date;

/**
 * 结算单明细
 * @author liyinglong@hanyun.com
 * @date 2016年7月25日 下午10:11:15
 */
public class TradeStatementDetail {
	private String transId;
    private String payId;
    private String merchantId;
    private String brandId;
    private String storeId;
    private String orderId;
    private Date orderTime;
    private Long orderAmount;
    private Long payAmount;
    private String payType;
    private Integer settleType;
    private Integer operateType;
    private Long amount;
    private Integer status;
    private Date finishTime;
    private Integer diffStatus;//差异状态
    private Long mchFee;
    private Long mchFeeRate;
    private Long mchFeeMax;
    private String typeCategory;//支付分类
    private String orderDocumentId;


    public String getPayId() {
        return payId;
    }
    public void setPayId(String payId) {
        this.payId = payId;
    }
    public String getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
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
    public Long getOrderAmount() {
        return orderAmount;
    }
    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }
    public Long getPayAmount() {
        return payAmount;
    }
    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }
    public String getPayType() {
        return payType;
    }
    public void setPayType(String payType) {
        this.payType = payType;
    }

	public Integer getSettleType() {
		return settleType;
	}
	public void setSettleType(Integer settleType) {
		this.settleType = settleType;
	}
	public Integer getOperateType() {
        return operateType;
    }
    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }
    public Long getAmount() {
        return amount;
    }
    public void setAmount(Long amount) {
        this.amount = amount;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Date getFinishTime() {
        return finishTime;
    }
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
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
    public Long getMchFee() {
        return mchFee;
    }
    public void setMchFee(Long mchFee) {
        this.mchFee = mchFee;
    }
    public Long getMchFeeRate() {
        return mchFeeRate;
    }
    public void setMchFeeRate(Long mchFeeRate) {
        this.mchFeeRate = mchFeeRate;
    }
    public Long getMchFeeMax() {
        return mchFeeMax;
    }
    public void setMchFeeMax(Long mchFeeMax) {
        this.mchFeeMax = mchFeeMax;
    }
	public String getTypeCategory() {
		return typeCategory;
	}
	public void setTypeCategory(String typeCategory) {
		this.typeCategory = typeCategory;
	}

    public String getOrderDocumentId() {
        return orderDocumentId;
    }

    public void setOrderDocumentId(String orderDocumentId) {
        this.orderDocumentId = orderDocumentId;
    }
}
