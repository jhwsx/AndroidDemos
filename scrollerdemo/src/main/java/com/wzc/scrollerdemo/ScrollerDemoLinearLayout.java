package com.wzc.scrollerdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Scroller;

class ScrollerDemoLinearLayout extends LinearLayout {

        private Scroller mScroller;

        public ScrollerDemoLinearLayout(Context context) {
            this(context, null);
        }

        public ScrollerDemoLinearLayout(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            // 1, 初始化Scroller
            mScroller = new Scroller(context);
        }

        public void startScroll() {
            // 2, 调用startScroll(), 开始滚动
            mScroller.startScroll(0, 0, -200, 0, 2000);
            // 3, 执行invalidate(), 刷新界面
            invalidate();
        }
        // 4, 重写View的computeScroll()并在其内部实现与滚动相关的业务逻辑
        @Override
        public void computeScroll() {
            super.computeScroll();
            if (mScroller.computeScrollOffset()) {
                int currX = mScroller.getCurrX();
                int currY = mScroller.getCurrY();
                Log.d("ScrollerDemoLinearLayou", "currX = " + currX + ", currY = " + currY);
                scrollTo(currX,currY);
                // 5, 再次刷新界面
                invalidate();
            }
        }
    }
