
package com.example.studentdemo.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.studentdemo.R;
import com.example.studentdemo.utils.MyToast;

import java.util.Locale;


/**
 * ip设置activity，包涵网络连接方式切换，IP设置
 */
public class IpSetActivity extends BaseActivity {
    private Button mSureBtn;
    private EditText mIP1ET;
    private EditText mIP2ET;
    private EditText mIP3ET;
    private EditText mIP4ET;
    /**
     * 切换网络请求方式http,mqtt;默认http
     */
    private Switch mSwitch;
    private SharedPreferences mSetIPPreferences;


    @Override
    protected String getLayoutTitle() {
        return getString(R.string.title_ip);
    }

    @Override
    protected void onAfter() {
        finish();
    }

    @Override
    protected void initData() {
        String[] ip = mSetIPPreferences.getString("ip", getString(R.string.default_ip)).split("\\.");
        if (ip.length >= 4) {
            mIP1ET.setText(String.format("%s", ip[0]));
            mIP2ET.setText(String.format("%s", ip[1]));
            mIP3ET.setText(String.format("%s", ip[2]));
            mIP4ET.setText(String.format("%s", ip[3]));
        }
    }

    @Override
    protected void initView() {
        mSetIPPreferences = getSharedPreferences("ipset", 0);
        mIP1ET = (EditText) findViewById(R.id.ip_et_ip0);
        mIP2ET = (EditText) findViewById(R.id.ip_et_ip1);
        mIP3ET = (EditText) findViewById(R.id.ip_et_ip2);
        mIP4ET = (EditText) findViewById(R.id.ip_et_ip3);
        mSureBtn = (Button) findViewById(R.id.ip_btn_sure);
        mSwitch = (Switch) findViewById(R.id.switch_connection_type);
        mSureBtn.setOnClickListener(this);
        mSwitch.setChecked(mSetIPPreferences.getBoolean("isHttp", true));
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mSetIPPreferences.edit().putBoolean("isHttp", b).apply();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ipset;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ip_btn_sure) {
            String string = handlString(mIP1ET) + "." + handlString(mIP2ET) + "."
                    + handlString(mIP3ET) + "." + handlString(mIP4ET);

            mSetIPPreferences.edit().putString("ip", string).apply();
            finish();
            MyToast.getToast(IpSetActivity.this, mSetIPPreferences.getString("ip", getString(R.string.default_ip)));
        }
    }

    private String handlString(EditText editText) {
        String string = editText.getText().toString().trim();
        if (string.equals("")) {
            String show = String.format(Locale.CHINESE, getResources().getString(R.string.handl), getResources().getString(R.string.handl_ip));
            MyToast.getToast(IpSetActivity.this, show);
            return "";
        }
        return string;
    }
}
