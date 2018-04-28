package com.richard.chart.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

import com.richard.chart.R;
import com.richard.chart.model.BrokenLineInfo;
import com.richard.chart.model.BrokenLinePoint;
import com.richard.chart.util.DensityUtil;

/**
 * Created by Administrator on 2015/10/21.
 * 折线图
 */
public class LineChart extends View {

    private Context context;

    public final static int MODE_WEEK = 1;
    public final static int MODE_MONTH = 2;

    private int viewWidth;
    private int viewHeight;

    private Paint paintLine;
    private Paint paintText;
    private Paint paintCircle;

    private float yMarginBottom;//y轴和底部的距离
    private float yRulingHeight;//y轴刻度线的高
    private float yTextMarginTop;//y轴文本和y轴线的距离
    private float yTextSize;//y轴文本字体大小
    private int yAxleColor = Color.BLACK;//y轴颜色
    private int yAxleTextColor = Color.BLACK;//y轴文本颜色

    private float titleTextSize;//标题字体大小
    private int titleTextColor = Color.WHITE;//标题文本颜色
    private int titleTextBgColor = Color.RED;//标题文本背景颜色

    private float pointTextSize;//坐标点文本字体大小
    private int pointTextColor = Color.RED;//坐标点的文本颜色
    private int pointCircleColor = Color.RED;//坐标点颜色
    private int pointLineColor = Color.RED;//曲线颜色
    private float pointSize;//圆点大小

    private float lineSize;//线条大小

    private int rulingColor = Color.GRAY;//刻度线颜色

    private BrokenLineInfo brokenLineInfo;

