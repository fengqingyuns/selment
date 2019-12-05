package com.hanyun.platform.settle.vo.commissionbill;

import java.util.Date;

/**
 * @author wangjie@hanyun.com
 * @Date 2018/3/1 8:51
 */
public class CommissionDetail {

    private String brandName;

    private String storeName;

    private Date tredeDate;

    private Long crrentDayTurnover;

    private Long turnoverPoints;

    private Long commissionAmount;

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

    public Date getTredeDate() {
        return tredeDate;
    }

    public void setTredeDate(Date tredeDate) {
        this.tredeDate = tredeDate;
    }

    public Long getCrrentDayTurnover() {
        return crrentDayTurnover;
    }

    public void setCrrentDayTurnover(Long crrentDayTurnover) {
        this.crrentDayTurnover = crrentDayTurnover;
    }

    public Long getTurnoverPoints() {
        return turnoverPoints;
    }

    public void setTurnoverPoints(Long turnoverPoints) {
        this.turnoverPoints = turnoverPoints;
    }

    public Long getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(Long commissionAmount) {
        this.commissionAmount = commissionAmount;
    }
}
