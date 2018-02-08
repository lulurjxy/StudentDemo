
package com.example.studentdemo.utils;

import android.content.Context;
import android.widget.Toast;

public class MyToast {
    public static void getToast(Context context, String text) {
        Toast.makeText(context, text, 0).show();
    }
    public static void getToastLong(Context context, String text) {
        Toast.makeText(context, text, 2).show();
    }
}
