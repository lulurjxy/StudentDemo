
package com.example.studentdemo.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.studentdemo.R;


public abstract class BaseFragment extends Fragment implements OnClickListener {
    protected String[] mCarIds;
    protected String mBasURL;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCarIds = getResources().getStringArray(R.array.car_id);
        getIP();
        activityCreated();
        initData();
    }

    private void getIP() {
        SharedPreferences sh = getActivity().getSharedPreferences("ipset", 0);
        mBasURL = "http://" + sh.getString("ip", getString(R.string.default_ip)) + ":" + 8080
                + "/transportservice/type/jason/action/";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), null);
    }

    protected abstract void activityCreated();

    protected abstract void initData();

    protected abstract int getLayoutId();

}
