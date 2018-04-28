package com.richard.chart.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/10/22.
 * 折线图信息
 */
public class BrokenLineInfo implements Serializable{

    private int lineMode;//类别

    private String lineDesc;//描述

    private int maxValue;//最大值

    private String leftField;//列表左边值

    private String rightField;//列表右边值

    private List<BrokenLinePoint> brokenLinePointList;

    private List<String> YAxisValueList;

    private List<String> listData;//列表数据

    public BrokenLineInfo() {
    }

    public int getLineMode() {
        return lineMode;
    }

    public void setLineMode(int lineMode) {
        this.lineMode = lineMode;
    }

    public String getLineDesc() {
        return lineDesc;
    }

    public void setLineDesc(String lineDesc) {
        this.lineDesc = lineDesc;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public String getLeftField() {
        return leftField;
    }

    public void setLeftField(String leftField) {
        this.leftField = leftField;
    }

    public String getRightField() {
        return rightField;
    }

    public void setRightField(String rightField) {
        this.rightField = rightField;
    }

    public List<BrokenLinePoint> getBrokenLinePointList() {
        return brokenLinePointList;
    }

    public void setBrokenLinePointList(List<BrokenLinePoint> brokenLinePointList) {
        this.brokenLinePointList = brokenLinePointList;
    }

    public List<String> getListData() {
        return listData;
    }

    public void setListData(List<String> listData) {
        this.listData = listData;
    }

    public List<String> getYAxisValueList() {
        return YAxisValueList;
    }

    public void setYAxisValueList(List<String> YAxisValueList) {
        this.YAxisValueList = YAxisValueList;
    }
}
