package com.hanyun.platform.settle.domain;

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
public class SettleBill {

    private Long id;

    private Date createTime;

    private Date updateTime;

    private String billId;

    private String entityId;

    private Integer entityType;

    private String brandId;

    private String storeId;

    private Integer separateSettle;

    private Integer capitalCollect;

    private Integer capitalCollectType;

    private Integer settleCircle;

    private Date tradeDateStart;

    private Date tradeDateEnd;

    private Date settleDate;

    private Integer settleStatus;

    private Integer auditStatus;

    private Long totalFlowAmt;

    private Long onlineFlowAmt;

    private Long payFee;

    private Long entityShaltRecAmt;

    private Long entityActualRecAmt;

    private Long bankCollectAmt;

    private Long platformActualRecAmt;

    private Long platformShareRate;

    private Long platformShareAmt;

    private Long brandShareRate;

    private Long brandShareAmt;

    private Long entityDivideAmt;

    private Integer diffResolveCnt;

    private Long diffResolveAmt;

    private Long diffResolvePayFee;

    private Long diffResolveActualAmt;

    private Integer diffSubmitCnt;

    private Long diffSubmitAmt;

    private Long diffSubmitPayFee;

    private Long diffSubmitActualAmt;

    private Long diffTotalAmt;

    private Long settleAmt;

    private Long turnoverPoints;

    private Long todayCommission;

    private Long commissionDeduction;

    private Long brandFlowAmt;

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

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
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

    public Integer getSeparateSettle() {
        return separateSettle;
    }

    public void setSeparateSettle(Integer separateSettle) {
        this.separateSettle = separateSettle;
    }

    public Integer getCapitalCollect() {
        return capitalCollect;
    }

    public void setCapitalCollect(Integer capitalCollect) {
        this.capitalCollect = capitalCollect;
    }

    public Integer getCapitalCollectType() {
        return capitalCollectType;
    }

    public void setCapitalCollectType(Integer capitalCollectType) {
        this.capitalCollectType = capitalCollectType;
    }

    public Integer getSettleCircle() {
        return settleCircle;
    }

    public void setSettleCircle(Integer settleCircle) {
        this.settleCircle = settleCircle;
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

    public Date getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
    }

