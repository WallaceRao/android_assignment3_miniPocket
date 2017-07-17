package com.yonghui.miniPocket.ui.accountDetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yonghui.miniPocket.R;
import com.yonghui.miniPocket.bean.MsgDay;
import com.yonghui.miniPocket.db.SqliteManage;
import com.yonghui.miniPocket.utils.OrderUtils;


import java.util.ArrayList;
import java.util.List;

public class CountDetialActivity extends AppCompatActivity {
    private ListView mLv;
    private List<MsgDay> mData;
    private String mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_detail);
        initView();
        initData();
    }

    private void initView() {
        mCount = getIntent().getStringExtra("count");
        mLv = (ListView) findViewById(R.id.countdetial_lv_detial);
        ((TextView) findViewById(R.id.baseactivity_tv_title)).setText(mCount + " Records");
        ((ImageButton) findViewById(R.id.baseactivity_ib_ok)).setVisibility(View.GONE);
        ((ImageButton) findViewById(R.id.baseactivity_ib_return)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        mData = new ArrayList<>();
        SqliteManage.QueryResult result = SqliteManage.getInstance(this).query
                ("inout", "count=?", new String[]{mCount});
        while (result.cursor.moveToNext()) {
            MsgDay msg = new MsgDay(result.cursor);
            mData.add(msg);
        }
        OrderUtils.orderDay(mData);
        if (mData.size() == 0) {
            Toast.makeText(CountDetialActivity.this, "No record found", Toast.LENGTH_SHORT).show();
        }
        CountDetialAdapter adapter = new CountDetialAdapter(mData, this);
        mLv.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }
}
