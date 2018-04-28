package com.richard.chart.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/22.
 */
public class BrokenLinePoint implements Serializable {

    private int value;

    private String desc;

    public BrokenLinePoint(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public BrokenLinePoint() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
