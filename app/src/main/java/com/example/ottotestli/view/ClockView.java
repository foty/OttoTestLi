package com.example.ottotestli.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Calendar;

/**
 * Create by lxx
 * Date : 2020/1/2 14:10
 * Use by
 */
public class ClockView extends View {

    /**
     * 首先去分析一下这个view的组成部分。(外圈时针圈，文字，内圈的秒针刻度，分针，时针，秒针)
     * 动画效果，秒针移动时的渐变效果。
     * <p>
     * 暂定这个view需要完成的不走如下:
     * 1.初始化(测量，画笔初始化);
     * 2.外圈时针圈，包括文字;
     * 3.秒针圈(刻度圈);
     * 4.秒针，分针，时针
     * 5.动画
     * 6.秒针移动的颜色渐变效果
     * 7.其他完善
     * (将一些魔法值修改，需要对外提供的变成变量，对外公开，不需要的写成常量)
     *
     * 第一步 初始化：
     * 每一个自定义view的首要步骤就是继承view或者viewgroup，实现它的基本方法。
     * <代码>
     * view的测量方法写法我认为90%都可以使用这套写法，差不多成了一种固定写法了[狗头]
     * 画四条弧度:每条弧度中间有一定的间隔，刚好容下一个文字。这里定义每条弧跨越度数为80度，每条弧度留出10个弧度
     * 来放下文字。
     * <代码></>
     *
     *
     */

    private Paint mPaint;
    private Paint mTextPaint;
    private Paint mRulingPaint;
    private Paint mSecondPaint;
    //圆心坐标X
    private float circleX;
    //圆心坐标Y
    private float circleY;
    private float mHourDegree;
    private float mMinuteDegree;
    private float mSecondDegree;

    // 梯度扫描渐变
    private SweepGradient mSweepGradient;
    // 渐变矩阵，作用在SweepGradient
    private Matrix mGradientMatrix;
    private Paint mGradientPaint;


