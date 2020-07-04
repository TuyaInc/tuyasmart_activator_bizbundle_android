package com.tuya.smart.config.bizbundle;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tuya.smart.deviceconfig.api.ITuyaDeviceActiveListener;
import com.tuya.smart.deviceconfig.api.TuyaDeviceActivatorManager;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.home.sdk.bean.HomeBean;
import com.tuya.smart.home.sdk.callback.ITuyaGetHomeListCallback;
import com.tuya.smart.home.sdk.callback.ITuyaHomeResultCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int INFO_MESSAGE = 1;
    private static final int REFRESH_CREATE_HOME_MESSAGE = 2;
    private EditText infoEt;
    private Button creatHome;
    private long currentHomeId = 0;
    private List<String> roomList = new ArrayList<>();
    private String homeName = "Big Bear Beer Bar";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoEt = findViewById(R.id.et_info);
        creatHome = findViewById(R.id.create_home);
        initData();

        Fresco.initialize(MainActivity.this);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == INFO_MESSAGE) {
                if (msg.obj != null)
                    infoEt.append((String) msg.obj + "\n");
                else
                    Toast.makeText(MainActivity.this, "msg null", Toast.LENGTH_SHORT).show();
            } else if (msg.what == REFRESH_CREATE_HOME_MESSAGE) {
                creatHome.setVisibility(View.GONE);
            }
        }
    };

    private void initData() {
        for (int i=0; i<5; i++) {
            roomList.add("beer bar -- " + i);
        }

        queryHomeInfo();
    }

    private void queryHomeInfo() {
        TuyaHomeSdk.getHomeManagerInstance().queryHomeList(new ITuyaGetHomeListCallback() {
            @Override
            public void onSuccess(List<HomeBean> homeBeans) {
                // do something
                if (homeBeans != null && !homeBeans.isEmpty()) {
                    currentHomeId = homeBeans.get(0).getHomeId();
                    refreshHomeDeviceInfo();
                    Message msg = Message.obtain();
                    msg.what = REFRESH_CREATE_HOME_MESSAGE;
                    mHandler.sendMessage(msg);
                }
            }
            @Override
            public void onError(String errorCode, String error) {
                // do something
            }
        });
    }

    private void refreshHomeDeviceInfo() {
        TuyaHomeSdk.newHomeInstance(currentHomeId).getHomeDetail(new ITuyaHomeResultCallback() {
            @Override
            public void onSuccess(HomeBean bean) {

            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        });
    }

    public void createHome(View view) {
        TuyaHomeSdk.getHomeManagerInstance().createHome(homeName,
                0,
                0,
                "",
                roomList,
                new ITuyaHomeResultCallback() {
                    @Override
                    public void onSuccess(HomeBean homeBean) {
                        currentHomeId = homeBean.getHomeId();
                        Message msg = Message.obtain();
                        msg.what = INFO_MESSAGE;
                        msg.obj = "Home id: " + homeBean.getHomeId();
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onError(String errorCode, String errorMsg) {
                        Message msg = Message.obtain();
                        msg.what = INFO_MESSAGE;
                        msg.obj = errorCode + "--" + errorMsg;
                        mHandler.sendMessage(msg);
                    }
                });
    }

    public void actionConfig(View view) {
        if (currentHomeId == 0) {
            Toast.makeText(MainActivity.this, "please create home first", Toast.LENGTH_SHORT).show();
            return;
        }
        TuyaDeviceActivatorManager.startDeviceActiveAction(this, currentHomeId, new ITuyaDeviceActiveListener() {
            @Override
            public void onDevicesAdd(List<String> list) {
                StringBuilder str = new StringBuilder();
                for (String id: list) {
                    str.append("add device success, id: " + id).append("\n");
                }
                Message msg = Message.obtain();
                msg.what = INFO_MESSAGE;
                msg.obj = str.toString();
                mHandler.sendMessage(msg);
            }

            @Override
            public void onRoomDataUpdate() {
                Toast.makeText(MainActivity.this, "please refresh room data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOpenDevicePanel(String s) {
                Toast.makeText(MainActivity.this, "u can open the panel of the device: " + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onExitConfigBiz() {
                Toast.makeText(MainActivity.this, "device config business exit", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
