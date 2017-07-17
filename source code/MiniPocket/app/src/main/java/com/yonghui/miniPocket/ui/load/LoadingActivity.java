package com.yonghui.miniPocket.ui.load;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.yonghui.miniPocket.R;
import com.yonghui.miniPocket.ui.main.MainActivity;

public class LoadingActivity extends Activity {
    public static final String VCONFNAME = "config.sys";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        initView();
        startWelcome();
    }

    private void initView() {

    }


    private void startWelcome() {
        Intent intent = new Intent();
        intent.setClass(LoadingActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