    public Integer getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(Integer settleStatus) {
        this.settleStatus = settleStatus;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Long getTotalFlowAmt() {
        return totalFlowAmt;
    }

    public void setTotalFlowAmt(Long totalFlowAmt) {
        this.totalFlowAmt = totalFlowAmt;
    }

    public Long getOnlineFlowAmt() {
        return onlineFlowAmt;
    }

    public void setOnlineFlowAmt(Long onlineFlowAmt) {
        this.onlineFlowAmt = onlineFlowAmt;
    }

    public Long getPayFee() {
        return payFee;
    }

    public void setPayFee(Long payFee) {
        this.payFee = payFee;
    }

    public Long getEntityShaltRecAmt() {
        return entityShaltRecAmt;
    }

    public void setEntityShaltRecAmt(Long entityShaltRecAmt) {
        this.entityShaltRecAmt = entityShaltRecAmt;
    }

    public Long getEntityActualRecAmt() {
        return entityActualRecAmt;
    }

    public void setEntityActualRecAmt(Long entityActualRecAmt) {
        this.entityActualRecAmt = entityActualRecAmt;
    }

    public Long getBankCollectAmt() {
        return bankCollectAmt;
    }

    public void setBankCollectAmt(Long bankCollectAmt) {
        this.bankCollectAmt = bankCollectAmt;
    }

    public Long getPlatformActualRecAmt() {
        return platformActualRecAmt;
    }

    public void setPlatformActualRecAmt(Long platformActualRecAmt) {
        this.platformActualRecAmt = platformActualRecAmt;
    }

    public Long getPlatformShareRate() {
        return platformShareRate;
    }

    public void setPlatformShareRate(Long platformShareRate) {
        this.platformShareRate = platformShareRate;
    }

    public Long getPlatformShareAmt() {
        return platformShareAmt;
    }

    public void setPlatformShareAmt(Long platformShareAmt) {
        this.platformShareAmt = platformShareAmt;
    }

    public Long getBrandShareRate() {
        return brandShareRate;
    }

    public void setBrandShareRate(Long brandShareRate) {
        this.brandShareRate = brandShareRate;
    }

    public Long getBrandShareAmt() {
        return brandShareAmt;
    }

    public void setBrandShareAmt(Long brandShareAmt) {
        this.brandShareAmt = brandShareAmt;
    }

    public Long getEntityDivideAmt() {
        return entityDivideAmt;
    }

    public void setEntityDivideAmt(Long entityDivideAmt) {
        this.entityDivideAmt = entityDivideAmt;
    }

    public Integer getDiffResolveCnt() {
        return diffResolveCnt;
    }

    public void setDiffResolveCnt(Integer diffResolveCnt) {
        this.diffResolveCnt = diffResolveCnt;
    }

    public Long getDiffResolveAmt() {
        return diffResolveAmt;
    }

    public void setDiffResolveAmt(Long diffResolveAmt) {
        this.diffResolveAmt = diffResolveAmt;
    }

    public Long getDiffResolvePayFee() {
        return diffResolvePayFee;
    }

    public void setDiffResolvePayFee(Long diffResolvePayFee) {
        this.diffResolvePayFee = diffResolvePayFee;
    }

    public Long getDiffResolveActualAmt() {
        return diffResolveActualAmt;
    }

    public void setDiffResolveActualAmt(Long diffResolveActualAmt) {
        this.diffResolveActualAmt = diffResolveActualAmt;
    }

    public Integer getDiffSubmitCnt() {
        return diffSubmitCnt;
    }

    public void setDiffSubmitCnt(Integer diffSubmitCnt) {
        this.diffSubmitCnt = diffSubmitCnt;
    }

    public Long getDiffSubmitAmt() {
        return diffSubmitAmt;
    }

    public void setDiffSubmitAmt(Long diffSubmitAmt) {
        this.diffSubmitAmt = diffSubmitAmt;
    }

    public Long getDiffSubmitPayFee() {
        return diffSubmitPayFee;
    }

    public void setDiffSubmitPayFee(Long diffSubmitPayFee) {
        this.diffSubmitPayFee = diffSubmitPayFee;
    }

    public Long getDiffSubmitActualAmt() {
        return diffSubmitActualAmt;
    }

    public void setDiffSubmitActualAmt(Long diffSubmitActualAmt) {
        this.diffSubmitActualAmt = diffSubmitActualAmt;
    }

    public Long getDiffTotalAmt() {
        return diffTotalAmt;
    }

    public void setDiffTotalAmt(Long diffTotalAmt) {
        this.diffTotalAmt = diffTotalAmt;
    }

    public Long getSettleAmt() {
        return settleAmt;
    }

    public void setSettleAmt(Long settleAmt) {
        this.settleAmt = settleAmt;
    }

    public Long getTurnoverPoints() {
        return turnoverPoints;
    }

    public void setTurnoverPoints(Long turnoverPoints) {
        this.turnoverPoints = turnoverPoints;
    }

    public Long getTodayCommission() {
        return todayCommission;
    }

    public void setTodayCommission(Long todayCommission) {
        this.todayCommission = todayCommission;
    }

    public Long getCommissionDeduction() {
        return commissionDeduction;
    }

    public void setCommissionDeduction(Long commissionDeduction) {
        this.commissionDeduction = commissionDeduction;
    }

    public Long getBrandFlowAmt() {
        return brandFlowAmt;
    }

    public void setBrandFlowAmt(Long brandFlowAmt) {
        this.brandFlowAmt = brandFlowAmt;
    }

    public SettleBill() {
    }

    public SettleBill(String billId, String brandId, String storeId) {
        this.billId = billId;
        this.brandId = brandId;
        this.storeId = storeId;
    }
}