    public LineChart(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        yMarginBottom = DensityUtil.dp2px(context, 50);
        yRulingHeight = DensityUtil.dp2px(context, 5);
        yTextMarginTop = DensityUtil.dp2px(context, 10);
        yTextSize = DensityUtil.sp2px(context, 8);

        titleTextSize = DensityUtil.sp2px(context, 10);

        pointTextSize = DensityUtil.sp2px(context, 8);
        pointSize = DensityUtil.dp2px(context, 3);

        lineSize = DensityUtil.dp2px(getContext(),1);

        if (attrs != null) {
            TypedArray tb = context.obtainStyledAttributes(attrs, R.styleable.LineChart);
            yMarginBottom = tb.getDimension(R.styleable.LineChart_yMarginBottom, yMarginBottom);
            yRulingHeight = tb.getDimension(R.styleable.LineChart_yRulingHeight, yRulingHeight);
            yTextMarginTop = tb.getDimension(R.styleable.LineChart_yTextMarginTop, yTextMarginTop);
            yTextSize = tb.getDimension(R.styleable.LineChart_yTextSize, yTextSize);
            yAxleColor = tb.getColor(R.styleable.LineChart_yAxleColor, yAxleColor);
            yAxleTextColor = tb.getColor(R.styleable.LineChart_yAxleTextColor, yAxleTextColor);

            titleTextSize = tb.getDimension(R.styleable.LineChart_titleTextSize, titleTextSize);
            titleTextColor = tb.getColor(R.styleable.LineChart_titleTextColor, titleTextColor);
            titleTextBgColor = tb.getColor(R.styleable.LineChart_titleTextBgColor, titleTextBgColor);

            pointTextSize = tb.getDimension(R.styleable.LineChart_pointTextSize, pointTextSize);
            pointTextColor = tb.getColor(R.styleable.LineChart_pointTextColor, pointTextColor);
            pointCircleColor = tb.getColor(R.styleable.LineChart_pointCircleColor, pointCircleColor);
            pointLineColor = tb.getColor(R.styleable.LineChart_pointLineColor, pointLineColor);
            pointSize = tb.getDimension(R.styleable.LineChart_pointSize, pointSize);

            tb.recycle();
        }

        paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLine.setAntiAlias(true);
        paintLine.setFilterBitmap(true);

        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));

        if (brokenLineInfo == null) {
            paintText.setTextSize(DensityUtil.sp2px(context, 16));
            paintText.setColor(Color.RED);
            canvas.drawText("No initial value", (float) (viewWidth / 2.5), (float) (viewHeight / 1.6), paintText);
            return;
        }

        drawY(canvas);
        drawRuling(canvas);
        drawCurve(canvas);
        drawLineTitle(canvas);
    }


    private void drawLineTitle(Canvas canvas) {
        paintLine.setStyle(Paint.Style.FILL);
        paintLine.setColor(titleTextBgColor);

        paintText.setColor(titleTextColor);
        paintText.setTextSize(titleTextSize);

        float top = viewHeight - yMarginBottom + yTextMarginTop + DensityUtil.dp2px(context, 15);

        RectF rectF = new RectF(viewWidth / 2 - paintText.measureText(brokenLineInfo.getLineDesc()) / 2 - DensityUtil.dp2px(context, 30), top, viewWidth / 2 + paintText.measureText(brokenLineInfo.getLineDesc()) / 2 + DensityUtil.dp2px(context, 30), viewHeight - DensityUtil.dp2px(context, 7));
        canvas.drawRect(rectF, paintLine);

        paintLine.setColor(Color.WHITE);
        canvas.drawLine(rectF.left + DensityUtil.dp2px(context, 7), rectF.centerY(), rectF.left + DensityUtil.dp2px(context, 30), rectF.centerY(), paintLine);
        canvas.drawCircle(rectF.left + DensityUtil.dp2px(context, 18), rectF.centerY(), pointSize, paintText);

        canvas.drawText(brokenLineInfo.getLineDesc(), rectF.left + DensityUtil.dp2px(context, 40), rectF.centerY() + DensityUtil.dp2px(context, 4), paintText);

    }


    private void drawCurve(Canvas canvas) {
        paintText.setColor(pointTextColor);
        paintText.setTextSize(pointTextSize);

        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setColor(pointLineColor);
        paintLine.setStrokeWidth(lineSize);

        paintCircle.setColor(pointCircleColor);
        paintCircle.setStyle(Paint.Style.FILL);

        Path path = new Path();

        float x = 0;
        float y = 0;

        if (brokenLineInfo.getLineMode() == MODE_WEEK) {
            BrokenLinePoint brokenLinePoint = brokenLineInfo.getBrokenLinePointList().get(0);
            int xRuling = viewWidth / (brokenLineInfo.getYAxisValueList().size() + 1);
            y = calculateY(brokenLinePoint.getValue());

            canvas.drawCircle(xRuling, y, pointSize, paintCircle);
            canvas.drawText(brokenLinePoint.getValue() + "", xRuling - (paintText.measureText(brokenLinePoint.getValue() + "")), y - 10, paintText);
            path.moveTo(xRuling, y);

            for (int i = 1; i < brokenLineInfo.getYAxisValueList().size(); i++) {
                brokenLinePoint = brokenLineInfo.getBrokenLinePointList().get(i);
                x = (i + 1) * xRuling;
                y = calculateY(brokenLinePoint.getValue());
                path.lineTo(x, y);
                //画圆点
                canvas.drawCircle(x, y, pointSize, paintCircle);
                canvas.drawText(brokenLinePoint.getValue() + "", x - (paintText.measureText(brokenLinePoint.getValue() + "")), y - 10, paintText);
            }
            //画曲线
            canvas.drawPath(path, paintLine);

        } else {
            int dataLength = brokenLineInfo.getBrokenLinePointList().size();
            int xRuling = viewWidth / dataLength;
            int firstRuling = xRuling / 2;

            x = xRuling - firstRuling;
            y = calculateY(brokenLineInfo.getBrokenLinePointList().get(0).getValue());

            canvas.drawCircle(x, y, pointSize, paintCircle);
            path.moveTo(x, y);

            for (int i = 1; i < dataLength; i++) {
                x = (i + 1) * xRuling - firstRuling;
                y = calculateY(brokenLineInfo.getBrokenLinePointList().get(i).getValue());
                path.lineTo(x, y);
                //画圆点
                canvas.drawCircle(x, y, pointSize, paintCircle);
            }
            //画曲线
            canvas.drawPath(path, paintLine);
        }

    }


    private float calculateY(int currentValue) {
        float v = viewHeight - yMarginBottom - 10;
        float y;
        y = v / brokenLineInfo.getMaxValue();
        y = viewHeight - yMarginBottom - currentValue * y;
        return y;
    }


    private void drawY(Canvas canvas) {
        paintLine.setStrokeWidth(lineSize);
        paintLine.setColor(yAxleColor);

        paintText.setTextSize(yTextSize);
        paintText.setColor(yAxleTextColor);
        //画y轴线
        canvas.drawLine(0f, viewHeight - yMarginBottom, viewWidth, viewHeight - yMarginBottom, paintLine);
        //画刻度
        if (brokenLineInfo.getLineMode() == MODE_WEEK) {
            int ruling = viewWidth / (brokenLineInfo.getYAxisValueList().size() + 1);
            int firstRuling = 0;
            for (int i = 1; i <= (brokenLineInfo.getYAxisValueList().size() + 1); i++) {
                if (i == 1) {
                    firstRuling = ruling / 2;
                    canvas.drawLine(firstRuling, viewHeight - yMarginBottom, firstRuling, viewHeight - yMarginBottom - yRulingHeight, paintLine);
                } else {
                    canvas.drawLine(i * ruling - firstRuling, viewHeight - yMarginBottom, i * ruling - firstRuling, viewHeight - yMarginBottom - yRulingHeight, paintLine);
                }
                if (i != (brokenLineInfo.getYAxisValueList().size() + 1)) {
                    canvas.drawText(brokenLineInfo.getYAxisValueList().get(i - 1), i * ruling - (firstRuling / 2), viewHeight - yMarginBottom + yTextMarginTop, paintText);
                }
            }
        } else {
            int dataLength = brokenLineInfo.getBrokenLinePointList().size();
            int ruling = viewWidth / dataLength;
            int firstRuling = 0;
            for (int i = 1; i <= dataLength; i++) {
                if (i == 1) {
                    firstRuling = ruling / 2;
                    canvas.drawLine(firstRuling, viewHeight - yMarginBottom, firstRuling, viewHeight - yMarginBottom - yRulingHeight, paintLine);
                } else {
                    canvas.drawLine(i * ruling - firstRuling, viewHeight - yMarginBottom, i * ruling - firstRuling, viewHeight - yMarginBottom - yRulingHeight, paintLine);
                }
                canvas.drawText(i + "", i * ruling - firstRuling - 7, viewHeight - yMarginBottom + yTextMarginTop, paintText);
            }
        }
    }


    private void drawRuling(Canvas canvas) {
        paintLine.reset();
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setColor(rulingColor);
        paintLine.setStrokeWidth(DensityUtil.dp2px(getContext(),0.2F));

        int ruling = (int) (viewHeight - yMarginBottom) / 4;

        for (int i = 1; i <= 3; i++) {
            canvas.drawLine(DensityUtil.dp2px(context, 15), i * ruling, viewWidth - DensityUtil.dp2px(context, 15), i * ruling, paintLine);
        }

    }


    public BrokenLineInfo getBrokenLineInfo() {
        return brokenLineInfo;
    }

    public void setBrokenLineInfo(BrokenLineInfo brokenLineInfo) {
        this.brokenLineInfo = brokenLineInfo;
    }
}
