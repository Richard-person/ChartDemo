package com.richard.chart.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/10/23.
 * 柱状图 信息
 */
public class ColumnarChartInfo implements Serializable {

    private int maxValue;//柱状图最大值

    private String leftField;//左侧字段

    private String rightField;//右侧字段

    private List<ColumnarChart> columnarChartList;//每一条柱状图值

    private List<String> yAxisValueList;//y轴 值

    public ColumnarChartInfo() {
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public List<ColumnarChart> getColumnarChartList() {
        return columnarChartList;
    }

    public void setColumnarChartList(List<ColumnarChart> columnarChartList) {
        this.columnarChartList = columnarChartList;
    }

    public List<String> getyAxisValueList() {
        return yAxisValueList;
    }

    public void setyAxisValueList(List<String> yAxisValueList) {
        this.yAxisValueList = yAxisValueList;
    }
}
