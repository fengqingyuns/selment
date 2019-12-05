package com.hanyun.platform.settle.vo;

/**
 * @author wangjie@hanyun.com
 * @Date 2018/3/22 14:03
 */
public class UmpayBill {
    //日期
    private String dateStr;
    private String brandId;
    //品牌
    private String brandName;
    private String storeId;
    //门店
    private String storeName;
    //省份
    private String  provice;
    //差异总金额
    private Double diffTotalAmount;
    //差异手续费
    private Double diffFeeAmount;
    //银行卡总金额
    private Double bankCardAmount;
    //银行卡手续费
    private Double bankCardFee;
    //总金额
    private Double totalAmount;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public Double getDiffTotalAmount() {
        return diffTotalAmount;
    }

    public void setDiffTotalAmount(Double diffTotalAmount) {
        this.diffTotalAmount = diffTotalAmount;
    }

    public Double getDiffFeeAmount() {
        return diffFeeAmount;
    }

    public void setDiffFeeAmount(Double diffFeeAmount) {
        this.diffFeeAmount = diffFeeAmount;
    }

    public Double getBankCardAmount() {
        return bankCardAmount;
    }

    public void setBankCardAmount(Double bankCardAmount) {
        this.bankCardAmount = bankCardAmount;
    }

    public Double getBankCardFee() {
        return bankCardFee;
    }

    public void setBankCardFee(Double bankCardFee) {
        this.bankCardFee = bankCardFee;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
