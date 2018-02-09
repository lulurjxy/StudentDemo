
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

/**
 * BaseActivity 所有Activity都是继承次类
 */
public abstract class BaseActivity extends FragmentActivity implements OnClickListener {
    /**
     * 标题信息
     */
    protected TextView mTitleTV;
    /**
     * 服务器地址
     */
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

    /**
     * 获取ip
     */
    private void getIP() {
        SharedPreferences sh = getSharedPreferences("ipset", 0);
        mBasURL = "http://" + sh.getString("ip", getString(R.string.default_ip)) + ":" + 8080
                + "/transportservice/type/jason/action/";
    }

    /**
     * 设置页面标题栏
     */
    private void setTitleLayout() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.titlelayout);
        ImageView imageView = (ImageView) layout.findViewById(R.id.back);//返回图标
        mTitleTV = (TextView) layout.findViewById(R.id.title);//标题栏
        mTitleTV.setText(getLayoutTitle());//标题栏设置页面标题
        imageView.setOnClickListener(new OnClickListener() {//返回图标点击监听

            @Override
            public void onClick(View v) {
                onAfter();
            }
        });
    }

    /**
     * @return 页面标题
     */
    protected abstract String getLayoutTitle();

    /**
     * 点击返回图标处理事件
     */
    protected abstract void onAfter();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * @return 对应的布局文件id
     */
    protected abstract int getLayoutId();
}