    public ClockView(Context context) {
        this(context, null, 0);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * 1.尝试修改字节码(asm,assist)
     * 2.
     *
     */

    /**
     * <p>
     * 涉及到的知识
     * 1.画弧，文字
     * 2.获取绘制文字的规格(更加美观)
     * 3.画布操作(旋转)
     * 4.渐变(梯度渐变/线性)
     */
    private void init() {
        //圆弧
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#ff22ff"));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);//设置绘制类型：填充/描边

        //文字
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.parseColor("#ff22ff"));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(24);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStyle(Paint.Style.STROKE);//设置绘制类型：填充/描边

        //刻度线
        mRulingPaint = new Paint();
        mRulingPaint.setColor(Color.parseColor("#FFA500"));
        mRulingPaint.setAntiAlias(true);
        mRulingPaint.setStrokeWidth(2);
        mRulingPaint.setStyle(Paint.Style.FILL);//设置绘制类型：填充/描边

        //秒针
        mSecondPaint = new Paint();
        mSecondPaint.setColor(Color.parseColor("#ff22ff"));
        mSecondPaint.setAntiAlias(true);
        mSecondPaint.setStrokeWidth(3);
        mSecondPaint.setStyle(Paint.Style.FILL);//设置绘制类型：填充/描边


        //渐变弧度。2点注意:1.渐变画笔style须是描边，颜色与背景色相同。2.渐变画笔宽度=刻度线宽度
        mGradientPaint = new Paint();
        mGradientPaint.setAntiAlias(true);
        mGradientPaint.setStrokeWidth(20);
        mGradientPaint.setStyle(Paint.Style.STROKE); //描边
        mGradientPaint.setColor(Color.parseColor("#ff22ff"));
        //颜色渐变，颜色选择使用白色加透明效果。(会产生一圈白色到白色半透明的效果)
        mGradientMatrix = new Matrix();
        mSweepGradient = new SweepGradient(200 + 300 / 2, 200 + 300 / 2,
                new int[]{Color.parseColor("#ffffff"), Color.parseColor("#80ffffff")}//不透明-半透明(A: 80)渐变
                , new float[]{0.75f, 1f});//渐变效果应用范围。这个是环形渐变则对应的渐变范围0-360度(从0度开始顺时针旋转)

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureDimension(widthMeasureSpec), measureDimension(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {

//        canvas.drawRect(0,0,getWidth(),getHeight(),mBgPaint);
        getCurrentTime();

        RectF mRectf = new RectF();
        mRectf.set(200, 200, 500, 500);

        //画弧
        // 不能直接画一个完整的圆，要留出4个位置绘制文字，这里假定每条弧度角度为80，留出10个角度来绘制文字，要让文字
        // 处于居中位置左右或者上下各分5个角度。
        for (int i = 0; i < 4; i++) {
            canvas.drawArc(mRectf, 5 + i * 90, 80, false, mPaint);
        }


        Rect boundRf = new Rect();
        //绘制文字
        mTextPaint.getTextBounds("12", 0, 2, boundRf);
        int textH = boundRf.height() / 2;
        canvas.drawText("3", 500, 200 + 300 / 2 + textH, mTextPaint);
        canvas.drawText("6", 200 + 300 / 2, 500 + textH, mTextPaint);
        canvas.drawText("9", 200, 200 + 300 / 2 + textH, mTextPaint);
        canvas.drawText("12", 200 + 300 / 2, 200 + textH, mTextPaint);

        //梯度渐变
        //matrix默认会在三点钟方向开始颜色的渐变，为了吻合钟表十二点钟顺时针旋转的方向，把秒针旋转的角度减去90度
        canvas.save();
        mGradientMatrix.setRotate(mSecondDegree - 90, 200 + 300 / 2, 200 + 300 / 2);
        mSweepGradient.setLocalMatrix(mGradientMatrix);

        //渐变内容与设置了渐变的画笔画出的内容有关，如果画出的是一个圆，那么作用范围就是一个圆；如果是一个正方形，那么作用范围就一个正方形。
        mGradientPaint.setShader(mSweepGradient);

        RectF gradiendRectf = new RectF();
        gradiendRectf.set(200 + 30, 200 + 30, 500 - 30, 500 - 30);
        canvas.drawArc(gradiendRectf, 0, 360, false, mGradientPaint);

        //画上刻度线
        for (int i = 0; i < 240; i++) {
            canvas.drawLine(200 + 300 / 2, 200 + 20, 200 + 300 / 2, 200 + 40, mRulingPaint);
            //
            canvas.rotate(1.5f, 200 + 300 / 2, 200 + 300 / 2);
        }
        canvas.restore();


        //秒针(要在梯度渐变后绘制)
        canvas.save();
        canvas.rotate(mSecondDegree, 200 + 300 / 2, 200 + 300 / 2);
        canvas.drawLine(200 + 300 / 2, 200 + 55, 200 + 300 / 2, 200 + 300 / 2, mSecondPaint);
        canvas.restore();

        invalidate();

    }

    /**
     * 测量大小
     *
     * @param measureSpec
     * @return
     */
    private int measureDimension(int measureSpec) {
        int defaultSize = 800;
        int model = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (model) {
            case MeasureSpec.EXACTLY:
                return size;
            case MeasureSpec.AT_MOST:
                return Math.min(size, defaultSize);
            case MeasureSpec.UNSPECIFIED:
            default:
                return defaultSize;
        }
    }

    /**
     * 获取当前时间
     */
    private void getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        float milliSecond = calendar.get(Calendar.MILLISECOND);
        float second = calendar.get(Calendar.SECOND) + milliSecond / 1000;
        float minute = calendar.get(Calendar.MINUTE) + second / 60;
        float hour = calendar.get(Calendar.HOUR) + minute / 60;
        mSecondDegree = second / 60 * 360;
        mMinuteDegree = minute / 60 * 360;
        mHourDegree = hour / 12 * 360;
    }
}
