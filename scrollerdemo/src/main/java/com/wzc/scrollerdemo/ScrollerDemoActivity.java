package com.wzc.scrollerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by wzc on 2017/8/26.
 */

public class ScrollerDemoActivity extends AppCompatActivity {

    private Button mButton;
    private ScrollerDemoLinearLayout mScrollerDemoLinearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_demo);
        mButton = (Button) findViewById(R.id.button6);
        mScrollerDemoLinearLayout = (ScrollerDemoLinearLayout) findViewById(R.id.scrollerdemolinearlayout);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollerDemoLinearLayout.startScroll();
            }
        });
    }


}
