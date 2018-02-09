
package com.example.studentdemo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast 提示工具
 */
public class MyToast {
    public static void getToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void getToastLong(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
