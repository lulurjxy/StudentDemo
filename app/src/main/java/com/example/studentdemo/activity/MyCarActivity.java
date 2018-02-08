
package com.example.studentdemo.activity;

import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.example.studentdemo.R;
import com.example.studentdemo.fragment.CarCondidtionFragment;
import com.example.studentdemo.fragment.CarControlFragment;


public class MyCarActivity extends BaseActivity {
    private FragmentTabHost mTabHost;

    @Override
    protected String getLayoutTitle() {
        return getString(R.string.control);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        addTab();
        setListener();
    }

    private void addTab() {
        String[] tag = {
                "control", "condition"
        };
        Class<?>[] cls = {
                CarControlFragment.class, CarCondidtionFragment.class
        };
        View[] views = {
                getTabView(R.string.control), getTabView(R.string.condition), getTabView(R.string.account)
        };
        for (int i = 0; i < tag.length; i++) {
            mTabHost.addTab(
                    mTabHost.newTabSpec(tag[i]).setIndicator(views[i]),
                    cls[i], null);
        }
    }

    private void setListener() {
        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("control")) {
                    mTitleTV.setText(R.string.control);
                } else if (tabId.equals("condition")) {
                    mTitleTV.setText(R.string.condition);
                }
            }
        });
    }

    private View getTabView(int resId) {
        View view = LayoutInflater.from(this).inflate(R.layout.textview_tab, null);
        TextView textView = (TextView) view.findViewById(R.id.content);
        textView.setText(resId);
        return view;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_car;
    }

    @Override
    protected void onAfter() {
        finish();
    }

    @Override
    public void onClick(View v) {

    }
}
