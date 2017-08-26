package com.wzc.scrollerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

/**
 * Created by wzc on 2017/8/26.
 * 自己再写一下郭霖的ScrollerViewPager的例子,希望能进步
 */


public class ScrollerViewPager1 extends ViewGroup {
    private static final String TAG = "ScrollerViewPager1";
    private int mLeftBorder; // 左边界
    private int mRightBorder; // 右边界
    private int mPagingTouchSlop; // 判断是滑动页面的最小距离
    private int mMovex;
    private Scroller mScroller;


    public ScrollerViewPager1(Context context) {
        this(context, null);
    }

    public ScrollerViewPager1(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mPagingTouchSlop = configuration.getScaledPagingTouchSlop();
        // 1, 实例化Scroller对象
        mScroller = new Scroller(context,new LinearInterpolator());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            // 测量每一个child
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }
        mLeftBorder = getChildAt(0).getLeft();
        mRightBorder = getChildAt(childCount - 1).getRight();
        Log.d(TAG, "mLeftBorder = " + mLeftBorder + ", mRightBorder = " + mRightBorder);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = getChildAt(i);
                view.layout(view.getMeasuredWidth() * i, 0, view.getMeasuredWidth() * (i + 1), view.getMeasuredHeight());
            }
        }

    }

    private int mDownX;
    private int mLastMoveX; // 子view最后的move事件的x坐标

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) ev.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                mMovex = (int) ev.getRawX();
                int diff = Math.abs(mMovex - mDownX);
                mLastMoveX = mMovex;
                if (diff > mPagingTouchSlop) {
                    Log.d(TAG, "用户要翻页了,把子view的move事件,发给父view来处理");
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
               mMovex = (int) event.getRawX();
                int scrollX =   mLastMoveX -mMovex;
                Log.d(TAG, "收到了子view发过来的move事件");
                scrollBy(scrollX, 0);
                // 修正边界
                if (getScrollX() + scrollX < mLeftBorder) { // 左边界
                    scrollTo(mLeftBorder,0);
                }
                if (getScrollX() + scrollX + getWidth() > mRightBorder) {
                    scrollTo(mRightBorder - getWidth(),0);
                }
                mLastMoveX = mMovex;
                break;
            case MotionEvent.ACTION_UP:
                int index = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = index * getWidth() - getScrollX();
                // 2, 调用Scroller的startScroll()方法
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                // 3, 调用invalidate()方法,刷新界面
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        // 4, 在computeScroll()方法中处理具体的滚动
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            // 5, 调用invalidate()方法,刷新界面
            invalidate();
        }
    }
}
