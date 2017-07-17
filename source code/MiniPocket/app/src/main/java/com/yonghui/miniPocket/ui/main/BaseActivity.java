package com.yonghui.miniPocket.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yonghui.miniPocket.R;

/**
 * Created by Yonghui Rao
 */
public class BaseActivity extends Activity {
    protected ImageButton mReturn;
    protected TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParentView();
        setParentListener();
    }

    protected void initParentView() {
        mReturn = (ImageButton) findViewById(R.id.baseactivity_ib_return);
        mTitle = (TextView) findViewById(R.id.baseactivity_tv_title);
    }

    protected void setParentListener() {
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
