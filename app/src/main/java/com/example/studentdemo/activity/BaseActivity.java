
package com.example.studentdemo.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studentdemo.R;


public abstract class BaseActivity extends FragmentActivity implements OnClickListener {
    protected TextView mTitleTV;
    protected String mBasURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        getIP();
        initView();
        initData();
        setTitleLayout();
    }

    private void getIP() {
        SharedPreferences sh = getSharedPreferences("ipset", 0);
        mBasURL = "http://" + sh.getString("ip", "192.168.1.133") + ":" + 8080
                + "/transportservice/type/jason/action/";
    }

    private void setTitleLayout() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.titlelayout);
        ImageView imageView = (ImageView) layout.findViewById(R.id.back);
        mTitleTV = (TextView) layout.findViewById(R.id.title);
        mTitleTV.setText(getLayoutTitle());
        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onAfter();
            }
        });
    }

    protected abstract String getLayoutTitle();

    protected abstract void onAfter();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayoutId();
}
