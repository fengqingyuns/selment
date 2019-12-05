package com.hanyun.platform.settle.vo;

/**
 * @author wangjie@hanyun.com
 * @Date 2018/3/29 14:07
 */
public class CommissionStatisticsVo {

    private Integer totalCount;
    private Object dataList;
    private Long sumAmount;

    public CommissionStatisticsVo() {
    }

    public CommissionStatisticsVo(Integer totalCount, Object dataList, Long sumAmount) {
        this.totalCount = totalCount;
        this.dataList = dataList;
        this.sumAmount = sumAmount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Object getDataList() {
        return dataList;
    }

    public void setDataList(Object dataList) {
        this.dataList = dataList;
    }

    public Long getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(Long sumAmount) {
        this.sumAmount = sumAmount;
    }
}
