package com.jaeger.listenrain.activity;

import android.content.Intent;
import android.os.Handler;

import com.jaeger.listenrain.R;
import com.jaeger.listenrain.base.BaseActivity;

/**
 * Created by Jaeger on 15/9/14.
 * ListeningRain
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void setViewStatus() {
        toNextPage();
    }

    private void toNextPage() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
