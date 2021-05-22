package com.mine.projet;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class startApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this, appimageconf.getImagePipelineConfig(this));
    }
}
