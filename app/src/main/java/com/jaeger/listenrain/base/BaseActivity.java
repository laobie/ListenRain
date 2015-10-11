package com.jaeger.listenrain.base;


import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jaeger.listenrain.R;

/**
 * Created by Jaeger on 15/9/15.
 * ListenRain
 */
public class BaseActivity extends AbstractBaseActivity implements View.OnClickListener {

    @Override
    protected void initIntentParam(Intent intent) {

    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setViewStatus() {

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBarColor();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    protected void showBack() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && actionBar.isShowing()) {
            actionBar.setTitle(title);
        }
    }

    protected void showToastS(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    protected void showToastL(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 默认是不需要重写该方法
     * 当需要设置不同的状态栏颜色时，重写改方法就行
     */
    protected void setStatusBarColor() {
        translucentBar(R.color.colorPrimary);
    }


    /**
     * 状态栏变色处理 4.4以上
     *
     * @param color 状态栏颜色
     *              4.4状态栏显示为改颜色
     *              5.0自动会加上半透明效果
     */
    public void translucentBar(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 获取状态栏高度
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            // 绘制一个和状态栏一样高的矩形，并添加到视图中
            View rectView = new View(this);
            LinearLayout.LayoutParams params
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            rectView.setLayoutParams(params);
            rectView.setBackgroundColor(getResources().getColor(color));
            // 添加矩形View到布局中
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            decorView.addView(rectView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
