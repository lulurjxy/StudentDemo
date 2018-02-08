
package com.example.studentdemo.httpRequest;

import android.content.Context;

import com.example.studentdemo.utils.HttpNetUtil;


public abstract class BaseRequest {
    public interface OnGetDataListener {
        void onReturn(Object data);
    }

    private HttpNetUtil mNetUtil1;
    private String url;
    private Context context;

    public BaseRequest(Context context) {
        this.context = context;
        url = "http://"
                + context.getSharedPreferences("ipset", 0).getString("ip", "192.168.1.131") + ":"
                + 8080
                + "/transportservice/type/jason/action/";
    }

    public void connec(final OnGetDataListener listener) {
        mNetUtil1 = new HttpNetUtil();
        mNetUtil1.asynPost(url + getAddr(), getParams(), new HttpNetUtil.ResponseListener() {

            @Override
            public void success(String result) {
                if (listener != null) {
                    if (!result.isEmpty()) {
                        listener.onReturn(anaylizeResponse(result));
                    }
                }
            }

            @Override
            public void error(String msg) {
                // MyToast.getToastLong(context, msg);
            }
        });
    }

    protected abstract String getAddr();

    protected abstract String getParams();

    protected abstract Object anaylizeResponse(String responseString);
}
