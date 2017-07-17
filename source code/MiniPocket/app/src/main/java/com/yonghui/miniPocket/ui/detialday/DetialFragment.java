package com.yonghui.miniPocket.ui.detialday;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yonghui.miniPocket.R;
import com.yonghui.miniPocket.bean.MsgDay;
import com.yonghui.miniPocket.utils.FormatUtils;

/**
 * Created by Yonghui Rao
 */
public class DetialFragment extends Fragment {
    private String[] weeks = new String[]{"Sunday", "Monday", "Thuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private MsgDay msgDay;
    private TextView mMoney, mClass, mCount, mTime, mWeek, mOther;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_daydetail, null);
        msgDay = (MsgDay) getArguments().getSerializable("msg");
        initFragmentView(view);
        return view;
    }

    private void initFragmentView(View view) {
        mMoney = (TextView) view.findViewById(R.id.dayactivity_tv_money);
        mClass = (TextView) view.findViewById(R.id.dayactivity_tv_class);
        mCount = (TextView) view.findViewById(R.id.dayactivity_tv_count);
        mTime = (TextView) view.findViewById(R.id.dayactivity_tv_time);
        mWeek = (TextView) view.findViewById(R.id.dayactivity_tv_week);
        mOther = (TextView) view.findViewById(R.id.dayactivity_tv_other);
        mMoney.setText(FormatUtils.format2d(msgDay.getMoney()));
        mClass.setText(msgDay.getClasses() + "");
        mCount.setText(msgDay.getCount() + "");
        mTime.setText(msgDay.getYear() + "/" + msgDay.getMonth() + "/" + msgDay.getDay());
        mWeek.setText(weeks[msgDay.getWeek() - 1]);
        mOther.setText(msgDay.getOther() + "");
        if (msgDay.getInout() == -1) {
            mMoney.setTextColor(getResources().getColor(R.color.text_out_color));
        } else {
            mMoney.setTextColor(getResources().getColor(R.color.text_in_color));
        }
    }
}
