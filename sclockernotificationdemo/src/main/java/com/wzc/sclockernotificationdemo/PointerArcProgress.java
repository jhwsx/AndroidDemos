package com.wzc.sclockernotificationdemo;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by wzc on 2017/7/21.
 *
 */

public class PointerArcProgress extends View {
    /** 外层圆弧的线宽 */
    private int mOuterArcStrokeWidth;
    /** 外层圆弧的半径 */
    private float mOuterArcRadius;
    /** 外部圆弧对应的矩形区域*/
    private RectF mOuterArcRectF;
    /** 内层圆弧的线宽 */
    private int mInnerArcStrokeWidth;
    /** 内部圆弧对应的矩形区域 */
    private RectF mInnerArcRectF;
    /** 内部圆弧的半径 */
    private float mInnerArcRadius;
    /** 内部圆弧和外部圆弧的间距 */
    private int mDistanceBetweenOuterArcAndInnerArc;
    /** 刻度线的线宽 */
    private int mScaleLineStrokeWidth;
    /** 刻度线的长度 */
    private int mScaleLineLength;
    /** 开始绘制圆弧的角度值,以水平向右为0,以逆时针为正值*/
    private int mStartAngle;
    /** 圆弧扫过的角度值 */
    private int mSweepAngle;
    /** 读数文字对应的矩形*/
    private Rect mNumberTextRect;
    /** 读数文字大小 */
    private int mNumberTextSize;
    /** 最大的padding值*/
    private int mMaxPadding;
    /** 圆心的X坐标 */
    private float mCenterX;
    /** 圆心的Y坐标 */
    private float mCenterY;
    /** 最大值和最小值之间的等分数 */
    private int mSection;

    private int mDistanceBetweenScaleTopAndInnerArcBottom;
    private Paint mPaint;
    private Path mPath;
    /** 最小读数 */
    private int mMin = 0;
    /** 最大读数 */
    private int mMax = 100;
    /** 实时读数 */
    private int mProgress = mMin;
    /** 指针长半径 */
    private int mPointerLongRadius;
    /** 指针短半径 */
    private int mPointerShortRadius;
    private int mDistanceBetweenPointerTopAndScaleLineBottom;
    /** 中心圆半径 */
    private int mPointRadius;
    /** 指针圆弧部分对应的矩形 */
    private RectF mPointerArcRectF;
    /** 百分比符号文字大小 */
    private int mPercentSymbolTextSize;
    /** 百分比符号文字区域 */
    private Rect mPercentSymbolTextRect;
    /** 绘制文字的画笔 */
    private TextPaint mTextPaint;
    /** 已用文字大小 */
    private int  mUsedTextTextSize;
    /** 已用文字区域 */
    private Rect mUsedTextRect;

    public PointerArcProgress(Context context) {
        this(context,null);
    }

    public PointerArcProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PointerArcProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mMaxPadding = getMaxPadding();

        setPadding(mMaxPadding, mMaxPadding, mMaxPadding, mMaxPadding);

        // 根据传入的大小和模式,返回需要的大小
        final int width = resolveSize(dp2px(220), widthMeasureSpec);

        mOuterArcRadius = (width - mMaxPadding * 2 - mOuterArcStrokeWidth) * 1.0f / 2;
        mInnerArcRadius = mOuterArcRadius - mDistanceBetweenOuterArcAndInnerArc - mInnerArcStrokeWidth * 0.5f;
        final int height = getWidgetHeight();

        setMeasuredDimension(width,height);

        mCenterX = mCenterY = getMeasuredWidth() / 2;

        final float value1 = mMaxPadding + mOuterArcStrokeWidth * 0.5f;
        mOuterArcRectF.set(
                value1,
                value1,
                getMeasuredWidth() - value1,
                getMeasuredWidth() - value1
        );

        final float value =  value1 + mDistanceBetweenOuterArcAndInnerArc;
        mInnerArcRectF.set(
                value,
                value,
                getMeasuredWidth() - value,
                getMeasuredWidth() - value
        );

