package com.hanyun.platform.settle.vo.commissionbill;

import java.util.Date;

/**
 * 通过参数查询佣金信息
 * @author wangjie@hanyun.com
 * @Date 2018/3/6 17:52
 */
public class QuerySettleBillPara {

    private String brandId;

    private String storeId;

    private Integer entityType;

    private Integer separateSettle;

    private Date tradeDateStart;

    private Date tradeDateEnd;

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

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public Integer getSeparateSettle() {
        return separateSettle;
    }

    public void setSeparateSettle(Integer separateSettle) {
        this.separateSettle = separateSettle;
    }

    public Date getTradeDateStart() {
        return tradeDateStart;
    }

    public void setTradeDateStart(Date tradeDateStart) {
        this.tradeDateStart = tradeDateStart;
    }

    public Date getTradeDateEnd() {
        return tradeDateEnd;
    }

    public void setTradeDateEnd(Date tradeDateEnd) {
        this.tradeDateEnd = tradeDateEnd;
    }
}
