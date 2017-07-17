package com.yonghui.miniPocket.ui.selectclass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yonghui.miniPocket.R;

public class SelectClassActivity extends AppCompatActivity {
    private ListView mLv, mLv_Right;
    private LeftAdapter mAdapter;
    private int mPosition = -1;
    private String[] class_left = new String[]{"clothes", "Food", "Transport", "Furnishing", "electronic communication",
            "entertainment", "study", "medical", "Others"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purpose_item);
        init();
        setListener();
    }

    private void init() {
        mLv = (ListView) findViewById(R.id.class_lv_left);
        mAdapter = new LeftAdapter();
        mLv.setAdapter(mAdapter);
    }

    private void setListener() {
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                mAdapter.setmPosition(position);
                mAdapter.notifyDataSetChanged();
                Intent intent = getIntent();
                intent.putExtra("msgclass",/* class_left[mPosition] + ">" + */class_left[mPosition]);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

    }

    class LeftAdapter extends BaseAdapter {
        private int mPosition = -1;

        @Override
        public int getCount() {
            return class_left.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv;
            if (convertView == null) {
                convertView = LayoutInflater.from(SelectClassActivity.this).inflate(R.layout.item_tv, null);
                tv = (TextView) convertView.findViewById(R.id.item_tv_tv);
                convertView.setTag(tv);
            } else {
                tv = (TextView) convertView.getTag();
            }
            tv.setText(class_left[position]);
            if (mPosition == position) {
                convertView.setBackgroundColor(SelectClassActivity.this.getResources().getColor(R.color.background_activity));
            } else {
                convertView.setBackgroundColor(getResources().getColor(R.color.item_down));
            }
            return convertView;
        }

        public void setmPosition(int mPosition) {
            this.mPosition = mPosition;
        }
    }

    class RightAdapter extends BaseAdapter {
        private String[] data;
        private int[] resouce;

        public RightAdapter(String[] data, int[] resouce) {
            this.data = data;
            this.resouce = resouce;
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHoler vh;
            if (convertView == null) {
                vh = new ViewHoler();
                convertView = LayoutInflater.from(SelectClassActivity.this).inflate(R.layout.lv_right_item, null);
                vh.img = (ImageView) convertView.findViewById(R.id.item_right_img);
                vh.tv = (TextView) convertView.findViewById(R.id.item_right_tv);
                convertView.setTag(vh);
            } else {
                vh = (ViewHoler) convertView.getTag();
            }
            vh.tv.setText(data[position]);
            vh.img.setImageResource(resouce[position]);
            return convertView;
        }

        class ViewHoler {
            TextView tv;
            ImageView img;
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
     //   MobclickAgent.onPause(this);
    }
}
