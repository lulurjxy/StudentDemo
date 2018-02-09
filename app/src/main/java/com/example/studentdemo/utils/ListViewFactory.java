
package com.example.studentdemo.utils;

import android.content.Context;
import android.text.TextPaint;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.example.studentdemo.R;
import com.example.studentdemo.adapter.ListAdapter;

import java.util.List;

/**
 * listview初始化工具
 */
public class ListViewFactory {
    public static ListAdapter getListView(Context context, LinearLayout layout,
                                          List<Object[]> datas,
                                          String[] titles) {
        LinearLayout tilteLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 45);
        // tilteLayout.setBackgroundResource(R.drawable.bold);
        tilteLayout.setLayoutParams(params);
        tilteLayout.setOrientation(LinearLayout.HORIZONTAL);
        for (int i = 0; i < titles.length; i++) {
            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(1,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(context);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            textView.setLayoutParams(textParams);
            textView.setText(titles[i]);
            textView.setTextSize(25);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.mipmap.bg_title);
            TextPaint textPaint = textView.getPaint();
            textPaint.setFakeBoldText(true);
            tilteLayout.addView(textView);

        }
        layout.addView(tilteLayout);
        ListView listView = new ListView(context);
        ListAdapter adapter = new ListAdapter(context, datas, titles.length);
        listView.setAdapter(adapter);
        layout.addView(listView);
        return adapter;
    }
}
