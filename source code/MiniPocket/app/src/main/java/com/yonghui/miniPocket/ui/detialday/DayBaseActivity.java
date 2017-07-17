package com.yonghui.miniPocket.ui.detialday;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yonghui.miniPocket.R;
import com.yonghui.miniPocket.bean.MsgDay;
import com.yonghui.miniPocket.db.SqliteManage;


import java.util.ArrayList;
import java.util.List;

public class DayBaseActivity extends AppCompatActivity {
    protected List<MsgDay> mMsgDayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initParentData();

    }

    protected void initParentData() {
        mMsgDayList = new ArrayList<>();
        SqliteManage.QueryResult result = SqliteManage.getInstance(this).query("inout", null, null);
        while (result.cursor.moveToNext()) {
            MsgDay msgDay = new MsgDay(
                    result.cursor
            );
            mMsgDayList.add(msgDay);
        }
        ((ImageView) findViewById(R.id.dayactivity_ib_return)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (mMsgDayList.size() == 0) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
       // MobclickAgent.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }
}
