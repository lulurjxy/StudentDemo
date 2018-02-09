
package com.example.studentdemo.utils;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.studentdemo.R;

/**
 * spinner初始化工具
 */
public class SpinnerFactory {
    public interface OnItemSelected {
        void onSelected(int position);
    }

    public static void getSpinner(Context context, Spinner spinner, String[] datas,
                                  final OnItemSelected listener) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.item_spinner, datas);
        adapter.setDropDownViewResource(R.layout.item_spinner);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.onSelected(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

}
