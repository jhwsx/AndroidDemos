package com.wzc.scrollerdemo;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

/**
 * 2017年7月29日11:52:53
 * 目标: 学习View类的scrollTo()和scrollBy()方法
 * 区别: 与Scroller的区别是,View类的这两个方法实现的效果是跳跃式的,而Scroller的是平滑滚动的效果.
 */
public class ScrollDemoActivity extends AppCompatActivity {

    private Button mBtnScrollto;
    private Button mBtnScrollby;
    private ConstraintLayout mConstraintLayout;
    private Button mBtnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_demo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.layout);
        mBtnScrollto = (Button) findViewById(R.id.btn_scrollto);
        mBtnScrollby = (Button) findViewById(R.id.btn_scrollby);
        mBtnReset = (Button) findViewById(R.id.btn_reset);
        mBtnScrollto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 注意:滚动的是view内部的内容
                // 参数一:x方向上相对于初始位置移动某段距离,正值表示向左,负值表示向右
                // 参数二:y方向上相对于初始位置移动某段距离,正值表示向上,负值表示向下
                mConstraintLayout.scrollTo(-60, -100);
            }
        });

        mBtnScrollby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConstraintLayout.scrollBy(-60, -100);
            }
        });

        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConstraintLayout.scrollTo(0, 0);
            }
        });
    }

}
