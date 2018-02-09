package com.example.studentdemo.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * MQTT网络连接工具类
 */

public class MqttUtil {

    public final static int QOS_MOST_ONCE = 0;
    public final static int QOS_LEAST_ONCE = 1;
    public final static int QOS_ONLY_ONCE = 2;
    private static final String TAG = MqttUtil.class.getSimpleName();
    private static String broker = "tcp://192.168.2.206:1883";// 代理服务器
    private static MqttClient myMqttClient;
    private static Handler handler = new Handler(Looper.getMainLooper());

    public interface MyMqttCallBack {
        void onConnectionLost(String info);

        void onMessageArrived(String topic, String result);

        void onDeliveryComplete(String info);
    }

    public void myMqttRequest(final String brokerUrl, final String pubTopic, final String subTopic, final String content, final MyMqttCallBack callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                request(brokerUrl, pubTopic, subTopic, content, callback);
            }
        }).start();
    }

    public static void request(String brokerUrl, String pubTopic, String subTopic, String content, final MyMqttCallBack callback) {
        String clientId = "testClient";
        if (!TextUtils.isEmpty(brokerUrl)) {
            broker = "tcp://" + brokerUrl + ":1883";
        }
        MemoryPersistence persistence = new MemoryPersistence();

        try {
//            if (myMqttClient == null)
            myMqttClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            Log.i(TAG, "request: Connecting to broker:" + broker);
//            if (!myMqttClient.isConnected())
            myMqttClient.connect(connOpts);
            Log.i(TAG, "request: Connected 已连接到服务器\n" + "推送 message: " + content);

            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(QOS_ONLY_ONCE);
            myMqttClient.publish(pubTopic, message);
            myMqttClient.subscribe(subTopic);
            Log.i(TAG, "request: 推送topic:" + pubTopic + "\n订阅topic:" + subTopic);

            myMqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onConnectionLost("丢失连接");
                        }
                    });
                }

                /**
                 * 订阅数据到达后，对应的处理
                 * @param topic 订阅的主题
                 * @param message 返回的信息
                 * @throws Exception 错误信息
                 */
                @Override
                public void messageArrived(final String topic, final MqttMessage message) throws Exception {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onMessageArrived(topic, new String(message.getPayload()));
                        }
                    });
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    try {
                        Log.i(TAG, "deliveryComplete: " + new String(token.getMessage().getPayload()));
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onDeliveryComplete("完成");
                        }
//                    close();
                    });
                }
            });
        } catch (MqttException me) {
            Log.e(TAG, "request: exception:" + "\n reason:" + me.getReasonCode() + "\n msg:" + me.getMessage() +
                    "\n loc:" + me.getLocalizedMessage() + "\n cause:" + me.getCause() + "\n excep:" + me);
            me.printStackTrace();
        }
    }

    public static void close() {
        try {
            if (myMqttClient != null && myMqttClient.isConnected())
                myMqttClient.disconnect();
            Log.i(TAG, "close: disconnect");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
