package com.wzc.scrollerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by wzc on 2017/7/29.
 * 该例子参考资料 http://blog.csdn.net/guolin_blog/article/details/48719871
 */

public class ScrollerLayout extends ViewGroup {

    private Scroller mScroller;
    private int mTouchSlop;
    private int mLeftBorder;
    private int mRightBorder;
    private float mXDown;
    private float mXMove;
    private float mXLastMove;

    public ScrollerLayout(Context context) {
        this(context, null);
    }

    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        // ===第一步, 创建Scroller实例
        mScroller = new Scroller(context);
        // 获取判定是滑动一页的最小像素数目
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledPagingTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取子控件的个数
        int childCount = getChildCount();
        // 分别测量每个子控件的大小
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec); // 这个
        }
        // 初始化左右边界值
        mLeftBorder = getChildAt(0).getLeft();
        mRightBorder = getChildAt(getChildCount() - 1).getRight();
        Log.d("ScrollerLayout", "mLeftBorder = " + mLeftBorder + ", mRightBorder = " + mRightBorder);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            // 把子控件在水平方向上进行布局
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                child.layout(i * child.getMeasuredWidth(), 0, (i + 1) * child.getMeasuredWidth(), child.getMeasuredHeight());
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("ScrollerLayout", "子控件的down事件");
                mXDown = ev.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXMove;
                // 当手指拖动值大于TouchSlop的值时,认为应该进行滚动,拦截子控件的事件,阻止事件传递到子控件中,把拦截到的事件交给ScrollerLayout的onTouchEvent()方法执行
                if (diff > mTouchSlop) {
                    Log.d("ScrollerLayout", "拦截子控件的手指move事件");
                    Log.d("ScrollerLayout", "手指拖动值大于TouchSlop的值");
                    return true;
                }
                Log.d("ScrollerLayout", "子控件的move事件");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("ScrollerLayout", "子控件的up事件");
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
                Log.d("ScrollerLayout", "收到了拦截到子控件的move事件");
                // 在这里对布局内容进行滚动从而影响拖动事件
                mXMove = event.getRawX();
                // 正值表示向左,负值表示向右
                int scrolledX = (int) (mXLastMove - mXMove);
                Log.d("ScrollerLayout", "getScrollX():" + getScrollX() + ", scrolledX = " + scrolledX + ", getWidth() = " + getWidth());
                // 进行边界保护
                if (getScrollX() + scrolledX < mLeftBorder) { // 避免滑动到了左边内容以外的区域
                    scrollTo(mLeftBorder, 0);
                    return true;
                }

                if ((getScrollX() + scrolledX + getWidth() > mRightBorder)) { // 避免滑动了右边内容以外的区域
                    scrollTo(mRightBorder - getWidth(), 0);
                    return true;
                }
                scrollBy(scrolledX, 0);
                // 更新上次触发ACTION_MOVE事件时的值
                mXLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                Log.d("ScrollerLayout", "收到了拦截到子控件的up事件");
                // 当手指抬起时,根据当前的滚动值来判断应该滚动到哪个页面
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                Log.d("ScrollerLayout", "getScrollX() = " + getScrollX() + ", dx = " + dx + ", targetIndex = " + targetIndex);
                // ===第二步, 调用startScroll()来初始化滚动数据并刷新界面
                // 参一: x方向上的起始滚动位置
                // 参三: x方向上需要滚动的距离,正值表示向左
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                // 第三步, 执行invalidate()刷新界面
                invalidate(); // 这个不能省略,省略了那么computeScroll()方法就不会调用了
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        // 第四步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) { // 判断滚动操作是否完成
            Log.d("ScrollerLayout", "mScroller.getCurrX():" + mScroller.getCurrX());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            // 第五步, 再次执行invalidate()刷新界面
            invalidate();
        }
    }
}
