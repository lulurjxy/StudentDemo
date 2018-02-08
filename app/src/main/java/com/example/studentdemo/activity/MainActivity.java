
package com.example.studentdemo.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentdemo.R;
import com.example.studentdemo.adapter.MenuListAdapter;
import com.example.studentdemo.been.Envir;
import com.example.studentdemo.been.MenuModel;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends BaseActivity implements OnClickListener {
    private SlidingMenu mMenu;
    private ListView mLeftMenuLV;
    private TextView mEnvirTV;
    private TextView mDistanceTV;
    private AsyncTask<Void, Void, Map<String, Object>> asyncTask;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Map<String, Object> map = (Map<String, Object>) msg.obj;
            if (map != null) {
                upTextView(map);
            }
        }

        ;
    };

    @Override
    protected String getLayoutTitle() {
        return getString(R.string.title_main);
    }

    @Override
    protected void initData() {
        Map<String, Object> map = new HashMap<String, Object>();
        Envir envir = new Envir(100, 20, 20, 15, 0);
        int[] distance = new int[]{
                10, 20, 30, 40
        };
        map.put("envir", envir);
        map.put("distance", distance);
        upTextView(map);
    }

    @Override
    protected void initView() {
        regBroadcast();
        initSlidingMenu();
        initLeftMenu();
        setListViewListener();
        mEnvirTV = (TextView) findViewById(R.id.main_tv_envir);
        mDistanceTV = (TextView) findViewById(R.id.main_tv_distance);
        initAsyn();

        Intent intent = new Intent();
        intent.setAction("myBroadcast");
        sendBroadcast(intent);
    }

    BroadcastReceiver broadcast;

    private void regBroadcast() {
        if (broadcast == null) {
            broadcast = new NetStateChangeBroadcast();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(broadcast, filter);
        }
    }


    private void initLeftMenu() {
        View view = mMenu.getMenu();
        RelativeLayout layout7 = (RelativeLayout) view.findViewById(R.id.layout7);
        mLeftMenuLV = (ListView) view.findViewById(R.id.listView1);
        layout7.setOnClickListener(this);
        prepare4ListView();
    }

    private void prepare4ListView() {
        List<MenuModel> list = new ArrayList<MenuModel>();
        String[] contents = getResources().getStringArray(R.array.menu_content);

        int[] icons = {
                R.mipmap.b
        };

        for (int i = 0; i < contents.length; i++) {
            list.add(new MenuModel(icons[i], contents[i]));
        }

        MenuListAdapter adapter = new MenuListAdapter(this, list);
        mLeftMenuLV.setAdapter(adapter);
    }

    private void setListViewListener() {
        mLeftMenuLV.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, MyCarActivity.class));
                        overridePendingTransition(R.anim.enter_open, R.anim.enter_close);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;


                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initSlidingMenu() {
        mMenu = new SlidingMenu(this);

        mMenu.setMode(SlidingMenu.RIGHT);

        mMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mMenu.setShadowWidthRes(R.dimen.shadow_width);

        mMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        mMenu.setBehindWidth(300);
        mMenu.setFadeDegree(0.35f);

        mMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        mMenu.setMenu(R.layout.leftmenu1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout7:
                startActivity(new Intent(MainActivity.this, IpSetActivity.class));
                break;
        }
    }

    @Override
    protected void onAfter() {
        mMenu.toggle();
    }

    private void initAsyn() {
        asyncTask = new AsyncTask<Void, Void, Map<String, Object>>() {

            @Override
            protected Map<String, Object> doInBackground(Void... params) {
                String url = "http://"
                        + getSharedPreferences("ipset", 0).getString("ip", "192.168.1.133") + ":"
                        + 8080
                        + "/transportservice/type/jason/action/";
                Map<String, Object> map = null;
                try {
                    Envir envir = getEnvir(url);
                    Thread.sleep(100);
                    int[] distance = new int[4];
                    int[] d1 = getDistance(url, 1);
                    Thread.sleep(100);
                    int[] d2 = getDistance(url, 2);
                    for (int i = 0; i < distance.length; i++) {
                        if (i < 2) {
                            distance[i] = d1[i];
                        } else {
                            distance[i] = d2[i - 2];
                        }
                    }
                    map = new HashMap<String, Object>();
                    map.put("envir", envir);
                    map.put("distance", distance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return map;
            }

            @Override
            protected void onPostExecute(Map<String, Object> map) {
                super.onPostExecute(map);
                upTextView(map);
            }
        };
    }

    private Envir getEnvir(String url) {
        Envir envir = null;

        return null;
    }

    private int[] getDistance(String url, int stationID) {
        int[] distance = null;

        return null;
    }

    @SuppressLint("StringFormatMatches")
    private void upTextView(Map<String, Object> map) {
        if (map != null) {
            Envir envir = (Envir) map.get("envir");
            int[] distance = (int[]) map.get("distance");
            if (envir != null) {
                mEnvirTV.setText(getString(R.string.envir, envir.getPm(), envir.getCo2(),
                        envir.getTemp(), envir.getHumi()));
            }
            if (distance != null && distance.length > 0) {
                mDistanceTV.setText(getString(R.string.bus_station, distance[0],
                        distance[1], distance[2], distance[3]));
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    long mExitTime;//上次点击时间

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 1000) {
            Toast.makeText(MainActivity.this, " 再 按 一 次 退 出 程 序 ", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }


    private AlertDialog dialog;

    class NetStateChangeBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}


