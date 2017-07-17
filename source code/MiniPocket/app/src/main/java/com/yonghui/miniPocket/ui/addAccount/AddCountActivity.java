package com.yonghui.miniPocket.ui.addAccount;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yonghui.miniPocket.R;
import com.yonghui.miniPocket.db.SqliteManage;
import com.yonghui.miniPocket.ui.input.InputActivity;

public class AddCountActivity extends AppCompatActivity {
    private EditText mEt;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account);
        initView();

    }

    private void initView() {
        mEt = (EditText) findViewById(R.id.countadd_et_name);
        mTv = (TextView) findViewById(R.id.countadd_tv_count);
    }

    public void onClick(View v) {
        if (v == null) return;
        switch (v.getId()) {
            case R.id.countadd_ib_return:
                finish();
                break;
            case R.id.countadd_ll_count:
                Intent intent = new Intent(this, InputActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.countadd_ib_add:
                if (mEt.getText().toString().length() > 1 && !mTv.getText().toString().equals("00.00")) {
                    addCount();
                } else if (mTv.getText().toString().equals("00.00")) {
                    Toast.makeText(AddCountActivity.this, "amount can't be 0", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(AddCountActivity.this, "please specify a name", Toast.LENGTH_SHORT).show();
                    return;
                }
                finish();
                break;
            default:
                break;
        }
    }

    private void addCount() {
        if (SqliteManage.getInstance(this).isExitInTable("count", "count=?", new String[]{mEt.getText().toString()})) {
            Toast.makeText(this, "the account has existed, please try another name", Toast.LENGTH_SHORT).show();
        } else {
            ContentValues values = new ContentValues();
            values.put("count", mEt.getText().toString());
            values.put("money", mTv.getText().toString());
            SqliteManage.getInstance(this).insertItem("count", values);
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            mTv.setText(data.getStringExtra("msgmoney"));
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
     //   MobclickAgent.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
     //   MobclickAgent.onPause(this);
    }
}
