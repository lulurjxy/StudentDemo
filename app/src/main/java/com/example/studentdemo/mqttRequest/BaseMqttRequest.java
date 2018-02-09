package com.example.studentdemo.mqttRequest;

import android.content.Context;
import android.util.Log;


import com.example.studentdemo.utils.MqttUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * MQTT请求基类
 */

public abstract class BaseMqttRequest {
    private static final String TAG = BaseMqttRequest.class.getSimpleName();

    private String url;
    private Context context;
    private final MqttUtil mqttUtil = new MqttUtil();

    BaseMqttRequest(Context context) {
        this.context = context;
    }

    public interface OnGetDataListener {
        void onReturn(Object data);
    }

    /**
     * 连接服务器
     *
     * @param listener 得到结果的监听
     */
    public void conToBroker(final OnGetDataListener listener) {
        mqttUtil.myMqttRequest(getBroker(), getPub(), getSub(), params(), new MqttUtil.MyMqttCallBack() {
            @Override
            public void onConnectionLost(String info) {
//                listener.onReturn(info);
            }

            @Override
            public void onMessageArrived(String topic, String result) {
                if (listener != null) {
                    Log.i(TAG, "messageArrived: result:" + result + "   topic:" + topic);
                    if (!result.isEmpty()) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            listener.onReturn(doResult(jsonObject.getString("serverinfo")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onDeliveryComplete(String info) {
//                listener.onReturn(info);
            }
        });


    }

    /**
     * @return 代理服务器地址
     */
    private String getBroker() {
        return context.getSharedPreferences("ipset", Context.MODE_PRIVATE).getString("ip", "192.168.0.238");
    }

    /**
     * @return 传递的参数
     */
    protected abstract String params();

    /**
     * @return 推送主题
     */
    protected abstract String getPub();

    /**
     * @return 订阅主题
     */
    protected abstract String getSub();

    /**
     * @param responseString 返回的结果
     * @return 返回结果处理后返回对应的对象
     */
    protected abstract Object doResult(String responseString);

    /**
     * 断开连接
     */
    public void close() {
        mqttUtil.close();
    }
}
