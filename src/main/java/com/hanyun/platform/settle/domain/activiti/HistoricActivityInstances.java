package com.hanyun.platform.settle.domain.activiti;

import java.util.List;

/**
 * Created by jack on 2017/5/26.
 */
public class HistoricActivityInstances {

    private List<ActivitHistoric> data;

    private Integer total;
    private Integer start;
    private String  sort;
    private String  order;
    private Integer  size;


    public List<ActivitHistoric> getData() {
        return data;
    }

    public void setData(List<ActivitHistoric> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
