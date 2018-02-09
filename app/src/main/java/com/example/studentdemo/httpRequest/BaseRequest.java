
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
                + context.getSharedPreferences("ipset", 0).getString("ip", "192.168.0.238") + ":"
                + 8080
                + "/transportservice/type/jason/action/";
    }

    /**
     * 连接服务器
     *
     * @param listener 结果返回监听
     */
    public void conToServer(final OnGetDataListener listener) {
        mNetUtil1 = new HttpNetUtil();
        mNetUtil1.asynPost(url + getAddr(), params(), new HttpNetUtil.ResponseListener() {

            @Override
            public void success(String result) {
                if (listener != null) {
                    if (!result.isEmpty()) {
                        listener.onReturn(doResult(result));
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

    protected abstract String params();

    /**
     * 处理结果
     *
     * @param responseString 返回结果
     * @return 处理后返回的对象
     */
    protected abstract Object doResult(String responseString);
}
