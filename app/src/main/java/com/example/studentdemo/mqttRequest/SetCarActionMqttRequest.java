
package com.example.studentdemo.mqttRequest;

import android.content.Context;


import com.example.studentdemo.config.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 小车控制MQTT请求
 */
public class SetCarActionMqttRequest extends BaseMqttRequest {
    private int carId;
    private String action;

    public SetCarActionMqttRequest(Context context) {
        super(context);
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    @Override
    protected String params() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppConfig.KEY_CAR_ACTION, action);
            jsonObject.put(AppConfig.KEY_CAR_ID, carId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @Override
    protected String getPub() {
        return "setCarMove";
    }

    @Override
    protected String getSub() {
        return "repSetCarMove";
    }

    @Override
    protected Object doResult(String responseString) {
        String result = "";
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            result = jsonObject.getString("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

}
