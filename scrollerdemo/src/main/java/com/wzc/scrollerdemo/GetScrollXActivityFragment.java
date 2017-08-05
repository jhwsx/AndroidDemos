package com.wzc.scrollerdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 2017年7月29日17:14:20
 * 学习目标: 学习getScrollX()获取到的值的含义
 * 幕布就是我们想要显示在屏幕上的内容.
 * getScrollX()获取到的值是幕布在窗口左边界的值,这个在初始化时是0,当向右拖动幕布时,为负值,当向左拖动幕布时,为正值.
 * getScrollX()记录了幕布相对于初始化位置的推动的距离,向右拖动时,为负值,向左拖动时,为正值.
 */
public class GetScrollXActivityFragment extends Fragment {

    public GetScrollXActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_get_scroll_x, container, false);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.imageView);
        ImageView imageView2 = (ImageView) inflate.findViewById(R.id.imageView2);
        ImageView imageView3 = (ImageView) inflate.findViewById(R.id.imageView3);
        TextView textView = (TextView) inflate.findViewById(R.id.textView);
        TextView textView2 = (TextView) inflate.findViewById(R.id.textView2);
        TextView textView3 = (TextView) inflate.findViewById(R.id.textView3);
        textView.setText("getScrollX() = " + imageView.getScrollX());
        imageView2.scrollTo(-100, 0);
        textView2.setText("getScrollX() = " + imageView2.getScrollX());
        imageView3.scrollTo(100, 0);
        textView3.setText("getScrollX() = " + imageView3.getScrollX());
        return inflate;
    }
}
