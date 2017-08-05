package com.wzc.scrollerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Created by wzc on 2017/7/30.
 * http://blog.csdn.net/lfdfhl/article/details/53143114
 * 理解:Scroller类的getFinalY()
 * 1,看源码
 * public final int getFinalY() {
 * return mFinalY;
 * }
 * 这个方法获取的是Scroller类的mFinalY字段
 * 2,mFinalY是在哪里赋值的呢?
 * public void startScroll(int startX, int startY, int dx, int dy, int duration) {
 * mMode = SCROLL_MODE;
 * mFinished = false;
 * mDuration = duration;
 * mStartTime = AnimationUtils.currentAnimationTimeMillis();
 * mStartX = startX;
 * mStartY = startY;
 * mFinalX = startX + dx;
 * mFinalY = startY + dy;
 * mDeltaX = dx;
 * mDeltaY = dy;
 * mDurationReciprocal = 1.0f / (float) mDuration;
 * }
 * 可以看到mFinalY = startY + dy;可以这样理解:是每一次滚动dy后到达的位置.
 */

public class BounceableRelativeLayout extends RelativeLayout {
    private static final String TAG = "Bounceable";
    private Scroller mScroller;
    private GestureDetector mGestureDetector;

    public BounceableRelativeLayout(Context context) {
        this(context, null);
    }

    public BounceableRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        setLongClickable(true);
        // 1, 创建Scroller对象
        mScroller = new Scroller(context);
        // 2, 创建GestureDetector对象
        mGestureDetector = new GestureDetector(context, new MySimpleGestureDetectorListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
//                mCount = 0;
                // 在抬起时,将布局恢复到初始位置
                reset(0, 0);
                break;
            default:
                // 其余事件交给手势识别器处理
                return mGestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

//    private float mCount;

    private class MySimpleGestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            int disY = (int) ((distanceY - 0.5) / 2); // 这里除以2的目的是为了产生粘滞的效果,如果不除以任何数,那么跟随手指一起滚动,就没有这种效果了.
//            mCount += disY;
//            Log.d(TAG, "distanceY == " + distanceY + ", disY == " + disY + ", mCount == " + mCount);
//            Log.d(TAG, "getScrollY() == " + getScrollY() + ", mScroller.getFinalY() == " + mScroller.getFinalY() + ", disY == " + disY + ", mCount == " + mCount );
//            mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), 0, disY);
//            invalidate();
            beginScroll(0, disY);
            return false;
        }

    }

    private void reset(int x, int y) {
        int dx = x - mScroller.getFinalX();
        int dy = y - mScroller.getFinalY();
        Log.d(TAG, "mScroller.getFinalX() == " + mScroller.getFinalX() + ", mScroller.getFinalY() == " + mScroller.getFinalY());
        beginScroll(dx, dy);
    }

    private void beginScroll(int dx, int dy) {
        Log.d(TAG, "getScrollY() == " + getScrollY() + ", mScroller.getFinalY() == " + mScroller.getFinalY() + ", dy == " + dy);
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();
    }

}
