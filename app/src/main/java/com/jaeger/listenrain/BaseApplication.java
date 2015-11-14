package com.jaeger.listenrain;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Jaeger on 15/9/29.
 * ListenRain
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        AVOSCloud.initialize(this, "39GbpyDQQ3aqNOwrrAcVwzNP", "0lz3QXUthcqm8EKUulT6Y9CT");
    }
}
