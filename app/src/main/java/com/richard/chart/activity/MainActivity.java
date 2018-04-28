package com.richard.chart.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.HorizontalScrollView;

import com.richard.chart.R;
import com.richard.chart.model.BrokenLineInfo;
import com.richard.chart.model.BrokenLinePoint;
import com.richard.chart.model.ColumnarChart;
import com.richard.chart.model.ColumnarChartInfo;
import com.richard.chart.model.HistogramListDataInfo;
import com.richard.chart.view.HistogramChart;
import com.richard.chart.view.LineChart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private LineChart line_chart1;
    private LineChart line_chart2;
    private HorizontalScrollView horizontal_scroll;
    private HistogramChart histogram_chart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        line_chart1 = findViewById(R.id.line_chart1);
        line_chart2 = findViewById(R.id.line_chart2);
        horizontal_scroll = findViewById(R.id.horizontal_scroll);

        //折线图
        line_chart1.setBrokenLineInfo(genratorSimulationData(LineChart.MODE_MONTH));
        line_chart1.invalidate();

        line_chart2.setBrokenLineInfo(genratorSimulationData(LineChart.MODE_WEEK));
        line_chart2.invalidate();

        //柱状图
        histogram_chart = new HistogramChart(this, generatorHistogramInfo());
        horizontal_scroll.addView(histogram_chart);
        histogram_chart.invalidate();
    }


    /**
     * 模拟折线图数据
     */
    private BrokenLineInfo genratorSimulationData(int mode) {
        int size;

        BrokenLineInfo brokenLineInfo = new BrokenLineInfo();

        if (mode == LineChart.MODE_WEEK) {
            size = 7;
            brokenLineInfo.setLeftField("9月21日-9月27日");
            brokenLineInfo.setLineDesc("本周总收入6888元");
        } else {
            size = 31;
            brokenLineInfo.setLeftField("9月1日-9月30日");
            brokenLineInfo.setLineDesc("月总收入38600元");
        }
        brokenLineInfo.setLineMode(mode);
        brokenLineInfo.setMaxValue(100);

        List<BrokenLinePoint> brokenLinePointList = new ArrayList<>();
        List<String> yAxisList = new ArrayList<>();

        if (mode == LineChart.MODE_WEEK) {
            yAxisList = Arrays.asList(new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"});
        }

        for (int i = 0; i < size; i++) {
            BrokenLinePoint brokenLinePoint = new BrokenLinePoint((int) (Math.random() * 100), i + "");
            brokenLinePointList.add(brokenLinePoint);

            if (mode == LineChart.MODE_MONTH) {
                yAxisList.add("" + (i + 1));
            }
        }
        brokenLineInfo.setYAxisValueList(yAxisList);
        brokenLineInfo.setBrokenLinePointList(brokenLinePointList);
        brokenLineInfo.setListData(Arrays.asList(new String[]{"a", "a", "a", "a", "a", "a", "a"}));

        return brokenLineInfo;
    }


    /**
     * 生成柱状图数据
     *
     * @return
     */
    private ColumnarChartInfo generatorHistogramInfo() {
        ColumnarChartInfo columnarChartInfo = new ColumnarChartInfo();
        columnarChartInfo.setMaxValue(100);
        columnarChartInfo.setyAxisValueList(Arrays.asList("一月", "二月", "三月", "四月", "五月", "六月", "七月","八月"));

        int size = columnarChartInfo.getyAxisValueList().size();
        List<ColumnarChart> columnarChartList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ColumnarChart columnarChart = new ColumnarChart();
            int num = (int) (Math.random() * columnarChartInfo.getMaxValue());
            columnarChart.setDesc(String.valueOf(num));
            columnarChart.setValue(num);
            columnarChartList.add(columnarChart);
        }

        columnarChartInfo.setColumnarChartList(columnarChartList);

        return columnarChartInfo;
    }
}
