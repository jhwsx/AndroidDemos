package com.wzc.toolbardemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * 2017年8月28日20:40:46
 * http://www.jianshu.com/p/ae0013a4f71a
 * http://www.jianshu.com/p/b3a40a55826e
 * https://developer.android.com/training/appbar/setting-up.html#add-toolbar
 */
public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // 设置标题
        toolbar.setTitle("Title");
        // 设置子标题
        toolbar.setSubtitle("subtitle");
        // 设置logo
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setNavigationIcon(R.drawable.ic_navigation_icon);
        // 让Toolbar即能取代原本的 actionbar
        setSupportActionBar(toolbar);
        // 设置导航图标
//        toolbar.setNavigationIcon(R.drawable.ic_navigation_icon);
        // 设置菜单条目点击监听事件
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 添加菜单
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // 获取SearchView
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        return true; // 返回true表示显示菜单
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(mContext, "Search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(mContext, "Settings", Toast.LENGTH_SHORT).show();
                break;
        }
        return true; // 返回true,表示事件被处理了
    }
}
