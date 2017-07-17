package com.yonghui.miniPocket.ui.record;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yonghui.miniPocket.R;
import com.yonghui.miniPocket.db.SqliteManage;
import com.yonghui.miniPocket.ui.input.InputActivity;
import com.yonghui.miniPocket.ui.main.BaseActivity;
import com.yonghui.miniPocket.ui.selectclass.SelectClassActivity;
import com.yonghui.miniPocket.ui.selectcount.SelectAccountActivity;
import com.yonghui.miniPocket.utils.SqliteUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RecordActivity extends BaseActivity {
    private Calendar mCalendar;
    private LinearLayout mLlMoney, mLlClass, mLlCount, mLlTime, mLlInOrOut;
    private TextView mTvMoney, mTvClass, mTvCount, mTvTime, mTvInOrOut;
    private ImageButton mBtOk;
    private EditText mOther;
    private int mYear, mMonth, mDay, mWeek;
    private int isOut = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_record);
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        mCalendar = Calendar.getInstance(Locale.ENGLISH);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
    }

    private void initView() {
        ((TextView) findViewById(R.id.baseactivity_tv_title)).setText("Add deposit/pay item");
        mLlMoney = (LinearLayout) findViewById(R.id.recordin_ll_money);
        mLlClass = (LinearLayout) findViewById(R.id.record_ll_class);
        mLlCount = (LinearLayout) findViewById(R.id.record_ll_count);
        mLlTime = (LinearLayout) findViewById(R.id.record_ll_time);
        mLlInOrOut = (LinearLayout) findViewById(R.id.recordin_ll_inorout);
        mTvCount = (TextView) findViewById(R.id.record_tv_count);
        mTvMoney = (TextView) findViewById(R.id.record_tv_money);
        mTvClass = (TextView) findViewById(R.id.record_tv_class);
        mTvTime = (TextView) findViewById(R.id.record_tv_time);
        mTvTime.setText(mYear + "/" + mMonth + "/" + mDay);
        mTvInOrOut = (TextView) findViewById(R.id.record_tv_inorout);
        mTvInOrOut.setText("Pay");
        mBtOk = (ImageButton) findViewById(R.id.baseactivity_ib_ok);
        mOther = (EditText) findViewById(R.id.record_et_note);
        mTvMoney.setTextColor(getResources().getColor(R.color.text_out_color));
        mLlClass.setOnClickListener(mListener);
        mLlMoney.setOnClickListener(mListener);
        mLlCount.setOnClickListener(mListener);
        mLlTime.setOnClickListener(mListener);
        mLlInOrOut.setOnClickListener(mListener);
        mBtOk.setOnClickListener(mListener);
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == null) return;
            Intent intent;
            switch (v.getId()) {
                case R.id.recordin_ll_money:
                    startActivityForResult(new Intent(RecordActivity.this, InputActivity.class), 0);
                    break;
                case R.id.record_ll_class:
                    intent = new Intent(RecordActivity.this, SelectClassActivity.class);
                    if (isOut == -1) {
                        intent.putExtra("flag", 1);
                        startActivityForResult(intent, 1);
                    } else {
                        if ("Salary income".equals(mTvClass.getText())) {
                            mTvClass.setText("Other income");
                        } else {
                            mTvClass.setText("Salary income");
                        }
                    }
                    break;
                case R.id.record_ll_count:
                    intent = new Intent(RecordActivity.this, SelectAccountActivity.class);
                    startActivityForResult(intent, 2);
                    break;
                case R.id.record_ll_time:
                    new DatePickerDialog(RecordActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {

                            mYear = year;
                            mMonth = monthOfYear + 1;
                            mDay = dayOfMonth;
                            Calendar c = Calendar.getInstance(Locale.CHINA);
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                c.setTime(format.parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth));
                                mWeek = c.get(Calendar.DAY_OF_WEEK);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            mTvTime.setText("Year" + mYear + "Month" + mMonth + "Day" + mDay);
                        }
                    },
                            mCalendar.get(Calendar.YEAR),
                            mCalendar.get(Calendar.MONTH),
                            mCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    break;
                case R.id.recordin_ll_inorout:
                    isOut = -1 * isOut;
                    if (isOut == -1) {
                        mTvInOrOut.setText("Outcome");
                        mTvClass.setText("Food");
                        mTvMoney.setTextColor(getResources().getColor(R.color.text_out_color));
                    } else {
                        mTvInOrOut.setText("Income");
                        mTvClass.setText("Salary");
                        mTvMoney.setTextColor(getResources().getColor(R.color.text_in_color));
                    }
                    break;
                case R.id.baseactivity_ib_ok:
                    if (mTvCount.getText().toString().length() == 0) {
                        Toast.makeText(RecordActivity.this, "Please select an account！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ("00.00".equals(mTvMoney.getText().toString())) {
                        Toast.makeText(RecordActivity.this, "Amount can't be 0", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        insertData();
                    }
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    private void insertData() {
        ContentValues values = new ContentValues();
        values.put("year", mYear);
        values.put("month", mMonth);
        values.put("day", mDay);
        values.put("week", mWeek);
        values.put("money", mTvMoney.getText().toString());
        values.put("inout", isOut);
        values.put("class", mTvClass.getText().toString());
        values.put("count", mTvCount.getText().toString());
        values.put("time", System.currentTimeMillis() + "");
        values.put("other", mOther.getText().toString());
        SqliteManage.getInstance(this).insertItem("inout", values);

        SqliteUtils.updata(RecordActivity.this, mTvCount.getText().toString(),
                isOut * Double.parseDouble(mTvMoney.getText().toString()));
        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    String msgmoney = data.getStringExtra("msgmoney");
                    if (msgmoney != null && msgmoney.length() != 0)
                        mTvMoney.setText(msgmoney);
                    break;
                case 1:
                    mTvClass.setText(data.getStringExtra("msgclass"));
                    break;
                case 2:
                    mTvCount.setText(data.getStringExtra("msgcount"));
                    break;
                default:
                    break;
            }
        }
    }

}