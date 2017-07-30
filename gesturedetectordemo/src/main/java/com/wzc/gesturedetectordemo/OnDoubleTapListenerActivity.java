package com.wzc.gesturedetectordemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * 2017年7月30日20:07:49
 * 学习目标:
 * 1,学习OnDoubleTapListener接口的三个方法的含义
 */
public class OnDoubleTapListenerActivity extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = "MyGesture";

    private GestureDetector mGestureDetector;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_double_tap_listener);
        // 2,创建GestureDetector实例
        mGestureDetector = new GestureDetector(this, new OnGestureListenerImpl());
        // 3,设置双击监听器
        mGestureDetector.setOnDoubleTapListener(new MyOnDoubleTapListenerImpl());
        mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setOnTouchListener(this);
        mTextView.setFocusable(true);
        mTextView.setClickable(true);
        mTextView.setLongClickable(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 3, onTouch(View v, MotionEvent event)中拦截
        return mGestureDetector.onTouchEvent(event);
    }

    // 1, 创建OnDoubleTapListener接口的实现类
    private class MyOnDoubleTapListenerImpl implements GestureDetector.OnDoubleTapListener {
        // 单击事件触发顺序
//                07-31 23:22:44.549 20436-20436/com.wzc.gesturedetectordemo D/MyGesture: onDown e.getAction() == 0
//                07-31 23:22:44.640 20436-20436/com.wzc.gesturedetectordemo D/MyGesture: onSingleTapUp e.getAction() == 1
//                07-31 23:22:44.850 20436-20436/com.wzc.gesturedetectordemo D/MyGesture: onSingleTapConfirmed e == 0
        // 双击事件触发顺序
//                07-31 23:23:53.442 20436-20436/com.wzc.gesturedetectordemo D/MyGesture: onDown e.getAction() == 0
//                07-31 23:23:53.482 20436-20436/com.wzc.gesturedetectordemo D/MyGesture: onSingleTapUp e.getAction() == 1
//                07-31 23:23:53.589 20436-20436/com.wzc.gesturedetectordemo D/MyGesture: onDoubleTap e == 0
//                07-31 23:23:53.589 20436-20436/com.wzc.gesturedetectordemo D/MyGesture: onDoubleTapEvent e == 0
//                07-31 23:23:53.589 20436-20436/com.wzc.gesturedetectordemo D/MyGesture: onDown e.getAction() == 0
//                07-31 23:23:53.624 20436-20436/com.wzc.gesturedetectordemo D/MyGesture: onDoubleTapEvent e == 2
//                07-31 23:23:53.641 20436-20436/com.wzc.gesturedetectordemo D/MyGesture: onDoubleTapEvent e == 2
//                07-31 23:23:53.653 20436-20436/com.wzc.gesturedetectordemo D/MyGesture: onDoubleTapEvent e == 1


        // 当单次点击时触发该方法
        // 与onSingleTapUp()方法的区别:
        // 在是双击时,onSingleTapUp()方法也会执行
        // 在是双击时,onSingleTapConfirmed()方法不会执行
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d(TAG, "onSingleTapConfirmed e == " + e.getAction());
            return false;
        }

        // 双击执行
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d(TAG, "onDoubleTap e == " + e.getAction());
            return false;
        }

        // 双击间隔中发生的动作
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.d(TAG, "onDoubleTapEvent e == " + e.getAction());
            return false;
        }
    }

    private class OnGestureListenerImpl implements GestureDetector.OnGestureListener {


        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG, "onDown e.getAction() == " + e.getAction());
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d(TAG, "onShowPress e.getAction() == " + e.getAction());
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d(TAG, "onSingleTapUp e.getAction() == " + e.getAction());
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "onScroll e1 = " + e1.getAction() + ", e2 = " + e2.getAction() + ", distanceX = " + distanceX + ", distanceY = " + distanceY);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d(TAG, "onLongPress e.getAction() == " + e.getAction());
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(TAG, "onFling e1 = " + e1.getAction() + ", e2 = " + e2.getAction() + ", velocityX = " + velocityX + ", velocityY = " + velocityY);
            return false;
        }
    }
}
