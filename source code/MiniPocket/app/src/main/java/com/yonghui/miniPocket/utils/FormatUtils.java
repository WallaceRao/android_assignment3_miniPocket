package com.yonghui.miniPocket.utils;

import android.content.Context;
import android.widget.TextView;

import com.yonghui.miniPocket.R;

import java.text.DecimalFormat;

/**
 * Created by Yonghui Rao
 */
public class FormatUtils {
    public static String format2d(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }

    public static void setText(TextView tv, double values, Context context) {
        if (values > 0) {
            tv.setTextColor(context.getResources().getColor(R.color.text_in_color));
        } else {
            tv.setTextColor(context.getResources().getColor(R.color.text_out_color));
        }
        tv.setText(FormatUtils.format2d(values));
    }
}

