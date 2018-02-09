
package com.example.studentdemo.httpRequest;

import android.content.Context;

import com.example.studentdemo.config.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;


public class CarActionRequest extends BaseRequest {
    private int carId;
    private String action;

    public CarActionRequest(Context context) {
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
    protected String getAddr() {
        return AppConfig.SET_CAR_ACTION;
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
