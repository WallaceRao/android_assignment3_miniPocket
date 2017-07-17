package com.yonghui.miniPocket.utils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.yonghui.miniPocket.R;

public class DialogUtils {
    public static void show(Context context, String alert, final DialogCallBack callBack) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Prompt")
                .setIcon(context.getResources().getDrawable(R.drawable.app_icon))
                .setMessage(alert)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.doListener();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    public interface DialogCallBack {
        void doListener();
    }
}
