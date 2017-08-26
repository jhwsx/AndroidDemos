package com.wzc.scrollerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Created by wzc on 2017/8/26.
 */

public class ScrollerViewPager1Activity extends AppCompatActivity {

    private ScrollerViewPager1 mScrollerViewPager1;
    private int[] resIds = {
            R.drawable.ic_pic1,
            R.drawable.ic_pic2,
            R.drawable.ic_pic3,
            R.drawable.ic_pic4,
            R.drawable.ic_pic5,
            R.drawable.ic_pic6,
            R.drawable.ic_pic7,
            R.drawable.ic_pic8,
            R.drawable.ic_pic9,
            R.drawable.ic_pic10,
            R.drawable.ic_pic11,
            R.drawable.ic_pic12,
            R.drawable.ic_pic13,
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_view_pager_1);
        mScrollerViewPager1 = (ScrollerViewPager1) findViewById(R.id.viewpager);
        for (int i = 0; i < resIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(resIds[i]);
            imageView.setClickable(true);
            mScrollerViewPager1.addView(imageView);
        }
    }
}
