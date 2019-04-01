package com.altsoft.localbox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.altsoft.Interface.ServiceInfo;
import com.altsoft.framework.Global;
import com.altsoft.framework.module.BaseActivity;
import com.altsoft.model.LOGGAL_BOX_AUTH;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class DeviceAuthLoginActivity extends BaseActivity {
    int value = 0;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_auth_login);
        ComponentInit();
    }

    private void ComponentInit() {
        LoginAuthChekc();
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                value++;
                // 메세지를 처리하고 또다시 핸들러에 메세지 전달 (15000ms 지연) - 15초
                mHandler.sendEmptyMessageDelayed(0, 6000);
                LoginAuthChekc();
            }
        };
        mHandler.sendEmptyMessage(0);
    }

    String DeviceID;

    private void LoginAuthChekc() {

        DeviceID = Global.GetDevicesUUID(this);
        LOGGAL_BOX_AUTH Cond = new LOGGAL_BOX_AUTH();
        Cond.DEVICE_NUMBER = DeviceID;
        Cond.AUTH_NUMBER = Global.getDeviceInfo().AUTH_NUMBER;
        Cond.AUTH_TYPE = 2;
        Call<List<LOGGAL_BOX_AUTH>> call = Global.getAPIService().GetLocalboxAuth(Cond);
        Global.getCallService().callService(call
                , new ServiceInfo.Act<ArrayList<LOGGAL_BOX_AUTH>>() {
                    @Override
                    public void execute(ArrayList<LOGGAL_BOX_AUTH> list) {
                        if (list.size() >= 0) {
                            list.get(0).DEVICE_NUMBER = DeviceID;
                            Global.setDeviceInfo(list.get(0));
                            ;
                            if (Global.getDeviceInfo().DEVICE_CODE != null && Global.getDeviceInfo().DEVICE_CODE > 0) {
                                Intent intent = new Intent(Global.getCurrentActivity(), MainActivity.class);
                                Global.getCurrentActivity().startActivity(intent);
                                mHandler.removeMessages(0);

                            } else {
                                if (Global.getDeviceInfo().AUTH_NUMBER == null) {
                                    getAuthNumber();
                                } else

                                    ((TextView) findViewById(R.id.txtAuthNumber)).setText(Global.getStringInfo().padLeft(Global.getDeviceInfo().AUTH_NUMBER.toString(), 4, '0'));
                            }
                        }
                    }
                },
                new ServiceInfo.Act<Throwable>() {
                    @Override
                    public void execute(Throwable data) {
                        //TODO: Do something!
                    }
                }
        );
    }

    private void getAuthNumber() {
        LOGGAL_BOX_AUTH Cond = new LOGGAL_BOX_AUTH();
        Cond.DEVICE_NUMBER = DeviceID;
        Cond.AUTH_TYPE = 2;
        Call<Long> call = Global.getAPIService().GetLoggalBoxAuthAutoNumber(Cond);
        Global.getCallService().callService(call
                , new ServiceInfo.Act<Long>() {
                    @Override
                    public void execute(Long val) {
                        LOGGAL_BOX_AUTH data = Global.getDeviceInfo();
                        data.AUTH_NUMBER = val;
                        Global.setDeviceInfo(data);
                        ((TextView) findViewById(R.id.textView)).setText("");
                        ((TextView) findViewById(R.id.txtAuthNumber)).setText(Global.getStringInfo().padLeft(Global.getDeviceInfo().AUTH_NUMBER.toString(), 4, '0'));
                    }
                },
                new ServiceInfo.Act<Throwable>() {
                    @Override
                    public void execute(Throwable data) {
                        //TODO: Do something!
                    }
                }
        );
    }
}
