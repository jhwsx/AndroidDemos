package com.wzc.gesturedetectordemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * 2017年7月30日20:08:25
 * 学习目标: 1,学习SimpleOnGestureListener类,是接口的空实现.
 */
public class SimpleOnGestureListenerActivity extends AppCompatActivity implements View.OnTouchListener{

    private GestureDetector mGestureDetector;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_on_gesture_listener);
        // 2,创建GestureDetector实例
        mGestureDetector = new GestureDetector(this, new MySimpleOnGestureListener());
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
    // 1, 创建SimpleOnGestureListener类的继承类
    private class MySimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("MyGesture", " e1.getX() == " + e1.getX() + ",  e2.getX() == " + e2.getX() + ", velocityX == " + velocityX + ", velocityY == " + velocityY);
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
