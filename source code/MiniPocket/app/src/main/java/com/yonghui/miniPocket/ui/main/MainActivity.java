package com.yonghui.miniPocket.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.Toast;

import com.yonghui.miniPocket.R;


public class MainActivity extends FragmentActivity {
    private Fragment mMainFragment, mCountFragment, currentFragment;
    private long lastPressTime;
    private RadioButton mRbMain, mRbCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initView();
        setListener();

    }

    private void initView() {
        mRbCount = (RadioButton) findViewById(R.id.main_rb_count);
        mRbMain = (RadioButton) findViewById(R.id.main_rb_main);

        mCountFragment = new AccountFragment();
        mMainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.mainactivity_fl_space, mMainFragment).commit();
        currentFragment = mMainFragment;
    }

    private void setListener() {
        mRbCount.setOnClickListener(mListener);
        mRbMain.setOnClickListener(mListener);
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == null) return;
            switch (v.getId()) {
                case R.id.main_rb_count:
                    addOrShowFragment(mCountFragment);
                    break;
                case R.id.main_rb_main:
                    addOrShowFragment(mMainFragment);
                    break;

                default:
                    break;
            }
        }
    };


    @Override
    public void onBackPressed() {
        long ct = System.currentTimeMillis();
        if (ct - lastPressTime <= 2000) {
            finish();
        } else {
            Toast.makeText(this, "Press once again to quit", Toast.LENGTH_SHORT).show();
            lastPressTime = ct;
        }
    }




    private void addOrShowFragment(Fragment fragment) {
        if (currentFragment == fragment)
            return;
        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().hide(currentFragment).add(R.id.mainactivity_fl_space, fragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
    }
}
