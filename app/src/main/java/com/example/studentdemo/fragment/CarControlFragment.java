
package com.example.studentdemo.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.studentdemo.R;
import com.example.studentdemo.httpRequest.BaseRequest;
import com.example.studentdemo.httpRequest.CarActionRequest;
import com.example.studentdemo.mqttRequest.BaseMqttRequest;
import com.example.studentdemo.mqttRequest.SetCarActionMqttRequest;
import com.example.studentdemo.utils.MyToast;
import com.example.studentdemo.utils.SpinnerFactory;

public class CarControlFragment extends BaseFragment {
    private Spinner mSpinner;
    private Switch mControlSh;
    private int carid = 0;
    private String[] actions = new String[]{
            "Stop", "Start"
    };
    private String action = "";
    private SharedPreferences sh;

    @Override
    protected void activityCreated() {
        sh = getActivity().getSharedPreferences("action", Context.MODE_PRIVATE);
        final boolean isHttp = getActivity().getSharedPreferences("ipset", Context.MODE_PRIVATE)
                .getBoolean("isHttp", true);//获取设置中的请求方式
        mSpinner = (Spinner) getView().findViewById(R.id.car_control_sp_car_id);
        SpinnerFactory.getSpinner(getActivity(), mSpinner, mCarIds, new SpinnerFactory.OnItemSelected() {

            @Override
            public void onSelected(int position) {
                carid = position + 1;
                mControlSh.setChecked(sh.getBoolean(carid + "", false));
            }
        });
        mControlSh = (Switch) getView().findViewById(R.id.car_control_sh_start_stop);
        mControlSh.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    action = actions[0];
                } else {
                    action = actions[1];
                }
                sh.edit().putBoolean(carid + "", isChecked).apply();
                if (isHttp)//判断请求方式是不是HTTP，不是则用MQTT请求
                    connec();
                else
                    mqttConnec();
            }


        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_car_control;
    }

    private void connec() {
        CarActionRequest carActionRequest = new CarActionRequest(getActivity());
        carActionRequest.setAction(action);
        carActionRequest.setCarId(carid);
        carActionRequest.conToServer(new BaseRequest.OnGetDataListener() {

            @Override
            public void onReturn(Object data) {
                if (data.equals("ok") && CarControlFragment.this.isAdded()) {
                    MyToast.getToast(getActivity(),
                            getString(R.string.success, getString(R.string.set)));
                }
            }
        });
    }

    private void mqttConnec() {
        SetCarActionMqttRequest request = new SetCarActionMqttRequest(getActivity());
        request.setAction(action);
        request.setCarId(carid);
        request.conToBroker(new BaseMqttRequest.OnGetDataListener() {
            @Override
            public void onReturn(Object data) {
                if (data.equals("ok") && CarControlFragment.this.isAdded()) {
                    MyToast.getToast(getActivity(),
                            getString(R.string.success, getString(R.string.set)));
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }

}
