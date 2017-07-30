package com.wzc.gesturedetectordemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * 学习目标:
 * 1,GestureDetector.OnGestureListener各个方法的含义是什么?
 * 2,如何使用GestureDetector
 */
public class OnGestureListenerActivity extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = "MyGesture";

    private GestureDetector mGestureDetector;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_gesture_listener);
        // 2, 创建GestureDetector实例mGestureDetector
        mGestureDetector = new GestureDetector(this, new MyOnGestureListenerImpl());
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

    // 1, 创建GestureDetector.OnGestureListener接口的实现类
    private class MyOnGestureListenerImpl implements GestureDetector.OnGestureListener {
        // 用户轻触触摸屏,由一个MotionEvent.ACTION_DOWN触发
        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG, "onDown e.getAction() == " + e.getAction());
            return false;
        }
        // 用户轻触触摸屏,但并没有移动或抬起,也是由一个down事件触发的
        // 与onDown()的异同点:
        // 同: 都是由down事件触发的
        // 异:
        // onDown一定会回调,onShowPress却不一定会回调(比如用户迅速触摸屏幕后离开时)
        //
        @Override
        public void onShowPress(MotionEvent e) {
            Log.d(TAG, "onShowPress e.getAction() == " + e.getAction());
        }
        // 用户轻触触摸屏抬起时触发,由一个up事件触发的
        // 只有在轻击一下屏幕,迅速抬起来,才会触发本方法
        // 如果包含了其他的操作,就不会触发本方法,比如(多个down时,不会触发;包含了move时,不会触发)
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d(TAG, "onSingleTapUp e.getAction() == " + e.getAction());
            return false;
        }
        // 用户按下触摸屏并拖动时触发,由一个down和多个move触发
        // 这个方法会多次触发
        // 参数含义:
        // 参一: 是第一个down事件
        // 参二: 是一个move事件,触发当前onScroll的那个move事件
        // 参三: 相邻两次调用onScroll时,在x方向上移动的距离,向左为正,向右为负
        // 参四: 相邻两次调用onScroll时,在y方向上移动的距离,向上为正,向下为负
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "onScroll e1 = " + e1.getAction() + ", e2 = " + e2.getAction() + ", distanceX = " + distanceX + ", distanceY = " + distanceY);
            return false;
        }
        // 用户长按触摸屏,由多个down触发
        @Override
        public void onLongPress(MotionEvent e) {
            Log.d(TAG, "onLongPress e.getAction() == " + e.getAction());
        }
        // 用户按下触摸屏,快速移动后松开,一个down,多个move和一个up触发
        // 如果用户不是快速移动后松开,则不会触发
        // 参数含义:
        // 参一:e1 是第一个down事件
        // 参二:e2 是一个up事件
        // 参三: velocityX：X轴上的移动速度，像素/秒,左为负,右为正
        // 参四: velocityY：Y轴上的移动速度，像素/秒,上为负,下为正
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(TAG, "onFling e1 = " + e1.getAction() + ", e2 = " + e2.getAction() + ", velocityX = " + velocityX + ", velocityY = " + velocityY);
            return false;
        }
    }
}
