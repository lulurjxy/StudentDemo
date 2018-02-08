
package com.example.studentdemo.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DialogFactory {
    static Dialog dialog = null;

    public interface OnListener {
        void onAfter();
    }

    public static void showDialog(Context context, String title, String msg,
            final OnListener oAfter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                oAfter.onAfter();
            }
        });
        dialog = builder.create();
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }
}
