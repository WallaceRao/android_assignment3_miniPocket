package com.yonghui.miniPocket.ui.accountDetail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonghui.miniPocket.R;
import com.yonghui.miniPocket.bean.MsgDay;
import com.yonghui.miniPocket.utils.FormatUtils;

import java.util.List;

/**
 * Created by Yonghui Rao
 */
public class CountDetialAdapter extends BaseAdapter {
    private List<MsgDay> mData;
    private Context mContext;

    public CountDetialAdapter(List<MsgDay> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
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
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LinearLayout.inflate(mContext, R.layout.item_count_detial, null);
            vh.tv_day = (TextView) convertView.findViewById(R.id.item_count_detial_day);
            vh.tv_class = (TextView) convertView.findViewById(R.id.item_count_detial_class);
            vh.tv_money = (TextView) convertView.findViewById(R.id.item_count_detial_money);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        MsgDay msgDay = mData.get(position);
        vh.tv_day.setText(msgDay.getYear() + "/" + msgDay.getMonth() + "/" + msgDay.getDay());
        vh.tv_class.setText(msgDay.getClasses());
        FormatUtils.setText(vh.tv_money, msgDay.getInout() * msgDay.getMoney(), mContext);
        return convertView;
    }

    class ViewHolder {
        TextView tv_day, tv_class, tv_money;
    }
}
