package com.example.wakelocknotification.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.example.wakelocknotification.config.SystemParams;

import butterknife.ButterKnife;


public abstract class BaseActivity extends FragmentActivity {
    protected SystemParams systemParams;

    protected Context context;
    protected Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(getLayoutResID());
        ButterKnife.bind(this);
        context = getApplicationContext();
        activity =this;
        systemParams = SystemParams.getInstance();

        initView();
    }


    protected abstract void initView();

    public abstract int getLayoutResID();

    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(this, targetActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void openActivity(Class<?> targetActivityClass) {
        openActivity(targetActivityClass, null);
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass, Bundle bundle) {
        openActivity(targetActivityClass, bundle);
        this.finish();
        ActivityCollector.removeActivity(this);
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass) {
        openActivity(targetActivityClass);
        this.finish();
        ActivityCollector.removeActivity(this);
    }

    public void openActivityAndCloseThis(Intent intent) {
        startActivity(intent);
        this.finish();
        ActivityCollector.removeActivity(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