        mPointerLongRadius = (int) (mInnerArcRadius - mDistanceBetweenScaleTopAndInnerArcBottom
                - mScaleLineLength - mDistanceBetweenPointerTopAndScaleLineBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画外层圆弧
        mPaint.setColor(0x80ffffff);
        mPaint.setStrokeWidth(mOuterArcStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShader(null);
        canvas.drawArc(mOuterArcRectF,mStartAngle,mSweepAngle,false,mPaint);

        // 画内层圆弧
        mPaint.setColor(0x80ffffff);
        mPaint.setShader(null);
        mPaint.setStrokeWidth(mInnerArcStrokeWidth);
        canvas.drawArc(mInnerArcRectF,mStartAngle,mSweepAngle,false,mPaint);

        /**
         * 画进度圆弧(起始到信用值),即是已完成的进度
         */
        mPaint.setShader(generateSweepGradient()); // 设置进度条的渐变色
        mPaint.setStrokeWidth(mInnerArcStrokeWidth);
        canvas.drawArc(mInnerArcRectF, mStartAngle,
                calculateRelativeAngleWithValue(mProgress), false, mPaint);

        // 画刻度
        // 计算起始角度刻度线的起点和终点
        mPaint.setStrokeWidth(mScaleLineStrokeWidth);
        mPaint.setColor(Color.WHITE);
        mPaint.setShader(null);
        float startX = mCenterX;
        float startY = mMaxPadding + mOuterArcStrokeWidth * 0.5f + mDistanceBetweenOuterArcAndInnerArc
                + mInnerArcStrokeWidth * 0.5f + mDistanceBetweenScaleTopAndInnerArcBottom;
        float endX = mCenterX;
        float endY = startY + mScaleLineLength;
        canvas.drawLine(startX,startY,endX,endY,mPaint);
        final int sectionAngle = (int) (mSweepAngle * 1f/ mSection);
        // 逆时针旋转
        canvas.save();
        for (int i = 0; i < mSection / 2; i++) {
            canvas.rotate(-sectionAngle, mCenterX, mCenterY);
            canvas.drawLine(startX,startY,endX,endY,mPaint);
        }
        canvas.restore();
        // 顺时针旋转
        canvas.save();
        for (int i = 0; i < mSection / 2; i++) {
            canvas.rotate(sectionAngle, mCenterX, mCenterY);
            canvas.drawLine(startX,startY,endX,endY,mPaint);
        }
        canvas.restore();

        // 画指针
        // 先画一个竖直向下的指针
        canvas.save();
        canvas.rotate(mStartAngle - 90, mCenterX, mCenterY);
        float angle = calculateRelativeAngleWithValue(mProgress);
        canvas.rotate(angle, mCenterX, mCenterY);
        mPath.reset();
        mPath.moveTo(mCenterX + mPointerShortRadius, mCenterY);
        mPointerArcRectF.set(mCenterX - mPointerShortRadius, mCenterX - mPointerShortRadius,
                mCenterX + mPointerShortRadius,  mCenterX + mPointerShortRadius);
        mPath.addArc(mPointerArcRectF, 0, 360);
        mPath.lineTo(mCenterX, mCenterY + mPointerLongRadius);
        mPath.lineTo(mCenterX - mPointerShortRadius, mCenterY);
        mPath.close();
        mPaint.setColor(0x80ffffff);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShader(null);
        canvas.drawPath(mPath,mPaint);
        canvas.restore();

        // 画中心圆
        canvas.save();
        mPaint.setColor(0x80ffffff);
        mPaint.setShader(null);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mCenterX, mCenterY, mPointRadius, mPaint);
        canvas.restore();

        // 画实时读数
        mPaint.setTextSize(mNumberTextSize);
        String numberText = String.valueOf(mProgress);
        mTextPaint.setTextSize(mNumberTextSize);
        float numberTextHeight = mTextPaint.ascent() + mTextPaint.descent();
        mPaint.getTextBounds(numberText, 0, numberText.length(), mNumberTextRect);
        float textBaseline = (getHeight() - numberTextHeight) / 2.0f + 2 * mNumberTextRect.height();
        canvas.drawText(numberText,
                (getWidth() - mTextPaint.measureText(numberText))/2.0f,
                textBaseline,
                mTextPaint);
        // 画百分比符号
        mTextPaint.setTextSize(mPercentSymbolTextSize);
        String percentSymbolText = "%";
        float percentSymbolHeight = mTextPaint.ascent() + mTextPaint.descent();
        canvas.drawText(percentSymbolText,
                getWidth() / 2.0f + mTextPaint.measureText(numberText)
                        + dp2px(2), textBaseline + numberTextHeight
                        - percentSymbolHeight, mTextPaint);
        // 画已用文字
        String usedText = getContext().getString(R.string.main_text_used);
        mTextPaint.setTextSize(mUsedTextTextSize);
        mPaint.getTextBounds(numberText, 0, numberText.length(), mUsedTextRect);
        float usedTextHeight = mTextPaint.ascent() + mTextPaint.descent();
        canvas.drawText(usedText,
                (getWidth() - mTextPaint.measureText(usedText))/2.0f,
                textBaseline - usedTextHeight + dp2px(5),
                mTextPaint);
    }

