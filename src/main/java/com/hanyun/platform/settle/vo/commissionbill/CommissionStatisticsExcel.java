package com.hanyun.platform.settle.vo.commissionbill;

/**
 * @author wangjie@hanyun.com
 * @Date 2018/3/2 16:15
 */
public class CommissionStatisticsExcel {

    private String brandName;
    private String storeName;
    private String tradeDate;
    private Double commissionAmount;
    private Double crrentDayTurnover;
    private Double turnoverPoints;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Double getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(Double commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public Double getCrrentDayTurnover() {
        return crrentDayTurnover;
    }

    public void setCrrentDayTurnover(Double crrentDayTurnover) {
        this.crrentDayTurnover = crrentDayTurnover;
    }

    public Double getTurnoverPoints() {
        return turnoverPoints;
    }

    public void setTurnoverPoints(Double turnoverPoints) {
        this.turnoverPoints = turnoverPoints;
    }
}
