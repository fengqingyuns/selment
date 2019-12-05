package com.hanyun.platform.settle.domain.activiti;

import java.util.List;

/**
 * Created by jack on 2017/5/23.
 */
public class ActivitQueryTask {
    private List<ActivitQueryTaskContent> data;
    private String total;
    private String start;
    private String sort;
    private String order;
    private String size;

    public List<ActivitQueryTaskContent> getData() {
        return data;
    }

    public void setData(List<ActivitQueryTaskContent> data) {
        this.data = data;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
