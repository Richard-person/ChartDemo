package com.richard.chart.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.richard.chart.R;
import com.richard.chart.model.ColumnarChart;
import com.richard.chart.model.ColumnarChartInfo;
import com.richard.chart.util.DensityUtil;

/**
 * Created by Administrator on 2015/10/23.
 * 柱状图
 */
public class HistogramChart extends View {

    private Context context;

    private int viewWidth;
    private int viewHeight;

    private Paint paintText;
    private Paint paintLine;
    private Paint paintColumnar;

    private int yAxleColor = Color.parseColor("#a3a3a3");//y轴颜色
    private int yAxleTextColor = Color.parseColor("#121212");//y轴文本颜色
    private float yTextMarginTop;//y轴文本和y轴线的距离
    private float yTextSize;//y轴文本字体大小

    private int columnarColor = Color.parseColor("#67a0d5");//柱状图颜色
    private int columnarTextColor = Color.parseColor("#67a0d5");//柱状图文本颜色
    private float columnarTextSize;//柱状图文本大小

    private int rulingColor = Color.GRAY;//刻度线颜色
    private int xRuling;//横向刻度

    private ColumnarChartInfo columnarChartInfo;//柱状图信息


    public HistogramChart(Context context, ColumnarChartInfo columnarChartInfo) {
        super(context);
        this.context = context;
        this.columnarChartInfo = columnarChartInfo;
        init(null);
    }

    public HistogramChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public HistogramChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(attrs);
    }


    private void init(AttributeSet attributeSet) {
        yTextSize = DensityUtil.dp2px(context, 10);
        yTextMarginTop = DensityUtil.dp2px(context, 10);
        columnarTextSize = DensityUtil.sp2px(context, 8);


        if (attributeSet != null) {
            TypedArray tb = context.obtainStyledAttributes(attributeSet, R.styleable.HistogramChart);
            yAxleColor = tb.getColor(R.styleable.HistogramChart_h_yAxleColor, yAxleColor);
            yAxleTextColor = tb.getColor(R.styleable.HistogramChart_h_yAxleTextColor, yAxleTextColor);
            yTextMarginTop = tb.getDimension(R.styleable.HistogramChart_h_yTextMarginTop, yTextMarginTop);
            yTextSize = tb.getDimension(R.styleable.HistogramChart_h_yTextSize, yTextSize);
            columnarColor = tb.getColor(R.styleable.HistogramChart_h_columnarColor, columnarColor);
            columnarTextColor = tb.getColor(R.styleable.HistogramChart_h_columnarTextColor, columnarTextColor);
            columnarTextSize = tb.getDimension(R.styleable.HistogramChart_h_columnarTextSize, columnarTextSize);

            tb.recycle();
        }

        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintColumnar = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (columnarChartInfo == null) {
            paintText.setTextSize(DensityUtil.sp2px(context, 16));
            paintText.setColor(Color.RED);
            canvas.drawText("No initial value", (float) (viewWidth / 2.5), (float) (viewHeight / 1.6), paintText);
            return;
        }
        drawY(canvas);
        drawRuling(canvas);
        drawHistogram(canvas);
    }


    private void drawY(Canvas canvas) {
        paintLine.reset();
        paintText.reset();

        paintLine.setColor(yAxleColor);
        paintLine.setStrokeWidth(1);
        paintLine.setStyle(Paint.Style.STROKE);

        paintText.setColor(yAxleTextColor);
        paintText.setTextSize(yTextSize);

        canvas.drawLine(0, viewHeight - DensityUtil.dp2px(context, 15) - yTextSize, viewWidth, viewHeight - DensityUtil.dp2px(context, 15) - yTextSize, paintLine);

        int yAxisValueListSize = columnarChartInfo.getyAxisValueList().size();

        for (int i = 0; i < yAxisValueListSize; i++) {
            canvas.drawText(columnarChartInfo.getyAxisValueList().get(i), xRuling * (2 * i + 1), viewHeight - DensityUtil.dp2px(context, 10), paintText);
        }
    }


    private void drawHistogram(Canvas canvas) {
        paintColumnar.reset();
        paintText.reset();

        paintColumnar.setColor(columnarColor);
        paintColumnar.setStyle(Paint.Style.FILL);

        paintText.setColor(columnarTextColor);
        paintText.setTextSize(columnarTextSize);

        float x;
        float top;

        int yAxisValueListSize = columnarChartInfo.getyAxisValueList().size();

        for (int i = 0; i < yAxisValueListSize; i++) {
            ColumnarChart columnarChart = columnarChartInfo.getColumnarChartList().get(i);
            x = xRuling * (2 * i + 1);
            top = calculateTop(columnarChart.getValue());

            RectF rectF = new RectF(x, top, x + xRuling, viewHeight - yTextMarginTop - DensityUtil.dp2px(context, 15));
            canvas.drawRect(rectF, paintColumnar);
            canvas.drawText(columnarChart.getDesc(), x + DensityUtil.dp2px(context, 5), top - DensityUtil.dp2px(context, 2), paintText);
        }
    }


    private float calculateTop(int currentValue) {
        float v = viewHeight - yTextMarginTop - DensityUtil.dp2px(context, 15);
        float top;
        top = v / columnarChartInfo.getMaxValue();
        top = viewHeight - yTextMarginTop - currentValue * top - DensityUtil.dp2px(context, 15);
        return top;
    }


    private void drawRuling(Canvas canvas) {
        paintLine.reset();
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setColor(rulingColor);
        paintLine.setStrokeWidth(1);

        int ruling = (int) (viewHeight - yTextMarginTop - DensityUtil.dp2px(context, 15)) / 6;

        for (int i = 1; i <= 5; i++) {
            canvas.drawLine(0, i * ruling, viewWidth, i * ruling, paintLine);
        }
    }


   /* public void setColumnarChartInfo(ColumnarChartInfo columnarChartInfo) {
        this.columnarChartInfo = columnarChartInfo;
    }*/


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        viewWidth = this.getScreenWidth(context);
        viewHeight = DensityUtil.dp2px(context, 200);
        xRuling = DensityUtil.dp2px(context, 25);
        if (columnarChartInfo != null && columnarChartInfo.getyAxisValueList().size() > 7) {
            viewWidth = xRuling * (columnarChartInfo.getyAxisValueList().size() * 2 + 1);
        }
        setMeasuredDimension(viewWidth, viewHeight);
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
