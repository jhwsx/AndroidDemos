package com.wzc.scrollerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * 2017年7月29日13:51:43
 * 学习目标: 使用Scroller来实现一个Viewpager的例子
 * 内容: 学习了使用Scroller的步骤,如果忘记了可以看下Scroller的类注释说明
 *
 */
public class ScrollerViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_view_pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