    private int getWidgetHeight( ) {
        // 由半径 + 指针短半径 + 实时读数文字高度确定的高度
        final  int height1 = (int) (mOuterArcRadius + mOuterArcStrokeWidth * 2 + mMaxPadding * 2
                + mPointerShortRadius + mNumberTextRect.height() * 3 + mUsedTextRect.height());
        // 由开始角度确定的高度
        final  int height2 = (int) getCoordinatePoint(mOuterArcRadius,mStartAngle)[1] +  mOuterArcStrokeWidth * 2 + mMaxPadding * 2;
        // 由结束角度确定的高度
        final  int height3 = (int) getCoordinatePoint(mOuterArcRadius,mStartAngle + mSweepAngle)[1] +  mOuterArcStrokeWidth * 2 + mMaxPadding * 2;

        return Math.max(height1, Math.max(height2, height3));
    }

    private int getMaxPadding() {
        return Math.max(
                Math.max(getPaddingLeft(), getPaddingRight()),
                Math.max(getPaddingTop(), getPaddingBottom())
        );
    }

    private void init() {
        mPaint = getPaint();
        mOuterArcStrokeWidth = dp2px(2);
        mInnerArcStrokeWidth = dp2px(6);
        mScaleLineStrokeWidth = dp2px(2);
        mScaleLineLength = dp2px(8);
        mStartAngle = 135;
        mSweepAngle = 270;
        mNumberTextRect = new Rect();
        mOuterArcRectF= new RectF();
        mInnerArcRectF= new RectF();
        mPointerArcRectF = new RectF();
        mPath = new Path();
        mDistanceBetweenOuterArcAndInnerArc = dp2px(8);
        mDistanceBetweenScaleTopAndInnerArcBottom = dp2px(4);
        mDistanceBetweenPointerTopAndScaleLineBottom = dp2px(5);
        mSection = 10;
        mPointRadius =  dp2px(10);
        mNumberTextSize = sp2px(37);
        mPointerShortRadius = dp2px(5);
        mPercentSymbolTextSize = sp2px(11);
        mPercentSymbolTextRect = new Rect();
        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(mNumberTextSize);
        mTextPaint.setAntiAlias(true);
        mUsedTextTextSize = sp2px(15);
        mUsedTextRect = new Rect();
    }

    private Paint getPaint() {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        return paint;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }
    private float[] getCoordinatePoint(float radius, float angle) {
        float[] point = new float[2];
        // 注意: 这里是按照手机坐标系的啊.不要再想成是数学中的坐标系了啊.
        double arcAngle = Math.toRadians(angle); //将角度转换为弧度
        if (angle < 90) {
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        } else if (angle == 90) {
            point[0] = mCenterX;
            point[1] = mCenterY + radius;
        } else if (angle > 90 && angle < 180) {
            arcAngle = Math.PI * (180 - angle) / 180.0;
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        } else if (angle == 180) {
            point[0] = mCenterX - radius;
            point[1] = mCenterY;
        } else if (angle > 180 && angle < 270) {
            arcAngle = Math.PI * (angle - 180) / 180.0;
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        } else if (angle == 270) {
            point[0] = mCenterX;
            point[1] = mCenterY - radius;
        } else {
            arcAngle = Math.PI * (360 - angle) / 180.0;
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        }

        return point;
    }

    /**
     * 产生扫描渲染
     */
    private SweepGradient generateSweepGradient() {
        /**
         * 参1:渲染中心点的x坐标
         * 参2:渲染中心点的y坐标
         * 参3:围绕中心渲染的颜色数组，至少要有两种颜色值
         * 参4:相对位置的颜色数组,可为null,若为null,可为null,颜色沿渐变线均匀分布
         */
        SweepGradient sweepGradient = new SweepGradient(mCenterX, mCenterY,
                new int[]{Color.argb(10, 255, 255, 255), Color.argb(200, 255, 255, 255), Color.argb(255, 255, 255, 255)},
                new float[]{0, calculateRelativeAngleWithValue(mProgress) / 720, calculateRelativeAngleWithValue(mProgress) / 360}
        );
        Matrix matrix = new Matrix();
        matrix.setRotate(mStartAngle - 2, mCenterX, mCenterY); // 开始角度-1是为了开始效果不留白
        sweepGradient.setLocalMatrix(matrix);

        return sweepGradient;
    }

    /**
     * 相对起始角度计算信用分所对应的角度大小,将value值换算成为进度条上的角度值
     */
    private float calculateRelativeAngleWithValue(int value) {
        return mSweepAngle * value * 1f / mMax;
    }
    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

    public int getProgress() {
        return mProgress;
    }
}
