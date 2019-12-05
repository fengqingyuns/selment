package com.hanyun.platform.settle.vo.settlebill;

import com.hanyun.platform.settle.vo.base.PageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author:wangximin
 * @date:2017/4/7
 */
public class SettleBillDetailReq extends PageRequest{

    private String billId;

    private String brandId;

    private String storeId;

    private String orderNum;

    private Date tradeDateStart;

    private Date tradeDateEnd;

    private Integer settleType;

    private String transId;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
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

    public Date getTradeDateStart() {
        return tradeDateStart;
    }

    public Date getTradeDateEnd() {
        return tradeDateEnd;
    }

    public void setTradeDateEnd(Date tradeDateEnd) {
        this.tradeDateEnd = tradeDateEnd;
    }

    public void setTradeDateStart(Date tradeDateStart) {
        this.tradeDateStart = tradeDateStart;
    }

    public Integer getSettleType() {
        return settleType;
    }

    public void setSettleType(Integer settleType) {
        this.settleType = settleType;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }
}
