package com.richard.chart.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/7.
 * 柱状图的列表数据信息
 */
public class HistogramListDataInfo implements Serializable {

    private String name;

    private String count;

    public HistogramListDataInfo(String name, String count) {
        this.name = name;
        this.count = count;
    }

    public HistogramListDataInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
