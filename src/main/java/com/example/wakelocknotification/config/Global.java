package com.example.wakelocknotification.config;

import android.app.Application;
import android.content.Context;

public class Global extends Application {
    public static Global instance;

    public static Global getInstance() {
        return instance;
    }

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SystemParams.init(this);
        context = this.getApplicationContext();

    }

}
